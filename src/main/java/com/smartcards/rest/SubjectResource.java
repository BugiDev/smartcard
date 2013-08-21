
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
 * Klasa SubjectResource kojom se expose-uju RESTful servisi. Kao početni segment
 * URL-a, dodeljena je vrednost /subject kako bi se obeležilo da ovaj servis radi
 * samo sa kategorijama.
 *
 * @author Bogdan Begovic
 */
@Path("/subject")
public class SubjectResource {
    
    @Property
    @Inject
    private Session hibernate;
    
    private List<Subject> subjects;

    /**
     * Metoda kojom se dodaju gettuju sve kategorije. 
     * Vraća se lista svih kategorija.
     * URL segment za ovu metodu je /getAllSubjects.
     * @return List<Subject>
     */
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
