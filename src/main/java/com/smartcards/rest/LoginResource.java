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
 * Klasa LoginResource kojom se expose-uju RESTful servisi. Kao početni segment
 * URL-a, dodeljena je vrednost /authenticatet kako bi se obeležilo da ovaj servis radi
 * samo sa login-om.
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

    /**
     * Metoda kojom se korisnici login-uju. 
     * Prosleđuju se podaci za username i password a vraća se objekat tipa User.
     * URL segment za ovu metodu je /getAllSubjects.
     * @param username
     * @param password
     * @return User
     */
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
