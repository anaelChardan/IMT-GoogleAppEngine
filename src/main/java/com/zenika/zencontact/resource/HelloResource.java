package com.zenika.zencontact.resource;

import com.zenika.zencontact.domain.Message;
import org.joda.time.DateTime;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloResource", value = "/api/message")
public class HelloResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String who = request.getParameter("who");
    response.setContentType("application/json; charset=utf-8");
    response.getWriter().println(new Gson().toJson(sayHello(who)));
  }

  public Message sayHello(String who) {
    Message msg = new Message();
    msg.message = String.format(
            "hello %s, it's %s",
            who, DateTime.now().toString("HH:mm:ss"));
    return msg;
  }
}
