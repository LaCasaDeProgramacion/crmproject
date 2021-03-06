package crm.impl;

import java.util.Properties;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Mail_API {
    public static void sendMail(String to,String subject,String message) throws MessagingException
    {
        String host="smtp.gmail.com";
        final String user="crm.4twin1@gmail.com";//change accordingly
        final String password="CASA4twin1";//change accordingly


        //Get the session object
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.from",user);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        props.setProperty("mail.debug", "true");

        Session session = Session.getInstance(props, null);
        MimeMessage msg = new MimeMessage(session);

        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        Transport transport = session.getTransport("smtp");

        transport.connect(user, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();



    }
}