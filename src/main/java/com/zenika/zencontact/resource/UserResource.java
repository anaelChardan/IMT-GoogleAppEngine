package com.zenika.zencontact.resource;

import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserRepository;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResource", value = "/api/v0/users")
public class UserResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json; charset=utf-8");
    response.getWriter().println(new Gson().toJsonTree(UserRepository.USERS).getAsJsonArray());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    User user = new Gson().fromJson(request.getReader(), User.class);
    if(user.id == null) {
        user.id = Long.valueOf(UserRepository.USERS.size());
    }
    UserRepository.USERS.add(user);
    response.setContentType("application/json; charset=utf-8");
    response.setStatus(201);
    response.getWriter().println(new Gson().toJson(user));
  }
}
