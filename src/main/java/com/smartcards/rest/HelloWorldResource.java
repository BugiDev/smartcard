/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.rest;

/**
 *
 * @author Bogdan
 */
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
 
@Path("/helloworld")
public class HelloWorldResource {

    public HelloWorldResource() {
    }
    
    @GET
    @Produces({"application/xml", "application/json"})
    public String getAllDomains()
    {
        return "nesto";
    }
}
