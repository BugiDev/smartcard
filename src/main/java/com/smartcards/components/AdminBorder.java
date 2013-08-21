package com.smartcards.components;

import com.smartcards.entities.User;
import com.smartcards.pages.AddNewCard;
import com.smartcards.pages.AddNewSubject;
import com.smartcards.pages.AddNewUser;
import com.smartcards.pages.Login;
import com.smartcards.pages.NewCardsUsers;
import com.smartcards.pages.SelectCardEdit;
import com.smartcards.pages.SelectSubjectEdit;
import com.smartcards.pages.SelectUserEdit;
import com.smartcards.pages.YourProfile;
import com.smartcards.util.UserType;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;

/**
 * Layout komponenta koja predstavlja kostur svih stranica. Odgovorna je za
 * menije i za navigaciju izmedju stranica.
 *
 * @author Bogdan Begovic
 */
@Import(stylesheet = {"context:css/layout.css", "context:css/tooltip.css"},
        library = {"context:js/hideshow.js", "context:js/jquery.tablesorter.min.js", "context:js/jquery.equalHeight.js", "context:js/jquery.index.documentLoad.js", "context:js/Constants.js"})
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
    @InjectPage
    private AddNewUser addNewUser;
    @InjectPage
    private YourProfile yourProfile;

    /**
     * Metoda koja proverava da li ulogovani korisnik ima rolu admin i u
     * zavisnosti od toga, vraća boolean vrednost.
     *
     * @return boolean
     */
    public boolean getTestIsAdmin() {
        if (asoUser.getRoleType() == UserType.ADMIN.getCode()) {
            return true;
        }
        return false;
    }

    /**
     * Metoda koja proverava da li ulogovani korisnik ima rolu admin ili
     * moderator i u zavisnosti od toga, vraća boolean vrednost.
     *
     * @return boolean
     */
    public boolean getTestIsAdminOrModerator() {
        if ((asoUser.getRoleType() == UserType.ADMIN.getCode()) || (asoUser.getRoleType() == UserType.MODERATOR.getCode())) {
            return true;
        }
        return false;
    }

    /**
     * Metoda koja proverava da li ulogovani korisnik ima rolu moderator i u
     * zavisnosti od toga, vraća boolean vrednost.
     *
     * @return boolean
     */
    public boolean getTestIsModerator() {
        if (asoUser.getRoleType() == UserType.MODERATOR.getCode()) {
            return true;
        }
        return false;
    }

    /**
     * Metoda koja služi da izloguje korisnika, odnosno da obriše sačuvanog
     * korisnika u sesiji i da redirect-uje na login stranu.
     *
     * @return
     */
    public Object onLogout() {
        asoUser = null;
        return login;
    }

    /**
     * Metoda koja handle-uje click iz aside menija. U zavisnosti od toga koji
     * parametar je prosledjen (ime stranice), redirektuje korisnika na tu
     * stranicu.
     *
     * @param menuType type of String
     * @return Page object
     */
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
        if (menuType.equalsIgnoreCase("newUser")) {

            return addNewUser;
        }
        if (menuType.equalsIgnoreCase("yourProfile")) {

            return yourProfile;
        }
        return null;
    }

    /**
     * Metoda koja redirect-uje na home stranicu.
     *
     * @return Page object
     */
    public Object onGoHome() {
        return newCardsUsers;
    }
}
