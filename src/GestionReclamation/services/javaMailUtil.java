/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestionReclamation.services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Louay
 */
public class javaMailUtil {
    public static void sendMail(String recepient, String username) {
        System.out.println("preparing to send mail");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "coachiniapp@gmail.com";
        String password = "coach2021";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        }
        );
        Message message =  prepareMessage(session,myAccountEmail,recepient,username);
        try {
            Transport.send(message);
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("message sent succefully!!!");
        
     }
    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String username) {
        try {
            Message message= new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Réclamation : Traité");
            message.setText("Salut M/Mme. "+username+" \nl'équipe de Coach'ini vous informe que l'une de vos réclamation "
                    + "qui était en cours de traitement vient d'être terminé.\nCordialement, Coach'ini");
            return message;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}
