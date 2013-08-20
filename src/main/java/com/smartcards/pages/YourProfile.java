
package com.smartcards.pages;

import com.smartcards.components.ShowRoleType;
import com.smartcards.entities.User;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 * Klasa koja predstavlja stranicu Your profile.
 * Služi za prikaz profilnih podataka korisnika.
 * @author Bogdan
 */ 
public class YourProfile {

    @SessionState
    @Property
    private User asoUser;
    @InjectComponent
    private ShowRoleType showRoleType;
}
