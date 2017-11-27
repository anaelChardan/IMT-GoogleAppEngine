package com.zenika.zencontact.resource;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.domain.blob.PhotoService;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResourceWithId", value = "/api/v0/users/*")
public class UserResourceWithId extends HttpServlet {

  private static final Logger LOG = Logger.getLogger(UserResourceWithId.class.getName());
  private static final String CONTACTS_CACHE_KEY = "com.zenika.zencontact.cache";
  private static final MemcacheService cache = MemcacheServiceFactory.getMemcacheService();

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
    User user = UserDaoObjectify.getInstance().get(id);
    PhotoService.getInstance().prepareUploadUrl(user);
    PhotoService.getInstance().prepareDownloadUrl(user);
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
    UserDaoObjectify.getInstance().save(user);
    cache.delete(CONTACTS_CACHE_KEY);
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
    cache.delete(CONTACTS_CACHE_KEY);
    UserDaoObjectify.getInstance().delete(id);
  }
}

