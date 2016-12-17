package com.voya.example.accountmanager.rest;

import com.voya.example.accountmanager.dao.AcccountManagerDao;
import com.voya.example.accountmanager.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Created by alinanicorescu on 17/12/2016.
 */

@Path("users")
public class UserAccountResource {


    @Autowired
    private AcccountManagerDao acccountManagerDao;

    @POST
    @Path("register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("email") String email, @FormParam("username") String userName) {

        acccountManagerDao.registerUser(email, userName);
        System.out.println("Registered user: " + email + " - " + userName);
        return Response.status(Response.Status.CREATED).build();
    }

}
