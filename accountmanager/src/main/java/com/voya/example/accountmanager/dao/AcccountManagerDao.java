package com.voya.example.accountmanager.dao;

import com.voya.example.accountmanager.model.AccountBalance;
import com.voya.example.accountmanager.model.AccountDeposit;
import com.voya.example.accountmanager.model.AccountTransfer;
import com.voya.example.accountmanager.model.UserDetails;
import com.voya.example.accountmanager.model.exception.DataValidationException;
import org.springframework.dao.DataAccessException;

import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
public interface AcccountManagerDao {


    void registerUser(UserDetails userDetails) throws DataAccessException;

    AccountBalance getAccountBalance(String email, String accountCode) throws DataAccessException;

    int deposit(AccountDeposit deposit) throws DataValidationException, DataAccessException;

    void transfer(AccountTransfer accountTransfer) throws DataValidationException, DataAccessException;
}
