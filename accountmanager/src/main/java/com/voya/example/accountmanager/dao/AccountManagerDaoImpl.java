package com.voya.example.accountmanager.dao;

import com.sun.rowset.internal.Row;
import com.voya.example.accountmanager.model.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
@Repository
public class AccountManagerDaoImpl implements AcccountManagerDao {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int selectItems() {

        String sql = "select PRICE from ITEM WHERE ID = 1";
        return this.jdbcTemplate.queryForInt(sql);
    }

    public void registerUser(final String email, final String userName) {

        PreparedStatementCreator psc = new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT INTO USER_DETAILS(email, username) VALUES(?,?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, userName);
                return ps;
            }
        };

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(psc, keyHolder);

        Number generatedId = keyHolder.getKey();
        String code = userName + "abc";
        String description = userName + "desc";

        jdbcTemplate.update("INSERT INTO USER_ACCOUNT(code, balance, description, user_id) VALUES(?,?,?,?)",
                new Object[]{code, 0, description, generatedId });

    }

    public AccountBalance getAccountBalance(String email, String accountCode) {
        RowMapper<AccountBalance> balanceRowMapper = new RowMapper<AccountBalance>() {
            public AccountBalance mapRow(ResultSet resultSet, int i) throws SQLException {

                AccountBalance accountBalance = new AccountBalance();
                accountBalance.setAmount(resultSet.getBigDecimal("balance"));
                return  accountBalance;
            }
        };
         AccountBalance accountBalance =
                 jdbcTemplate.queryForObject("SELECT us.balance FROM USER_ACCOUNT us JOIN USER_DETAILS details ON us.user_id = details.id " +
                         "WHERE details.email = ? AND us.code = ? ", new Object[]{email, accountCode}, balanceRowMapper);
        return accountBalance;
    }

    public int deposit(String email, String accountCode, BigDecimal amount) {

        int updated = jdbcTemplate.update("UPDATE USER_ACCOUNT us JOIN USER_DETAILS details ON us.user_id = details.id " +
                        "SET us.balance = us.balance +  ? WHERE details.email=? AND us.code =?",
                new Object[]{amount, email, accountCode});
        return updated;
    }

    public int transfer(String email, String fromAccount, String toAccount, BigDecimal amount) {
        System.out.println(email + "-" + fromAccount + "-" + toAccount + "-" + amount);
        BigDecimal toDeposit = BigDecimal.ZERO.subtract(amount);
        int updated = deposit(email, fromAccount, toDeposit);
        int deposit = 0;
        if (updated > 0) {
            deposit = deposit(email, toAccount, amount);
        }
        return updated + deposit;
    }
}
