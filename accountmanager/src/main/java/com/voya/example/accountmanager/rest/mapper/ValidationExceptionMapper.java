package com.voya.example.accountmanager.rest.mapper;

import com.voya.example.accountmanager.model.exception.DataValidationException;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
@Provider
@Service
public class ValidationExceptionMapper implements ExceptionMapper<DataValidationException> {
    public Response toResponse(DataValidationException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).
                encoding(MediaType.APPLICATION_JSON).build();
    }
}
