
package com.smartcards.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Klasa Card predstavlja entitet koji će biti sačuvan u bazi.
 * Implementira interface Serializable koji je neophodan za serializaciju podataka pri čuvanju i čitanju podataka iz baze.
 * @author Bogdan Begovic
 */
@Entity
public class Card implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "Card_ID")
    private Long cardID;
    @Basic(optional = false)
    @Column(name = "Card_question")
    private String cardQuestion;
    @Basic(optional = false)
    @Column(name = "Card_answer")
    private String cardAnswer;
    @Column(name = "Card_rating_total")
    private float cardRatingTotal;
    @Basic(optional = false)
    @Column(name = "Card_num_raters")
    private float cardNumRaters;
    @Basic(optional = false)
    @Column(name = "Card_status")
    private int cardStatus;
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name="Subject_ID")
    private Subject subject;
    
    /**
     * Prazan konstruktor
     */
    public Card() {
    }

      @Override
    public int hashCode() {
        int hash = 0;
        hash += (getCardID() != null ? getCardID().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Card)) {
            return false;
        }
        Card other = (Card) object;
        if ((this.getCardID() == null && other.getCardID() != null) || (this.getCardID() != null && !this.cardID.equals(other.cardID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Object ID = "+cardID.toString();
    }
    
    /**
     * Standardni getter.
     * @return the cardID
     */
    public Long getCardID() {
        return cardID;
    }

    /**
     * Standardni setter.
     * @param cardID the cardID to set
     */
    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    /**
     * Standardni getter.
     * @return the cardQuestion
     */
    public String getCardQuestion() {
        return cardQuestion;
    }

    /**
     * Standardni setter.
     * @param cardQuestion the cardQuestion to set
     */
    public void setCardQuestion(String cardQuestion) {
        this.cardQuestion = cardQuestion;
    }

    /**
     * Standardni getter.
     * @return the cardAnswer
     */
    public String getCardAnswer() {
        return cardAnswer;
    }

    /**
     * Standardni setter.
     * @param cardAnswer the cardAnswer to set
     */
    public void setCardAnswer(String cardAnswer) {
        this.cardAnswer = cardAnswer;
    }

    /**
     * Standardni getter.
     * @return the cardRatingTotal
     */
    public float getCardRatingTotal() {
        return cardRatingTotal;
    }

    /**
     * Standardni setter.
     * @param cardRatingTotal the cardRatingTotal to set
     */
    public void setCardRatingTotal(float cardRatingTotal) {
        this.cardRatingTotal = cardRatingTotal;
    }

    /**
     * Standardni getter.
     * @return the cardNumRaters
     */
    public float getCardNumRaters() {
        return cardNumRaters;
    }

    /**
     * Standardni setter.
     * @param cardNumRaters the cardNumRaters to set
     */
    public void setCardNumRaters(float cardNumRaters) {
        this.cardNumRaters = cardNumRaters;
    }

    /**
     * Standardni getter.
     * @return the cardStatus
     */
    public int getCardStatus() {
        return cardStatus;
    }

    /**
     * Standardni setter.
     * @param cardStatus the cardStatus to set
     */
    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    /**
     * Standardni getter.
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Standardni setter.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Standardni getter.
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * Standardni setter.
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
}
