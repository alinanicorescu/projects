package com.voya.example.accountmanager.rest;

import com.voya.example.accountmanager.dao.AcccountManagerDao;
import com.voya.example.accountmanager.model.UserDetails;
import org.slf4j.Logger;
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

        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(email);
        userDetails.setUsername(userName);
        userDetails.validate();
        acccountManagerDao.registerUser(userDetails);
        return Response.status(Response.Status.CREATED).encoding(MediaType.APPLICATION_JSON).build();
    }

}
