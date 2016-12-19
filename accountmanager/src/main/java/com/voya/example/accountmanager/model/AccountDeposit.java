package com.voya.example.accountmanager.model;

import com.voya.example.accountmanager.model.exception.DataValidationException;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 18/12/2016.
 */

public class AccountDeposit {
    
    @PathParam("email")
    private String email;

    @FormParam("accountCode")
    private String accountCode;

    @FormParam("amount")
    private BigDecimal amount;

    public void validate() throws DataValidationException {
        boolean allParams = email != null && accountCode != null && amount != null;
        if (!allParams) {
            throw new DataValidationException("The parameters {email, accountCode, amount} must not be null");
        }
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new DataValidationException("The amount to transfer must be a positive value");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
