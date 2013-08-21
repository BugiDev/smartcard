
package com.smartcards.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Klasa Subject predstavlja entitet koji će biti sačuvan u bazi.
 * Implementira interface Serializable koji je neophodan za serializaciju podataka pri čuvanju i čitanju podataka iz baze.
 * @author Bogdan Begovic
 */
@Entity
public class Subject implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "Subject_ID")
    private Long subjectID;
    @Basic(optional = false)
    @Column(name = "Subject_name")
    private String subjectName;
    @Basic(optional = false)
    @Column(name = "Subject_deleted")
    private boolean subjectDeleted;
    @OneToMany(mappedBy = "subject")
    private Set<Card> card;

    /**
     *
     */
    public Subject() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getSubjectID() != null ? getSubjectID().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.getSubjectID() == null && other.getSubjectID() != null) || (this.getSubjectID() != null && !this.subjectID.equals(other.subjectID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Object ID = " + subjectID.toString();
    }

    /**
     * Standardni getter.
     * @return the subjectID
     */
    public Long getSubjectID() {
        return subjectID;
    }

    /**
     * Standardni setter.
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(Long subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * Standardni getter.
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Standardni setter.
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Standardni getter.
     * @return the card
     */
    public Set<Card> getCard() {
        return card;
    }

    /**
     * Standardni setter.
     * @param card the card to set
     */
    public void setCard(Set<Card> card) {
        this.card = card;
    }

    /**
     * @return the subjectDeleted
     */
    public boolean isSubjectDeleted() {
        return subjectDeleted;
    }

    /**
     * Standardni setter.
     * @param subjectDeleted the subjectDeleted to set
     */
    public void setSubjectDeleted(boolean subjectDeleted) {
        this.subjectDeleted = subjectDeleted;
    }
}
