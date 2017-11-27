package com.zenika.zencontact.email;

import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.resource.auth.AuthenticationService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
                    new InternetAddress("team@imt-2017-anael.appspotmail.com",
                            "Application team")});
            msg.setSubject(email.subject);
            msg.setText(email.body);

            Transport.send(msg);
            LOG.warning("mail envoyé!");
        } catch (UnsupportedEncodingException | MessagingException ignored) {}
    }

    public void handleEmail(HttpServletRequest req) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session, req.getInputStream());
            LOG.warning("Subject:" + msg.getSubject());
            Multipart multipart = (Multipart) msg.getContent();
            BodyPart bodyPart = multipart.getBodyPart(0);
            LOG.warning("Body" + bodyPart.getContent());

            Arrays.stream(msg.getFrom()).forEach(e -> LOG.warning("From: " + e.toString()));

            Transport.send(msg);
            LOG.warning("mail envoyé!");
        } catch (MessagingException | IOException ignored) {}
    }

}
