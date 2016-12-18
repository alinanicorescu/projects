package com.voya.example.accountmanager.rest.mapper;

import com.voya.example.accountmanager.model.exception.AccountDatabaseException;
import com.voya.example.accountmanager.model.exception.DataValidationException;
import org.jvnet.hk2.annotations.Service;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
@Service
@Provider
public class DataAccessExceptionMapper implements ExceptionMapper<DataAccessException> {

    public Response toResponse(DataAccessException e) {

        Response.Status status = Response.Status.INTERNAL_SERVER_ERROR;

        if (e instanceof EmptyResultDataAccessException) {
            status = Response.Status.NOT_FOUND;

        }
        if (e instanceof DuplicateKeyException) {
            status = Response.Status.BAD_REQUEST;
        }

        return Response.status(status).entity(e.getMessage()).encoding(MediaType.APPLICATION_JSON).build();

    }
}
