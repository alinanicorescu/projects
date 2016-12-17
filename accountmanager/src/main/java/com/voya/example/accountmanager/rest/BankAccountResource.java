package com.voya.example.accountmanager.rest;

import com.voya.example.accountmanager.dao.AcccountManagerDao;
import com.voya.example.accountmanager.model.AccountBalance;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */

@Path("account")
public class BankAccountResource {

    @Autowired
    private AcccountManagerDao acccountManagerDao;

    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountBalance getAccountBalance(@PathParam("email") String email,
                                            @QueryParam("accountCode") String accountCode) {

        System.out.println("Account balance for: email " + email + "accountCode: " + accountCode);
        return acccountManagerDao.getAccountBalance(email, accountCode);
    }

    @POST
    @Path("deposit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deposit(@FormParam("email") String email, @FormParam("accountCode") String accountCode,
                            @FormParam("amount") BigDecimal amount) {
        System.out.println("Deposit  for: email " + email + "accountCode: " +accountCode);
        acccountManagerDao.deposit(email, accountCode, amount);
        return Response.status(Response.Status.CREATED).build();
    }


    @POST
    @Path("transfer")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response transfer(@FormParam("email") String email, @FormParam("from") String fromAccount,
                             @FormParam("to") String toAccount, @FormParam("amount") BigDecimal amount) {
        System.out.println("Transfer for: email " + email + "from: " + fromAccount + " to: " + toAccount);
        acccountManagerDao.transfer(email, fromAccount, toAccount, amount);
        return Response.status(Response.Status.CREATED).build();
    }


}
