package com.voya.example.accountmanager.model;

import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
public class AccountBalance {

    private BigDecimal balance;
    private String accountCode;

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
