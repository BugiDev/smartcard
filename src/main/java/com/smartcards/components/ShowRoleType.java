package com.smartcards.components;

import com.smartcards.util.UserType;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;

/**
 * Ova komponenta služi za prikazivanje tipa korisnika. Pošto su tipovi sačuvani
 * kao integer podaci, prevode se u String reprezentaciju i prikazuju.
 *
 * @author Bogdan Begovic
 */
public class ShowRoleType {

    @Parameter(required = true)
    private long userRoleType;
    @Parameter
    @Property
    private String result;

    @SetupRender
    void initializeValues() {

        if (userRoleType == UserType.ADMIN.getCode()) {
            result = "Admin";
        } else if (userRoleType == UserType.MODERATOR.getCode()) {
            result = "Moderator";
        } else if (userRoleType == UserType.CONTRIBUTOR.getCode()) {
            result = "Contributor";
        } else {
            result = "User";
        }
    }
}
