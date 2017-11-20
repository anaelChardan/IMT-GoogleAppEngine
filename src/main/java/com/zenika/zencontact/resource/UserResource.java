package com.zenika.zencontact.resource;

import com.google.gson.Gson;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.datastore.UserDaoDataStore;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResource", value = "/api/v0/users")
public class UserResource extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(new Gson().toJsonTree(UserDaoObjectify.getInstance().getAll()).getAsJsonArray());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        User user = new Gson().fromJson(request.getReader(), User.class);
        user.id(UserDaoObjectify.getInstance().save(user));

        response.setContentType("application/json; charset=utf-8");
        response.setStatus(201);
        response.getWriter().println(new Gson().toJson(user));
    }
}
