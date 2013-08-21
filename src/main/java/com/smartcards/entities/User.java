
package com.smartcards.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 * Klasa Subject predstavlja entitet koji će biti sačuvan u bazi.
 * Implementira interface Serializable koji je neophodan za serializaciju podataka pri čuvanju i čitanju podataka iz baze.
 * @author Bogdan Begovic
 */
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "User_ID")
    private Long userID;
    @Basic(optional = false)
    @Column(name = "Username")
    private String username;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Basic(optional = false)
    @Column(name = "Firstname")
    private String firstname;
    @Basic(optional = false)
    @Column(name = "Lastname")
    private String lastname;
    @Basic(optional = false)
    @Column(name = "Birthday")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    @Basic(optional = false)
    @Column(name = "Role_type")
    private int roleType;
    @Basic(optional = false)
    @Column(name = "User_confirmed")
    private boolean userConfirmed;
    @Basic(optional = true)
    @Column(name = "Last_loged_in")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date lastLogedIn;
    @Basic(optional = false)
    @Column(name = "User_active")
    private boolean userActive;
    @OneToMany(mappedBy = "user")
    private Set<Card> card;

    /**
     * Prazan konstruktor
     */
    public User() {
    }

    /**
     * Konstruktor koji prima podatke.
     * @param username
     * @param password
     * @param email
     * @param firstname
     * @param lastname
     * @param birthday
     * @param roleType
     * @param userConfirmed
     * @param lastLogedIn
     * @param userActive
     */
    public User(String username, String password, String email, String firstname, String lastname, Date birthday, int roleType, boolean userConfirmed, Date lastLogedIn, boolean userActive) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.roleType = roleType;
        this.userConfirmed = userConfirmed;
        this.lastLogedIn = lastLogedIn;
        this.userActive = userActive;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getUserID() != null ? getUserID().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.getUserID() == null && other.getUserID() != null) || (this.getUserID() != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Object ID = " + userID.toString();
    }

    /**
     * Standardni getter.
     * @return the userID
     */
    public Long getUserID() {
        return userID;
    }

    /**
     * Standardni setter.
     * @param userID the userID to set
     */
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    /**
     * Standardni getter.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Standardni setter.
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Standardni getter.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Standardni setter.
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Standardni getter.
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Standardni setter.
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Standardni getter.
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Standardni setter.
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Standardni getter.
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Standardni setter.
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Standardni getter.
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Standardni setter.
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Standardni getter.
     * @return the roleType
     */
    public int getRoleType() {
        return roleType;
    }

    /**
     * Standardni setter.
     * @param roleType the roleType to set
     */
    public void setRoleType(int roleType) {
        this.roleType = roleType;
    }

    /**
     * Standardni getter.
     * @return the userConfirmed
     */
    public boolean getUserConfirmed() {
        return userConfirmed;
    }

    /**
     * Standardni setter.
     * @param userConfirmed the userConfirmed to set
     */
    public void setUserConfirmed(boolean userConfirmed) {
        this.userConfirmed = userConfirmed;
    }

    /**
     * Standardni getter.
     * @return the lastLogedIn
     */
    public Date getLastLogedIn() {
        return lastLogedIn;
    }

    /**
     * Standardni setter.
     * @param lastLogedIn the lastLogedIn to set
     */
    public void setLastLogedIn(Date lastLogedIn) {
        this.lastLogedIn = lastLogedIn;
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
     * Standardni getter.
     * @return the userActive
     */
    public boolean isUserActive() {
        return userActive;
    }

    /**
     * Standardni setter.
     * @param userActive the userActive to set
     */
    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }
}
