package com.smartcards.components;

import com.smartcards.entities.User;
import com.smartcards.pages.AddNewCard;
import com.smartcards.pages.AddNewSubject;
import com.smartcards.pages.Login;
import com.smartcards.pages.NewCardsUsers;
import com.smartcards.pages.SelectCardEdit;
import com.smartcards.pages.SelectSubjectEdit;
import com.smartcards.pages.SelectUserEdit;
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

    @SessionState
    @Property
    private User asoUser;
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
    private String username = asoUser.getFirstname() + " " + asoUser.getLastname();
    @InjectPage
    private Login login;
    @InjectPage
    private AddNewCard addNewCardPage;
    @InjectPage
    private NewCardsUsers newCardsUsers;
    @InjectPage
    private SelectCardEdit selectCardEdit;
    @InjectPage
    private SelectUserEdit selectUserEdit;
    @InjectPage
    private AddNewSubject addNewSubject;
    @InjectPage
    private SelectSubjectEdit selectSubjectEdit;

    public boolean getTestIsAdmin() {
        if (asoUser.getRoleType() == UserType.ADMIN.getCode()) {
            return true;
        }
        return false;
    }

    public boolean getTestIsAdminOrModerator() {
        if ((asoUser.getRoleType() == UserType.ADMIN.getCode()) || (asoUser.getRoleType() == UserType.MODERATOR.getCode())) {
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

    public Object onAsideMenuClick(String menuType) {
        if (menuType.equalsIgnoreCase("newCard")) {

            return addNewCardPage;
        }
        if (menuType.equalsIgnoreCase("selectCardEdit")) {

            return selectCardEdit;
        }
        if (menuType.equalsIgnoreCase("selectUserEdit")) {

            return selectUserEdit;
        }
        if (menuType.equalsIgnoreCase("newSubject")) {

            return addNewSubject;
        }
        if (menuType.equalsIgnoreCase("editSubject")) {

            return selectSubjectEdit;
        }
        return null;
    }

    public Object onGoHome() {
        return newCardsUsers;
    }
}
