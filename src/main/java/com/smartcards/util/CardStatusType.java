package com.smartcards.util;

/**
 * Enum CardStatusType koji predstavlja String reprezentaciju tipova statusa
 * kartice.
 *
 * @author Bogdan Begovic
 */
public enum CardStatusType {

    /**
     * Postavljanje kodova za sve statuse.
     */
    TRASHED(0),
    PENDING(1),
    APROVED(2);
    private int code;

    /*
     * Privatni konstruktor koji prima kodove i spaja ih sa odgovarajućom String reprezentacijom.
     */
    private CardStatusType(int c) {
        code = c;
    }

    /**
     * Metoda koja vraća kod
     *
     * @return code type of long
     */
    public int getCode() {
        return code;
    }
}
