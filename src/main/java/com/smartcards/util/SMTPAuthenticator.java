package com.smartcards.util;

import javax.mail.PasswordAuthentication;

/**
 * Klasa SMTPAuthenticator koja nasleđuje javax.mail.Authenticator i zaslužna je za autentikaciju mail servisa.
 * @author Bogdan Begovic
 */
public class SMTPAuthenticator extends javax.mail.Authenticator{
    private String mail = "bogdanbegovic@gmail.com";
    private String password = "qgqyaswxyewuxqid";
    
     /**
     * Metoda koja vraća objekat tipa PasswordAuthentication sa podacima o emailu i šifri.
     * @return
     */
    @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(mail, password);
        }
}
