package com.smartcards.util;

/**
 * Enum UserType koji predstavlja String reprezentaciju tipova korisnika.
 *
 * @author Bogdan Begovic
 */
public enum UserType {

    /**
     * Postavljanje kodova za sve korisnike.
     */
    USER(1),
    ADMIN(2),
    MODERATOR(3),
    CONTRIBUTOR(4);
    private int code;

    /*
     * Privatni konstruktor koji prima kodove i spaja ih sa odgovarajućom String reprezentacijom.
     */
    private UserType(int c) {
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
