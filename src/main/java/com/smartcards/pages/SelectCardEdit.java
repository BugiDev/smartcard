/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.pages;

import com.smartcards.entities.Card;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.CardStatusType;
import com.smartcards.util.UserType;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR})
@Import(library = {"context:js/dialog/EditCardForDelete.js"})
public class SelectCardEdit {

    @SessionState
    @Property
    private User asoUser;
    // Screen fields
    @Property
    private List<Card> cards;
    @Property
    private Card card;
    @Property
    private BeanModel<Card> myModel;
    @Inject
    private BeanModelSource beanModelSource;
    @Inject
    private Messages messages;
    @Property
    @Inject
    private Session hibernate;
    @InjectPage
    private EditCard editCardPage;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private long selectedCardID;
    @Component(id = "deleteForm")
    private Form deleteForm;

    // The code
    void setupRender() {

        myModel = beanModelSource.createDisplayModel(Card.class, messages);
        myModel.add("subject", null);
        myModel.add("action", null);

        myModel.include("cardQuestion", "cardAnswer", "subject", "action");
        myModel.get("subject").label("Card Subject");
        myModel.get("cardQuestion").sortable(false);
        myModel.get("cardAnswer").sortable(false);

        // Get all persons - ask business service to find them (from the database)

        cards = hibernate.createCriteria(Card.class).add(Restrictions.disjunction().add(Restrictions.eq("cardStatus", CardStatusType.PENDING.getCode())).add(Restrictions.eq("cardStatus", CardStatusType.APROVED.getCode()))).list();
    }

    public Object onEdit(long cardID) {
        editCardPage.setInitialDataToEdit(cardID);
        return editCardPage;
    }

    @CommitAfter
    public Object onSubmitFromDeleteForm() {
        try {
            Card card = (Card) hibernate.createCriteria(Card.class).add(Restrictions.eq("cardID", selectedCardID)).uniqueResult();
            card.setCardStatus(CardStatusType.TRASHED.getCode());
            hibernate.update(card);
            hibernate.flush();
        } catch (HibernateException e) {
            return null;
        }
        return this;
    }

    public void onSelectCard(long selected) {
        selectedCardID = selected;
        System.out.println("SELECTED ID: " + selected);
    }
}
