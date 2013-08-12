/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.pages;

import com.smartcards.entities.Subject;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Environmental;
import org.apache.tapestry5.annotations.InjectComponent;
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

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR})
public class AddNewSubject {

    @Component(id = "subjectForm")
    private Form subjectForm;
    @InjectComponent
    private Zone messageZoneSubject;
    @Inject
    private Request request;
    @Environmental
    private JavaScriptSupport javaScriptSupport;
    @Persist(PersistenceConstants.FLASH)
    @Validate("required")
    @Property
    private String subjectInput;
    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
    @Property
    @Inject
    private Session hibernate;
    @SessionState
    @Property
    private Subject subject;
    @Property
    private String messageText;
    @Property
    private String cssClass;
    @Inject
    private Messages messages;
    @SessionState
    @Property
    private User asoUser;

    @CommitAfter
    public void onSuccessFromSubjectForm() {

        try {
            
            subject = new Subject();
            subject.setSubjectName(subjectInput);
            hibernate.save(subject);
            hibernate.flush();

            messageText = messages.get("messageSuccess");
            cssClass = "messageSuccess";
            ajaxResponseRenderer.addRender("messageZoneSubject", messageZoneSubject);

        } catch (HibernateException e) {
            messageText = messages.get("messageError");
            cssClass = "messageError";
            ajaxResponseRenderer.addRender("messageZoneSubject", messageZoneSubject);
        }

    }

    public Object onSelectedFromResetSubject() {
        subjectInput = null;
        return this;

    }

    public void onAddJSMessage() {
        javaScriptSupport.addScript(" \n"
                + "        \n"
                + "        $(\".close_btn_message\").on(\"click\", function(e) {\n"
                + "    \n"
                + "            $(\"#messageZoneSubject\").fadeOut();\n"
                + "  $(\".close_btn_message\").hide(); \n"
                + "        });");
    }
}
