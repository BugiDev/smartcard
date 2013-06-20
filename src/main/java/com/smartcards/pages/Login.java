package com.smartcards.pages;

import com.smartcards.entities.User;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Import(stylesheet = {"context:css/login_style.css"}, library = {"context:js/jquery-1.5.2.min.js", "context:js/login_onStart.js"})
public class Login {

    private Logger logger = LoggerFactory.getLogger(Login.class);
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

    public NewCardsUsers onSubmit() {
        logger.debug("Username = "+username);
        logger.debug("Password = "+password);
        List resaultList = hibernate.createCriteria(User.class).add(Restrictions.eq("username", username)).add(Restrictions.eq("password", password)).add(Restrictions.eq("userActive", true)).list();
        logger.debug(resaultList.toString());
        if (resaultList.size() > 0) {
            User tempUser = (User) resaultList.get(0);
            logger.debug(tempUser.getUsername()+" "+tempUser.getPassword());
            asoUser = tempUser;
        }
        return newCardsUsers;
    }


}
