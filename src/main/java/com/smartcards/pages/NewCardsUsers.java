package com.smartcards.pages;

import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR, UserType.CONTRIBUTOR})
public class NewCardsUsers {

    @SessionState
    @Property
    private User asoUser;
}
