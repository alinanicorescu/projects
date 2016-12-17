package com.voya.example.accountmanager.model;


import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
public class AccountBalance {

    private BigDecimal amount;

    public AccountBalance() {

    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
