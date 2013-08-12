/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.pages;

import com.smartcards.components.ShowRoleType;
import com.smartcards.entities.Card;
import com.smartcards.entities.Subject;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.CardStatusType;
import com.smartcards.util.UserType;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.beaneditor.BeanModel;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.BeanModelSource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR})
public class SelectSubjectEdit {

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
    private List<Subject> subjects;
    @Property
    private Subject subject;
    @Property
    private BeanModel<Subject> subjectModel;
    @Inject
    private BeanModelSource beanModelSource;
    @InjectPage
    private EditSubject editSubjectPage;
    @Property
    @Persist(PersistenceConstants.FLASH)
    private long selectedSubjectID;
    @Component(id = "deleteForm")
    private Form deleteForm;

    // The code
    void setupRender() {

        subjectModel = beanModelSource.createDisplayModel(Subject.class, messages);
        subjectModel.add("action", null);

        subjectModel.include("subjectName", "action");
        subjectModel.get("subjectName").sortable(false);
        // Get all persons - ask business service to find them (from the database)

        subjects = hibernate.createCriteria(Subject.class).list();

    }

    public Object onEditSubject(long subjectID) {
        editSubjectPage.setInitialDataToEdit(subjectID);
        return editSubjectPage;
    }

    @CommitAfter
    public Object onSubmitFromDeleteForm() {
        try {
//            Subject subject = (Subject) hibernate.createCriteria(Subject.class).add(Restrictions.eq("cardID", selectedCardID)).uniqueResult();
////            subject.s;
            hibernate.update(subject);
            hibernate.flush();
        } catch (HibernateException e) {
            return null;
        }
        return this;
    }

    public void onSelectCard(long selected) {
        selectedSubjectID = selected;
        System.out.println("SELECTED ID: " + selected);
    }
}
