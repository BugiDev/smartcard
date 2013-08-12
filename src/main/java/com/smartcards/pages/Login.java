package com.smartcards.pages;

import com.smartcards.entities.User;
import java.util.Date;
import java.util.List;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

@Import(stylesheet = {"context:css/login_style.css"}, library = {"context:js/jquery-1.5.2.min.js", "context:js/login_onStart.js"})
public class Login {

    @Persist(PersistenceConstants.FLASH)
    @Validate("required")
    @Property
    private String username;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String password;
    @Inject
    private Session hibernate;
    @SessionState
    @Property
    private User asoUser;
    @Property
    private boolean asoUserExists;
    @InjectPage
    private NewCardsUsers newCardsUsers;
    @Component(id = "loginForm")
    private Form form;
    @InjectComponent
    private Zone loginErrorResponse;
    @Inject
    private Messages messages;
    @Property
    private String loginErrorMessage;
    @Inject
    private Request request;
    @Inject
    private Block loginErrorBlock;

    void setupRender() {
        username = null;
        password = null;
    }

    @CommitAfter
    public Object onSubmit() {
        loginErrorMessage = "";
        List resaultList = hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password)).add(Restrictions.eq("userActive", true)).list();

        if (resaultList.size() > 0) {
            User tempUser = (User) resaultList.get(0);
            tempUser.setLastLogedIn(new Date());
            hibernate.update(tempUser);
            hibernate.flush();

            if (tempUser.getUserConfirmed()) {
                asoUser = tempUser;
                return newCardsUsers;
            } else {
                loginErrorMessage = messages.get("userNotConfirmed");
                return loginErrorBlock;
            }

        } else {
            loginErrorMessage = messages.get("loginFail");
            return loginErrorBlock;
        }
    }
}
