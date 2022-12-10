package controller;

/**
 * This class to send email from our app account maqadeer.app@gmail.com Using
 * Java Mail API. To download: https://javaee.github.io/javamail/
 *
 * @Auther This code is from: https://www.youtube.com/watch?v=4dsrrMGKY6g with
 * some changes
 */

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import javax.mail.Authenticator;


public class Email {
    
    final static String SENDER_EMAIL = "maqadeer.app@gmail.com";
    final static String SENDER_PASSWORD = "kbhfyzlihsfhosia";
    final static String MAIL_SERVER_HOST = "smtp.gmail.com";
    final static String MAIL_SERVER_PORT = "465";

    private static Properties setupServer() {
        Properties props = new Properties();
        props.put("mail.smtp.user", SENDER_EMAIL);
        props.put("mail.smtp.host", MAIL_SERVER_HOST);
        props.put("mail.smtp.port", MAIL_SERVER_PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", MAIL_SERVER_PORT);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        System.getSecurityManager();
        return props;
    }

    private static Session createSession(Properties props) {
        return Session.getInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });
    }

    private static MimeMessage createMessage(Session session, String to,
            String subject, String body) throws MessagingException {

        MimeMessage msg = new MimeMessage(session);
        msg.setSubject(subject);
        msg.setText(body);
        msg.setFrom(new InternetAddress(SENDER_EMAIL));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        return msg;
    }

    public static void sendTo(String reciver, String subject, String body) {
        try {
            Properties server = setupServer();
            Session session = createSession(server);
            MimeMessage msg = createMessage(session, reciver, subject, body);
            Transport.send(msg);
            System.out.println("Message sent successfully");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static String[] generate_rand() {
        //int []  randomNumber=new int [5];
        String[] rand = new String[5];

        Random randomObj = new Random();
        //for loop
        for (int i = 0; i < rand.length; i++) {
            rand[i] = String.valueOf(new Random().nextInt(10));
        }
        return rand;
    }
}