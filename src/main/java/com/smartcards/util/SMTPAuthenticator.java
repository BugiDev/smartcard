/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.util;

import javax.mail.PasswordAuthentication;

/**
 *
 * @author Bogdan Begovic
 */
public class SMTPAuthenticator extends javax.mail.Authenticator{
    private String mail = "bogdanbegovic@gmail.com";
    private String password = "qgqyaswxyewuxqid";
    
     @Override
        public PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication(mail, password);
        }
}
