/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.util;

/**
 *
 * @author Bogdan Begovic
 */
public enum CardStatusType {

    TRASHED(0),
    PENDING(1),
    APROVED(2);
    
    private int code;

    /*
     * Private constructor that takes the code and associate it with type of issue.
     */
    private CardStatusType(int c) {
        code = c;
    }

    /**
     * Method that gets the code
     *
     * @return code type of long
     */
    public int getCode() {
        return code;
    }
}
