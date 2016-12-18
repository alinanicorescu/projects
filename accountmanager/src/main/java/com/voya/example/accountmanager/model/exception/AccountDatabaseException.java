package com.voya.example.accountmanager.model.exception;

import org.springframework.dao.DataAccessException;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
public class AccountDatabaseException extends DataAccessException {

    public AccountDatabaseException(String msg) {
        super(msg);
    }

    public AccountDatabaseException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
