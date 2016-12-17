package com.voya.example.accountmanager;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by alinanicorescu on 17/12/2016.
 */
public class MyApplication extends ResourceConfig {


  //  private Logger LOG = LoggerFactory.getLogger(MyApplication.class);


    public MyApplication() {
       // LOG.info("MyApplication started!");

        // Turn on Jersery classpath scanning for providers and resources in the
        // given package directories
        packages("com.voya.example.rest");

        // Jackson JSON marshalling
       // register(Jackson.class);
    }
}
