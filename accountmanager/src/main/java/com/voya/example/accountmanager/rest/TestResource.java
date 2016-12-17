package com.voya.example.accountmanager.rest;

import com.voya.example.accountmanager.dao.AcccountManagerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
@Path("test")
public class TestResource {


    @Autowired
    private AcccountManagerDao acccountManagerDao;

    @GET
    @Path("/price")
    @Produces("application/json")
    public String getPrice() {
        int price = acccountManagerDao.selectItems();
        return String.valueOf(price);
    }
}
