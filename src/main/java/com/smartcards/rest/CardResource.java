/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.rest;

import com.smartcards.entities.Card;
import com.smartcards.entities.Subject;
import com.smartcards.util.CardStatusType;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
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
@Path("/card")
public class CardResource {

    @Property
    @Inject
    private Session hibernate;
    private List<Card> cards;
    private Card card;
    @Inject
    private HibernateSessionManager manager;

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

    @POST
    @Path("/getCardByID")
    @Produces({"application/json"})
    public Card getCardByID(@FormParam("cardID") long cardID) {

        card = (Card) hibernate.createCriteria(Card.class).add(Restrictions.eq("cardID", cardID)).uniqueResult();

        card.setUser(null);
        card.setSubject(null);

        if (card == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return card;
    }

    @POST
    @Path("/rateCard")
    @Produces({"application/json"})
    public Card rateCard(@FormParam("cardID") long cardID, @FormParam("rating") int rating) {

        card = (Card) hibernate.createCriteria(Card.class).add(Restrictions.eq("cardID", cardID)).uniqueResult();
        card.setCardNumRaters(card.getCardNumRaters() + 1);
        card.setCardRatingTotal(card.getCardRatingTotal() + rating);
        card.setUser(null);
        card.setSubject(null);
        hibernate.flush();
        manager.commit();
        if (card == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return card;
    }
}
