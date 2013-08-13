/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.rest;

import com.smartcards.entities.Subject;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Bogdan Begovic
 */
@Path("/subject")
public class SubjectResource {
    
    @Property
    @Inject
    private Session hibernate;
    
    private List<Subject> subjects;

    @GET
    @Path("/getAllSubjects")
    @Produces({"application/json"})
    public List<Subject> getAllSubjects() {
        
        subjects = (List<Subject>) hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectDeleted", false)).list();
        
        for (Subject subject : subjects) {
            subject.setCard(null);
        }
        if (subjects == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        
        return subjects;
    }
}
