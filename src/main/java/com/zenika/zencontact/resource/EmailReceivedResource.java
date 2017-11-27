package com.zenika.zencontact.resource;

import com.zenika.zencontact.email.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmailReceivedResource", value = "/_ah/mail/*")
@ServletSecurity(@HttpConstraint(rolesAllowed = {"admin"}))
public class EmailReceivedResource extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmailService.getInstance().handleEmail(req);
    }
}
