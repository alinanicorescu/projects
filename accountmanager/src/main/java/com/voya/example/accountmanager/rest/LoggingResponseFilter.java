package com.voya.example.accountmanager.rest;


import com.sun.org.apache.regexp.internal.RESyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.JacksonObjectMapperFactoryBean;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.List;

/**
 * Created by alinanicorescu on 18/12/2016.
 */
@Provider
public class LoggingResponseFilter implements ContainerResponseFilter {


    private static final Logger logger = LoggerFactory.getLogger(LoggingResponseFilter.class);

    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext responseContext) throws IOException {


            String method = containerRequestContext.getMethod();

            logger.debug("Requesting " + method + " for path " + containerRequestContext.getUriInfo().getPath() +
                    " PATH PARAMETERS: " + stringParameters(containerRequestContext.getUriInfo().getPathParameters()) +
                    " QUERY PARAMETERS: " + stringParameters(containerRequestContext.getUriInfo().getQueryParameters()) +
                    " STATUS: " + responseContext.getStatus());
            logger.debug("Response status: " + responseContext.getStatus());

        }

    private String stringParameters (MultivaluedMap<String, String> params) {
        StringBuilder builder = new StringBuilder();
        for (String key : params.keySet()) {
            List<String> values = params.get(key);
            String stringValues = String.join(",", values);
            builder.append(key).append("{").append(stringValues).append("}");
        }
        return builder.toString();
    }
}

