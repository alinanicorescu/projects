package com.voya.example.accountmanager.dao;

import com.voya.example.accountmanager.model.*;
import com.voya.example.accountmanager.model.exception.AccountDatabaseException;
import com.voya.example.accountmanager.model.exception.DataValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
@Repository
public class AccountManagerDaoImpl implements AcccountManagerDao {

    private static String BALANCE_FOR_UPDATE = "SELECT balance FROM USER_ACCOUNT WHERE CODE = ? AND USER_ID ="
            + "(SELECT ID FROM USER_DETAILS WHERE email = ?) for update";
    private static String INSERT_USER_DETAILS = "INSERT INTO USER_DETAILS(email, username) VALUES(?,?)";
    private static String INSERT_USER_ACCOUNT = "INSERT INTO USER_ACCOUNT(code, balance, description, user_id) VALUES(?,?,?,?)";
    private static String SELECT_ACCOUNT_BALANCE = "SELECT us.balance FROM USER_ACCOUNT us JOIN USER_DETAILS details ON us.user_id = details.id " +
            "WHERE details.email = ? AND us.code = ? ";
    private static String DEPOSIT_TO_ACCOUNT = "UPDATE USER_ACCOUNT us JOIN USER_DETAILS details ON us.user_id = details.id " +
            "SET us.balance = us.balance +  ? WHERE details.email=? AND us.code =?";

    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AccountManagerDaoImpl.class);

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Transactional
    public void registerUser(final UserDetails userDetails) {

        final String userName = userDetails.getUsername();
        final String email = userDetails.getEmail();
        try {
            PreparedStatementCreator psc = new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                    PreparedStatement ps = con.prepareStatement(INSERT_USER_DETAILS,
                            Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, email);
                    ps.setString(2, userName);
                    return ps;
                }
            };

            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update(psc, keyHolder);

            Number generatedId = keyHolder.getKey();
            String code = AccountDetailsGenerator.generateAccountCodeForUser(userName);
            String description = AccountDetailsGenerator.generateAccountDescriptionForUser(userName);

            jdbcTemplate.update(INSERT_USER_ACCOUNT,
                    new Object[]{code, 0, description, generatedId});
        } catch (DuplicateKeyException e) {
            throw new AccountDatabaseException("The username or email already registered", e);
        }

    }

    public AccountBalance getAccountBalance(String email, String accountCode) {

        RowMapper<AccountBalance> balanceRowMapper = new RowMapper<AccountBalance>() {
            public AccountBalance mapRow(ResultSet resultSet, int i) throws SQLException {

                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setBalance(resultSet.getBigDecimal("balance"));
                return  accountBalance;
            }
        };
         AccountBalance accountBalance =
                 jdbcTemplate.queryForObject("SELECT us.balance FROM USER_ACCOUNT us JOIN USER_DETAILS details ON us.user_id = details.id " +
                         "WHERE details.email = ? AND us.code = ? ", new Object[]{email, accountCode}, balanceRowMapper);
        accountBalance.setAccountCode(accountCode);
        return accountBalance;
    }

    public int deposit(AccountDeposit accountDeposit) throws DataValidationException {
        try {

            int updated = jdbcTemplate.update(DEPOSIT_TO_ACCOUNT,
                    new Object[]{accountDeposit.getAmount(), accountDeposit.getEmail(), accountDeposit.getAccountCode()});
            return updated;
        } catch (TransientDataAccessException e) {
            throw new AccountDatabaseException("The operation could not be completed, try again", e);
        }
    }

    @Transactional
    public void transfer(AccountTransfer accountTransfer) throws DataValidationException {

        BigDecimal amount = accountTransfer.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new DataValidationException("Amount must be greater than zero for a transfer");
        }
        BigDecimal fromAmount;
        String fromEmail = accountTransfer.getFromEmail();
        String fromAccount = accountTransfer.getFromAccount();
        try {
            fromAmount = jdbcTemplate.queryForObject(BALANCE_FOR_UPDATE, new Object[]{fromAccount, fromEmail}, BigDecimal.class);
        } catch (EmptyResultDataAccessException e) {
            throw new AccountDatabaseException("The from account was not found", e);

        }
        if (fromAmount.subtract(amount).compareTo(BigDecimal.ZERO) < 0) {
            throw new DataValidationException("Not enough money to make the transfer");
        } else {
            String toEmail = accountTransfer.getToEmail();
            String toAccount = accountTransfer.getToAccount();
            BigDecimal toDeposit = BigDecimal.ZERO.subtract(amount);
            try {
                jdbcTemplate.queryForObject(BALANCE_FOR_UPDATE, new Object[]{toAccount, toEmail}, BigDecimal.class);
            } catch (EmptyResultDataAccessException e) {
                throw new AccountDatabaseException("The to account was not found", e);

            }
            int firstDep = jdbcTemplate.update(DEPOSIT_TO_ACCOUNT,
                    new Object[]{toDeposit, fromEmail, fromAccount});
            if (firstDep == 0) {
                throw new AccountDatabaseException("The update for the fromAccount was not made");
            }
            int secDep = jdbcTemplate.update(DEPOSIT_TO_ACCOUNT,
                    new Object[]{amount, toEmail, toAccount});
            if (secDep == 0) {
                throw new AccountDatabaseException("The update for the toAccount was not made");
            }
        }
    }
}
