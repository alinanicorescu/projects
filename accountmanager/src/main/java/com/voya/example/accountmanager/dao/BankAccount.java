package com.voya.example.accountmanager.dao;

import org.springframework.stereotype.Component;

import javax.ws.rs.Path;
import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */

@Component
@Path("/account")
public class BankAccount {

    private BigDecimal amount;

    public BankAccount() {

    }


}
