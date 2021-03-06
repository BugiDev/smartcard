package com.smartcards.pages;

import com.smartcards.entities.Card;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.CardStatusType;
import com.smartcards.util.SendMail;
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
 * Klasa NewCardsUsers predstavlja home stranicu za admin panel. Pravo pristupa
 * imaju svi tipovi korisnika. Javascript klasa selectCardForDelete je dodata
 * kako bi se omogućilo brisanje kartica i korisnika.
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR, UserType.CONTRIBUTOR})
@Import(library = {"context:js/selectCardForDelete.js"})
public class NewCardsUsers {

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
    @Component(id = "deleteForm")
    private Form deleteForm;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private long selectedCardID;
    @Property
    private List<User> users;
    @Property
    private User user;
    @Property
    private BeanModel<User> userModel;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private long selectedUserID;
    @InjectPage
    private EditCard editCardPage;
    @InjectPage
    private EditUser editUserPage;
    SendMail mailZaSlanje = null;

    /*
     * Metoda koja se poziva pri svakom renderovanju strane.
     * Koristi se da kreira i napuni grid sa podacima.
     */
    void setupRender() {

        myModel = beanModelSource.createDisplayModel(Card.class, messages);
        myModel.add("subject", null);
        myModel.add("action", null);

        myModel.include("cardQuestion", "cardAnswer", "subject", "action");
        myModel.get("subject").label("Card Subject");
        myModel.get("cardQuestion").sortable(false);
        myModel.get("cardAnswer").sortable(false);

        cards = hibernate.createCriteria(Card.class).add(Restrictions.eq("cardStatus", 1)).list();

        userModel = beanModelSource.createDisplayModel(User.class, messages);
        userModel.add("action", null);

        userModel.include("firstName", "lastName", "email", "username", "password", "lastLogedIn", "action");
        userModel.get("firstName").sortable(false);
        userModel.get("lastName").sortable(false);
        userModel.get("email").sortable(false);
        userModel.get("username").sortable(false);
        userModel.get("password").sortable(false);
        userModel.get("lastLogedIn").sortable(false);

        users = hibernate.createCriteria(User.class).add(Restrictions.eq("userConfirmed", false)).list();
    }

    /**
     * Metoda kojom se bira entitet za editovanje, a njegov ID prosleđuje se
     * stranici za editovanje.
     *
     * @param cardID
     * @return Page object
     */
    public Object onEdit(long cardID) {
        editCardPage.setInitialDataToEdit(cardID);
        return editCardPage;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za odobravanje
     * kartica.
     *
     * @param cardID
     * @return istu stranicu (refresh)
     */
    @CommitAfter
    public Object onAprove(long cardID) {

        try {
            Card card = (Card) hibernate.createCriteria(Card.class).add(Restrictions.eq("cardID", cardID)).uniqueResult();
            card.setCardStatus(CardStatusType.APROVED.getCode());
            hibernate.update(card);
            hibernate.flush();
        } catch (HibernateException e) {
            return this;
        }
        return this;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za brisanje kartica.
     *
     * @return istu stranicu (refresh)
     */
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

    /**
     * Metoda kojom se bira entitet za brisanje. Postavlja ID entiteta.
     *
     * @param selected
     */
    public void onSelectCard(long selected) {
        selectedCardID = selected;
    }

    /**
     * Metoda koja proverava da li ulogovani korisnik ima rolu admin ili
     * moderator i u zavisnosti od toga, vraća boolean vrednost.
     *
     * @return boolean
     */
    public boolean getTestIsAdminOrModerator() {
        if ((asoUser.getRoleType() == UserType.ADMIN.getCode()) || (asoUser.getRoleType() == UserType.MODERATOR.getCode())) {
            return true;
        }
        return false;
    }

    /**
     * Metoda koja proverava da li ulogovani korisnik ima rolu admin i u
     * zavisnosti od toga, vraća boolean vrednost.
     *
     * @return boolean
     */
    public boolean getTestIsAdmin() {
        if (asoUser.getRoleType() == UserType.ADMIN.getCode()) {
            return true;
        }
        return false;
    }

    /**
     * Metoda kojom se bira entitet za editovanje, a njegov ID prosleđuje se
     * stranici za editovanje.
     *
     * @param userID
     * @return Page object
     */
    public Object onEditUser(long userID) {
        editUserPage.setInitialDataToEdit(userID);
        return editUserPage;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za odobravanje
     * korisnika.
     *
     * @param cardID
     * @return istu stranicu (refresh)
     */
    @CommitAfter
    public Object onAproveUser(long userID) {

        try {
            User user = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", userID)).uniqueResult();
            user.setUserConfirmed(true);
            hibernate.update(user);
            hibernate.flush();

            mailZaSlanje = new SendMail("Your account has been created!!!", "Thank you for creating an accoutn. Your username is: " + user.getUsername() + " and password is: " + user.getPassword(), user.getEmail());
        } catch (HibernateException e) {
            return this;
        }
        return this;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za brisanje
     * korisnika.
     *
     * @return istu stranicu (refresh)
     */
    @CommitAfter
    public Object onSubmitFromDeleteUserForm() {
        try {
            User user = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", selectedUserID)).uniqueResult();
            user.setUserActive(false);
            user.setUserConfirmed(true);
            hibernate.update(user);
            hibernate.flush();
        } catch (HibernateException e) {
            return null;
        }
        return this;
    }

    /**
     * Metoda kojom se bira entitet za brisanje. Postavlja ID entiteta.
     *
     * @param selected
     */
    public void onSelectUser(long selected) {
        selectedUserID = selected;
    }
}
