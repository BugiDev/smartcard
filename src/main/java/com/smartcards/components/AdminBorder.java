package com.smartcards.components;

import com.smartcards.entities.User;
import com.smartcards.pages.Login;
import com.smartcards.util.UserType;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 * Layout component for pages of application smartcards.
 */
@Import(stylesheet = {"context:css/layout.css", "context:css/tooltip.css"},
        library = {"context:js/hideshow.js", "context:js/jquery.tablesorter.min.js", "context:js/jquery.equalHeight.js", "context:js/jquery.index.documentLoad.js"})
public class AdminBorder {

    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String pageTitle;
    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String tabTitle;
    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String breadcrumb;
    @Property
    @Parameter(required = true, defaultPrefix = "literal")
    private String username;
    @SessionState
    @Property
    private User asoUser;
    @InjectPage
    private Login login;

    public boolean getTestIsAdmin() {
        if (asoUser.getRoleType() == UserType.ADMIN.getCode()) {
            return true;
        }
        return false;
    }

    public boolean getTestIsModerator() {
        if (asoUser.getRoleType() == UserType.MODERATOR.getCode()) {
            return true;
        }
        return false;
    }

    public Object onLogout() {
        asoUser = null;
        return login;
    }
}
