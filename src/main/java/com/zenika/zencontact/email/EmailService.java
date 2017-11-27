package com.zenika.zencontact.email;

import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.resource.auth.AuthenticationService;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailService {
    private static EmailService ourInstance = new EmailService();
    private static Logger LOG = Logger.getLogger(EmailService.class.getName());

    public static EmailService getInstance() {
        return ourInstance;
    }

    private EmailService() {
    }

    public void sendEmail(Email email) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(
                    AuthenticationService.getInstance().getUser().getEmail(),
                    AuthenticationService.getInstance().getUsername()));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email.to, email.toName));

            msg.setReplyTo(new Address[]{
                    new InternetAddress("anael.chardan@gmail.com",
                            "Application team")});
            msg.setSubject(email.subject);
            msg.setText(email.body);

            Transport.send(msg);
            LOG.warning("mail envoyé!");
        } catch (UnsupportedEncodingException | MessagingException ignored) {}
    }
}
