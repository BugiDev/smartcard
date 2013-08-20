package com.smartcards.pages;

import com.smartcards.entities.Subject;
import com.smartcards.entities.User;
import com.smartcards.services.ProtectedPage;
import com.smartcards.util.UserType;
import java.util.List;
import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
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
 * Klasa koja služi za prikaz i selektovanje kategorija koji se edituju. Pravo
 * pristupa imaju samo ADMIN i MODERATOR korisnici. Klasa takođe dodaje i
 * javascript klasu EditSubjectForDelete.js kako bi omogućila selekciju
 * korisnika za brisanje.
 *
 * @author Bogdan Begovic
 */
@ProtectedPage(getRoles = {UserType.ADMIN, UserType.MODERATOR})
@Import(library = {"context:js/dialog/EditSubjectForDelete.js"})
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

    /*
     * Metoda koja se poziva pri svakom renderovanju strane.
     * Koristi se da kreira i napuni grid sa podacima.
     */
    void setupRender() {

        subjectModel = beanModelSource.createDisplayModel(Subject.class, messages);
        subjectModel.add("action", null);

        subjectModel.include("subjectName", "action");
        subjectModel.get("subjectName").sortable(false);
        // Get all persons - ask business service to find them (from the database)

        subjects = hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectDeleted", false)).list();

    }

    /**
     * Metoda kojom se bira entitet za editovanje, a njegov ID prosleđuje se
     * stranici za editovanje.
     *
     * @param subjectID
     * @return Page object
     */
    public Object onEditSubject(long subjectID) {
        editSubjectPage.setInitialDataToEdit(subjectID);
        return editSubjectPage;
    }

    /**
     * Metoda koja handle-uje submit na formi. Zaslužna je za brisanje
     * kategorija.
     *
     * @return istu stranicu (refresh)
     */
    @CommitAfter
    public Object onSubmitFromDeleteForm() {
        try {
            Subject subject = (Subject) hibernate.createCriteria(Subject.class).add(Restrictions.eq("subjectID", selectedSubjectID)).uniqueResult();
            subject.setSubjectDeleted(true);
            hibernate.update(subject);
            hibernate.flush();
        } catch (HibernateException e) {
            return null;
        }
        return this;
    }

    /**
     * Metoda kojom se bira entitet za brisanje. Postavlja ID entiteta.
     *
     * @param selected
     */
    public void onSelectSubject(long selected) {
        selectedSubjectID = selected;
    }
}
