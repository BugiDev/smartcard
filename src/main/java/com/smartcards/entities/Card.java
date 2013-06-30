/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
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
    private int cardRatingTotal;
    @Basic(optional = false)
    @Column(name = "Card_num_raters")
    private int cardNumRaters;
    @Basic(optional = false)
    @Column(name = "Card_status")
    private int cardStatus;
    @ManyToOne
    @JoinColumn(name="User_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name="Subject_ID")
    private Subject subject;
    
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
     * @return the cardID
     */
    public Long getCardID() {
        return cardID;
    }

    /**
     * @param cardID the cardID to set
     */
    public void setCardID(Long cardID) {
        this.cardID = cardID;
    }

    /**
     * @return the cardQuestion
     */
    public String getCardQuestion() {
        return cardQuestion;
    }

    /**
     * @param cardQuestion the cardQuestion to set
     */
    public void setCardQuestion(String cardQuestion) {
        this.cardQuestion = cardQuestion;
    }

    /**
     * @return the cardAnswer
     */
    public String getCardAnswer() {
        return cardAnswer;
    }

    /**
     * @param cardAnswer the cardAnswer to set
     */
    public void setCardAnswer(String cardAnswer) {
        this.cardAnswer = cardAnswer;
    }

    /**
     * @return the cardRatingTotal
     */
    public int getCardRatingTotal() {
        return cardRatingTotal;
    }

    /**
     * @param cardRatingTotal the cardRatingTotal to set
     */
    public void setCardRatingTotal(int cardRatingTotal) {
        this.cardRatingTotal = cardRatingTotal;
    }

    /**
     * @return the cardNumRaters
     */
    public int getCardNumRaters() {
        return cardNumRaters;
    }

    /**
     * @param cardNumRaters the cardNumRaters to set
     */
    public void setCardNumRaters(int cardNumRaters) {
        this.cardNumRaters = cardNumRaters;
    }

    /**
     * @return the cardStatus
     */
    public int getCardStatus() {
        return cardStatus;
    }

    /**
     * @param cardStatus the cardStatus to set
     */
    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    
}
