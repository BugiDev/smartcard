package com.smartcards.pages;

import com.smartcards.entities.User;
import com.smartcards.util.UserType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Klasa EditUser koja je zaslužna za prikaz i logiku EditUser strane. Služi za
 * editovanje korisnika.
 *
 * @author Bogdan
 */
public class EditUser {

    @Component(id = "userForm")
    private Form userForm;
    @InjectComponent
    private Zone messageZone;
    @Inject
    private Request request;
    @Environmental
    private JavaScriptSupport javaScriptSupport;
    @Persist(PersistenceConstants.FLASH)
    @Validate("required")
    @Property
    private String firstName;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String lastName;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String username;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String password;
    @Validate("required, regexp=^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String email;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private Date birthday;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String selectRoleType;
    @Property
    @Persist(PersistenceConstants.CLIENT)
    private List<String> allRoleTypes;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Property
    @Inject
    private Session hibernate;
    @SessionState
    @Property
    private User newUser;
    @Property
    private String messageText;
    @Property
    private String cssClass;
    @Inject
    private Messages messages;
    @SessionState
    @Property
    private User asoUser;

    /*
     * Metoda koja se poziva pri svakom renderovanju strane.
     * Koristi se da kreira i napuni listu svih tipova korisnika vrednostima.
     */
    public void setupRender() {

        allRoleTypes = new ArrayList<String>();
        allRoleTypes.add("Admin");
        allRoleTypes.add("Moderator");
        allRoleTypes.add("Contributor");
        allRoleTypes.add("User");
    }

    /**
     * Metoda koja handle-uje submit posle uspešne provere na formi. Zaslužna je
     * za editovanje korisnika i vraćanje poruka o uspešnosti ili grešci.
     */
    @CommitAfter
    public void onSuccess() {

        try {

            newUser.setFirstname(firstName);
            newUser.setLastname(lastName);
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setBirthday(birthday);

            if (selectRoleType.equalsIgnoreCase("admin")) {
                newUser.setRoleType(UserType.ADMIN.getCode());
            } else if (selectRoleType.equalsIgnoreCase("moderator")) {
                newUser.setRoleType(UserType.MODERATOR.getCode());
            } else if (selectRoleType.equalsIgnoreCase("contributor")) {
                newUser.setRoleType(UserType.CONTRIBUTOR.getCode());
            } else if (selectRoleType.equalsIgnoreCase("newUser")) {
                newUser.setRoleType(UserType.USER.getCode());
            }
            newUser.setLastLogedIn(new Date());
            newUser.setUserActive(true);
            newUser.setUserConfirmed(false);
            hibernate.update(newUser);
            hibernate.flush();

            messageText = messages.get("messageSuccess");
            cssClass = "messageSuccess";
            ajaxResponseRenderer.addRender("messageZone", messageZone);

            onSelectedFromResetUser();

        } catch (HibernateException e) {
            messageText = messages.get("messageError");
            cssClass = "messageError";
            ajaxResponseRenderer.addRender("messageZone", messageZone);
        }
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za resetovanje
     * podataka.
     *
     * @return istu stranicu (refresh)
     */
    public Object onSelectedFromResetUser() {
        firstName = null;
        lastName = null;
        username = null;
        password = null;
        email = null;
        birthday = null;
        selectRoleType = null;
        return this;

    }

    /**
     * Metoda kojom se dodaje deo javascript koda zaslužnog za zatvaranje
     * message komponente.
     */
    public void onAddJSMessage() {
        javaScriptSupport.addScript(" \n"
                + "        \n"
                + "        $(\".close_btn_message\").on(\"click\", function(e) {\n"
                + "    \n"
                + "            $(\"#messageZone\").hide();\n"
                + "  $(\".close_btn_message\").hide(); \n"
                + "        });");
    }

    /**
     * Metoda koja, pri učitavanju stranice, uzima entitet za prosleđeni ID i
     * podešava početne podatke koji se edituju.
     *
     * @param userID
     */
    public void setInitialDataToEdit(long userID) {

        newUser = (User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", userID)).uniqueResult();

        firstName = newUser.getFirstname();
        lastName = newUser.getLastname();
        username = newUser.getUsername();
        password = newUser.getPassword();
        email = newUser.getEmail();
        birthday = newUser.getBirthday();

        if (newUser.getRoleType() == UserType.ADMIN.getCode()) {
            selectRoleType = "Admin";
        } else if (newUser.getRoleType() == UserType.MODERATOR.getCode()) {
            selectRoleType = "Moderator";
        } else if (newUser.getRoleType() == UserType.CONTRIBUTOR.getCode()) {
            selectRoleType = "Contributor";
        } else if (newUser.getRoleType() == UserType.USER.getCode()) {
            selectRoleType = "User";
        }
    }
}
