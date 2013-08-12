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
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.Session;

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN})
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

    // The code
    void setupRender() {

        userModel = beanModelSource.createDisplayModel(User.class, messages);
        userModel.add("action", null);
        userModel.add("roleTypeName", null);
        
        userModel.include("firstName", "lastName", "email", "username", "password", "dailyCounter", "lastLogedIn", "roleTypeName", "action");
        userModel.get("firstName").sortable(false);
        userModel.get("lastName").sortable(false);
        userModel.get("email").sortable(false);
        userModel.get("username").sortable(false);
        userModel.get("password").sortable(false);
        userModel.get("dailyCounter").sortable(false);
        userModel.get("lastLogedIn").sortable(false);

        // Get all persons - ask business service to find them (from the database)

        users = hibernate.createCriteria(User.class).list();

    }

    public Object onEditUser(long userID) {
        return null;
    }
}
