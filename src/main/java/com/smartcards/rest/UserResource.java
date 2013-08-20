/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.rest;

import com.smartcards.entities.User;
import java.util.Date;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

/**
 *
 * @author Bogdan Begovic
 */
@Path("/user")
public class UserResource {

    @Property
    @Inject
    private Session hibernate;
    @Property
    private User restUser;
    @Inject
    private HibernateSessionManager manager;

    @POST
    @Path("/createNewUser")
    @Produces({"application/json"})
    public String createNewUser(@FormParam("firstname") String firstName, @FormParam("lastname") String lastName, @FormParam("username") String username,
            @FormParam("password") String password, @FormParam("email") String email, @FormParam("birthday") long birthday, @FormParam("roletype") int roleType) {

        User newUser = new User(username, password, email, firstName, lastName, new Date(birthday), roleType, false, new Date(), true);

        try {
            hibernate.save(newUser);
            hibernate.flush();
            manager.commit();
            return "true";
        } catch (Exception e) {
            manager.abort();
            e.printStackTrace();
            return "false";
//            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
    }
}
