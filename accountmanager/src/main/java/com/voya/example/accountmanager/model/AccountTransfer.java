package com.voya.example.accountmanager.model;

import com.voya.example.accountmanager.model.exception.DataValidationException;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
public class AccountTransfer {

    @PathParam("email")
    private String fromEmail;
    @FormParam("toEmail")
    private String toEmail;
    @FormParam("from")
    private String fromAccount;
    @FormParam("to")
    private String toAccount;
    @FormParam("amount")
    private BigDecimal amount;

    public void validate() throws DataValidationException {
        boolean allParams = fromEmail != null && toEmail != null ||
                fromAccount != null && toAccount != null && amount != null;
        if (!allParams) {
            throw new DataValidationException("The parameters {fromEmail, toEmail, fromAccount, toAccount, amount} must not be null");
        }
        if (BigDecimal.ZERO.compareTo(amount) >= 0) {
            throw new DataValidationException("The amount to transfer must be a positive value");
        }
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
