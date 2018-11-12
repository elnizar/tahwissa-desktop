/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author BRAHMI
 */
public class SendMail {
    
    
  
    String adressMail="mouhamedamine.brahmi@esprit.tn", ContenuMail, subject;

    public void envoyé(String adressMail, String ContenuMail, String subject) {

        boolean debug = false;
         adressMail="mouhamedamine.brahmi@esprit.tn";

        //Set the host smtp address
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "mouhamedamine.brahmi@esprit.tn");
        props.put("mail.smtp.password", "nikomek123");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getDefaultInstance(props, null);
        session.setDebug(debug);

        Message msg = new MimeMessage(session);

        InternetAddress addressFrom;
        try {
            addressFrom = new InternetAddress("mouhamedamine.brahmi@esprit.tn");

            InternetAddress addressTo = new InternetAddress(adressMail);

            msg.setRecipient(Message.RecipientType.TO, addressTo);
            msg.setFrom(addressFrom);

            msg.setSubject(subject);
            msg.setText(ContenuMail);
            Transport t = session.getTransport("smtp");
            t.connect("smtp.gmail.com", "mouhamedamine.brahmi@esprit.tn", "nikomek123");
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    
}
    

