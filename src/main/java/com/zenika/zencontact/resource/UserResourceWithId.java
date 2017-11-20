package com.zenika.zencontact.resource;

import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserRepository;
import com.google.gson.Gson;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResourceWithId", value = "/api/v0/users/*")
public class UserResourceWithId extends HttpServlet {

  private Long getId(HttpServletRequest request) {
    String pathInfo = request.getPathInfo(); // /{id}
    String[] pathParts = pathInfo.split("/");
    if(pathParts.length == 0) {
        return null;
    }
    return Long.valueOf(pathParts[1]); // {id}
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    Iterable<User> users = UserRepository.USERS;
    Predicate<User> getUserById = new Predicate<User>() {
        public boolean apply(User user) {
            return user.id == id;
        }
    };
    User user = Iterables.find(users, getUserById, null);
    response.setContentType("application/json; charset=utf-8");
    response.getWriter().println(new Gson().toJson(user));
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    User user = new Gson().fromJson(request.getReader(), User.class);
    Iterable<User> users = UserRepository.USERS;
    Predicate<User> getUserById = new Predicate<User>() {
        public boolean apply(User user) {
            return user.id == id;
        }
    };
    UserRepository.USERS.set(Iterables.indexOf(users, getUserById), user);
    response.setContentType("application/json; charset=utf-8");
    response.getWriter().println(new Gson().toJson(user));
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    Iterable<User> users = UserRepository.USERS;
    Predicate<User> getUserById = new Predicate<User>() {
        public boolean apply(User user) {
            return user.id == id;
        }
    };
    UserRepository.USERS.remove(Iterables.indexOf(users, getUserById));
  }
}

