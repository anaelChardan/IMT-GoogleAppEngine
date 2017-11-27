package com.zenika.zencontact.resource;

import com.google.gson.Gson;
import com.zenika.zencontact.domain.Email;
import com.zenika.zencontact.email.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EmailToSendResource", value = "/api/v0/email")
public class EmailToSendResource extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Email email = new Gson().fromJson(req.getReader(), Email.class);
        EmailService.getInstance().sendEmail(email);
    }
}
