package com.voya.example.accountmanager.rest;

import com.voya.example.accountmanager.dao.AcccountManagerDao;
import com.voya.example.accountmanager.model.AccountBalance;
import com.voya.example.accountmanager.model.AccountDeposit;
import com.voya.example.accountmanager.model.AccountTransfer;
import com.voya.example.accountmanager.model.exception.DataValidationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.ValidationException;
import java.math.BigDecimal;

/**
 * Created by alinanicorescu on 17/12/2016.
 */

@Path("account")
public class BankAccountResource {

    @Autowired
    private AcccountManagerDao acccountManagerDao;

    @GET
    @Path("{email}/balance")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountBalance getAccountBalance(@PathParam("email") String email,
                                            @QueryParam("accountCode") String accountCode) {

        if (accountCode == null) {
            throw new DataValidationException("Account code is missing");
        }
        AccountBalance accountBalance =  acccountManagerDao.getAccountBalance(email, accountCode);
        return accountBalance;

    }

    @POST
    @Path("{email}/deposit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response deposit(@BeanParam AccountDeposit accountDeposit) {
        accountDeposit.validate();
        acccountManagerDao.deposit(accountDeposit);
        return buildResponse(Response.Status.CREATED, null, MediaType.TEXT_HTML);

    }


    @POST
    @Path("{email}/transfer")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response transfer(@BeanParam AccountTransfer accountTransfer) {

        accountTransfer.validate();
        acccountManagerDao.transfer(accountTransfer);
        return buildResponse(Response.Status.CREATED, null, MediaType.TEXT_HTML);

    }

    private Response buildResponse (Response.Status status, Object entity, String encoding) {
        Response.ResponseBuilder responseBuilder = Response.status(status).encoding(encoding);
        if (entity != null) {
            responseBuilder.entity(entity);
        }
        return responseBuilder.build();
    }
}
