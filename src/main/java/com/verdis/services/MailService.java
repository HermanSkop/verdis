package com.verdis.services;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;
import jakarta.mail.*;

@Service
public class MailService {
    private final Session mailSession;

    public MailService(Session mailSession) {
        this.mailSession = mailSession;
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("herman.skapets@innowise.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
