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
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Bogdan Begovic
 */
@Path("/authenticate")
public class LoginResource {

    @Property
    @Inject
    private Session hibernate;
    @Property
    private User restUser;
    @Inject
    private HibernateSessionManager manager;

    @POST
    @Produces({"application/json"})
    public User authenticate(@FormParam("username") String username, @FormParam("password") String password) {

        restUser = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password)).uniqueResult();

        if (restUser == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        restUser.setCard(null);
        restUser.setLastLogedIn(new Date());

        try {
            hibernate.update(restUser);
            hibernate.flush();
            manager.commit();
        } catch (Exception e) {
            manager.abort();
        }

        return restUser;
    }
}
