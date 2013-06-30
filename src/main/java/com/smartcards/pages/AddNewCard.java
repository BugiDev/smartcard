/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.pages;

import com.smartcards.entities.Card;
import com.smartcards.entities.Subject;
import com.smartcards.entities.User;
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
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Bogdan Begovic
 */
@Import(stylesheet = {"context:css/shCore.css", "context:css/cardStyle.css"}, library = {"context:js/jquery.min.js", "context:js/jquery.flippy.min.js", "context:js/shBrushXml.js", "context:js/shBrushJScript.js", "context:js/shCore.js"})
public class AddNewCard {

    private Logger logger = LoggerFactory.getLogger(Login.class);
    @Component(id = "cardForm")
    private Form cardForm;
    @InjectComponent
    private Zone cardPreviewZone;
    @InjectComponent
    private Zone cardPlaceholderZone;
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

    public void setupRender() {

        List<Subject> tmpCategories = hibernate.createCriteria(Subject.class).list();
        allCategories = new ArrayList<String>();
        for (Subject subject : tmpCategories) {
            allCategories.add(subject.getSubjectName());
        }

        isJSAdded = false;
    }

    @CommitAfter
    public void onSuccess() {
        if (isPreview) {
            if (isJSAdded == false) {
                ajaxResponseRenderer.addRender("cardPlaceholderZone", cardPlaceholderZone).addRender("cardPreviewBlock", cardPreviewBlock);
            } else {
                ajaxResponseRenderer.addRender("cardPlaceholderZone", cardPlaceholderZone);
            }
        } else {

            card.setCardQuestion(cardQuestion);
            card.setCardAnswer(cardAnswer);
            card.setCardNumRaters(0);
            card.setCardRatingTotal(0);
            card.setCardStatus(1);
            card.setSubject((Subject) hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectName", selectCategory.toString())).uniqueResult());
            card.setUser((User) hibernate.createCriteria(User.class).add(Restrictions.eq("userID", 1L)).uniqueResult());
            hibernate.save(card);
            hibernate.flush();
        }
    }

    public void onSelectedFromPreviewCard() {
        isPreview = true;
    }

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
}
