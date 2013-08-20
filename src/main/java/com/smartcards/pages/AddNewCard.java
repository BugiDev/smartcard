package com.smartcards.pages;

import com.smartcards.entities.Card;
import com.smartcards.entities.Subject;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;
import java.util.ArrayList;
import java.util.List;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
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
 * Klasa AddNewCard koja je zaslužna za prikaz i logiku AddNewCard strane. Služi
 * za dodavanje kartica.
 *
 * @author Bogdan
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR, UserType.CONTRIBUTOR})
@Import(stylesheet = {"context:css/shCore.css", "context:css/cardStyle.css", "context:css/tooltip.css"}, library = {"context:js/jquery.min.js", "context:js/jquery.flippy.min.js", "context:js/shBrushXml.js", "context:js/shBrushJScript.js", "context:js/shCore.js"})
public class AddNewCard {

    @Component(id = "cardForm")
    private Form cardForm;
    @InjectComponent
    private Zone cardPreviewZone;
    @InjectComponent
    private Zone cardPlaceholderZone;
    @InjectComponent
    private Zone messageZone;
    @Inject
    private Request request;
    @Inject
    private Block cardPreviewBlock;
    @Environmental
    private JavaScriptSupport javaScriptSupport;
    @Inject
    @Path("context:js/testFlip.js")
    private Asset scripts;
    @Persist(PersistenceConstants.FLASH)
    @Validate("required")
    @Property
    private String cardQuestion;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String cardAnswer;
    @Validate("required")
    @Persist(PersistenceConstants.FLASH)
    @Property
    private String selectCategory;
    @Property
    @Persist(PersistenceConstants.CLIENT)
    private List<String> allCategories;
    private boolean isPreview = false;
    @Persist(PersistenceConstants.SESSION)
    private boolean isJSAdded;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Property
    @Inject
    private Session hibernate;
    @SessionState
    @Property
    private Card card;
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
     * Koristi se da kreira i napuni listu svih kategorija.
     */
    public void setupRender() {

        List<Subject> tmpCategories = hibernate.createCriteria(Subject.class).list();
        allCategories = new ArrayList<String>();
        for (Subject subject : tmpCategories) {
            allCategories.add(subject.getSubjectName());
        }
        isJSAdded = false;
    }

    /**
     * Metoda koja handle-uje submit posle uspešne provere na formi. Zaslužna je
     * za editovanje kartica i vraćanje poruka o uspešnosti ili grešci.
     */
    @CommitAfter
    public void onSuccess() {
        if (isPreview) {
            if (isJSAdded == false) {
                ajaxResponseRenderer.addRender("cardPlaceholderZone", cardPlaceholderZone).addRender("cardPreviewBlock", cardPreviewBlock);
            } else {
                ajaxResponseRenderer.addRender("cardPlaceholderZone", cardPlaceholderZone);
            }
        } else {
            try {
                card.setCardQuestion(cardQuestion);
                card.setCardAnswer(cardAnswer);
                card.setCardNumRaters(0f);
                card.setCardRatingTotal(0f);
                card.setCardStatus(1);
                card.setSubject((Subject) hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectName", selectCategory.toString())).uniqueResult());
                card.setUser((User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", asoUser.getUserID())).uniqueResult());
                hibernate.save(card);
                hibernate.flush();

                messageText = messages.get("messageSuccess");
                cssClass = "messageSuccess";
                ajaxResponseRenderer.addRender("messageZone", messageZone);

                onSelectedFromResetCard();

            } catch (HibernateException e) {
                messageText = messages.get("messageError");
                cssClass = "messageError";
                ajaxResponseRenderer.addRender("messageZone", messageZone);
            }

        }
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za prikazivanje
     * preview komponente na strani.
     *
     */
    public void onSelectedFromPreviewCard() {
        isPreview = true;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za resetovanje
     * podataka.
     *
     * @return istu stranicu (refresh)
     */
    public Object onSelectedFromResetCard() {
        isPreview = false;
        cardQuestion = null;
        cardAnswer = null;
        return this;

    }

    /**
     * Metoda kojom se dodaje deo javascript koda zaslužnog za resize-ovanje
     * cele stranice.
     */
    public void onAddJS() {
        javaScriptSupport.importJavaScriptLibrary(scripts);
        javaScriptSupport.addScript(" $('aside').height($(document).height()-80);\n"
                + "	\n"
                + "		$('#main').height($(document).height()-90);"
                + "             \n"
                + "             $(window).resize(function() {\n"
                + "			$('aside').height($(document).height()-80);\n"
                + "		$('#main').height($(document).height()-90);});");
        isJSAdded = true;
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
}
