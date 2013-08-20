package com.smartcards.rest;

import com.smartcards.entities.Card;
import com.smartcards.entities.Subject;
import com.smartcards.util.CardStatusType;
import java.util.List;
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
 * Klasa CardResource kojom se expose-uju RESTful servisi. Kao početni segment
 * URL-a, dodeljena je vrednost /card kako bi se obeležilo da ovaj servis radi
 * samo sa karticama.
 *
 * @author Bogdan Begovic
 */
@Path("/card")
public class CardResource {

    @Property
    @Inject
    private Session hibernate;
    private List<Card> cards;
    private Card card;
    @Inject
    private HibernateSessionManager manager;

    /**
     * Metoda kojom se gettuju sve kartice iz određene kategorije. Prosleđuje se
     * ID kategorije a vraća se lista kartica. URL segment za ovu metodu je
     * /getAllSubjects.
     *
     * @param subjectID
     * @return List<Card>
     */
    @POST
    @Path("/getAllCardsForSubject")
    @Produces({"application/json"})
    public List<Card> getAllSubjects(@FormParam("subjectID") long subjectID) {

        Subject subject = (Subject) hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectID", subjectID)).uniqueResult();

        if (subject == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } else {
            System.out.println("SUBJECT " + subject.getSubjectName());
        }

        cards = (List<Card>) hibernate.createCriteria(Card.class).add(Restrictions.eq("subject", subject)).add(Restrictions.eq("cardStatus", CardStatusType.APROVED.getCode())).list();

        for (Card card : cards) {
            card.setUser(null);
            card.setSubject(null);
        }
        if (cards == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return cards;
    }

    /**
     * Metoda kojom se rate-uje određena kartica. Prosleđuje se ID kartice i rating a
     * vraća se poruka o uspehu. URL segment za ovu metodu je /rateCard.
     *
     * @param cardID
     * @param rating
     * @return String
     */
    @POST
    @Path("/rateCard")
    @Produces({"application/json"})
    public String rateCard(@FormParam("cardID") long cardID, @FormParam("rating") float rating) {

        card = (Card) hibernate.createCriteria(Card.class).add(Restrictions.eq("cardID", cardID)).uniqueResult();
        card.setCardNumRaters(card.getCardNumRaters() + 1.0f);
        System.out.println("RATING " + rating);
        card.setCardRatingTotal(card.getCardRatingTotal() + rating);
        System.out.println("TOTAL RATING " + card.getCardRatingTotal());
        hibernate.flush();
        manager.commit();
        if (card == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return "true";
    }
}
