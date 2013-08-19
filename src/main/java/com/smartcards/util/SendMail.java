/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartcards.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 *
 * @author Bogdan Begovic
 */

public class SendMail {
    
private boolean poslat;
    
 String  d_email = "bogdanbegovic@gmail.com",
            d_host = "smtp.gmail.com",
            d_port  = "465";
            

    public SendMail(String subject, String text, String mailTo)
    {
        Properties props = new Properties();
        props.put("mail.smtp.user", d_email);
        props.put("mail.smtp.host", d_host);
        props.put("mail.smtp.port", d_port);
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.port", d_port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        SecurityManager security = System.getSecurityManager();

        try
        {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(text);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(d_email));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            Transport.send(msg);
            setPoslat(true);
        }
        catch (Exception mex)
        {
            mex.printStackTrace();
            setPoslat(false);
        } 
    }

    /**
     * @return the poslat
     */
    public boolean isPoslat() {
        return poslat;
    }

    /**
     * @param poslat the poslat to set
     */
    public void setPoslat(boolean poslat) {
        this.poslat = poslat;
    }
}

