package com.voya.example.accountmanager.dao;

import com.voya.example.accountmanager.model.AccountBalance;

import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
public interface AcccountManagerDao {

    public int selectItems();

    void registerUser(String email, String userName);

    AccountBalance getAccountBalance(String email, String accountCode);

    int deposit(String email, String accountCode, BigDecimal amount);

    int transfer(String email, String fromAccount, String toAccount, BigDecimal amount);
}
