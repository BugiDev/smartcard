/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.pages;

import com.smartcards.components.ShowRoleType;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
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
 * Klasa koja služi za prikaz i selektovanje korisnika koji se edituju.
 * Pravo pristupa imaju samo ADMIN korisnici. 
 * Klasa takođe dodaje i javascript klasu EditUserForDelete.js kako bi omogućila selekciju korisnika za brisanje.
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN})
@Import(library = {"context:js/dialog/EditUserForDelete.js"})
public class SelectUserEdit {

    @SessionState
    @Property
    private User asoUser;
    // Screen fields
    @Inject
    private Messages messages;
    @Property
    @Inject
    private Session hibernate;
    @Property
    private List<User> users;
    @Property
    private User user;
    @Property
    private BeanModel<User> userModel;
    @Inject
    private BeanModelSource beanModelSource;
    @InjectComponent
    private ShowRoleType showRoleType;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private long selectedUserID;
    @Component(id = "deleteForm")
    private Form deleteForm;
    @InjectPage
    private EditUser editUserPage;

    /*
     * Metoda koja se poziva pri svakom renderovanju strane.
     * Koristi se da kreira i napuni grid sa podacima.
     */
    void setupRender() {

        userModel = beanModelSource.createDisplayModel(User.class, messages);
        userModel.add("action", null);
        userModel.add("roleTypeName", null);

        userModel.include("firstName", "lastName", "email", "username", "password", "lastLogedIn", "roleTypeName", "action");
        userModel.get("firstName").sortable(false);
        userModel.get("lastName").sortable(false);
        userModel.get("email").sortable(false);
        userModel.get("username").sortable(false);
        userModel.get("password").sortable(false);
        userModel.get("lastLogedIn").sortable(false);

        users = hibernate.createCriteria(User.class).add(Restrictions.eq("userActive", true)).list();

    }

    /**
     * Metoda kojom se bira entitet za editovanje, a njegov ID prosleđuje se stranici za editovanje.
     * @param userID
     * @return Page object
     */
    public Object onEditUser(long userID) {
        editUserPage.setInitialDataToEdit(userID);
        return editUserPage;
    }

    /**
     *  Metoda koja handle-uje submit na formi.
     *  Zaslužna je za brisanje korisnika.
     * @return istu stranicu (refresh)
     */
    @CommitAfter
    public Object onSubmitFromDeleteForm() {
        try {
            User user = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", selectedUserID)).uniqueResult();
            user.setUserActive(false);
            hibernate.update(user);
            hibernate.flush();
        } catch (HibernateException e) {
            return null;
        }
        return this;
    }

    /**
     * Metoda kojom se bira entitet za brisanje. 
     * Postavlja ID entiteta.
     * @param selected
     */
    public void onSelectUser(long selected) {
        selectedUserID = selected;
    }
}
