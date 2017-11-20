package com.zenika.zencontact.resource;

import com.google.appengine.api.blobstore.BlobKey;
import com.zenika.zencontact.domain.blob.PhotoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PhotoResource", value = "/api/v0/photo/*")
public class PhotoResource extends HttpServlet {
    private Long getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo(); // /{id}
        String[] pathParts = pathInfo.split("/");
        if(pathParts.length == 0) {
            return null;
        }
        return Long.valueOf(pathParts[1]); // {id}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = getId(req);
        if(id == null) {
            resp.setStatus(404);
            return;
        }

        PhotoService.getInstance().updatePhoto(id, req);
        resp.setContentType("text/plain");
        resp.getWriter().println("ok");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo(); // /{id}
        String[] pathParts = pathInfo.split("/");
        if(pathParts.length == 0) {
            resp.setStatus(404);
            return;
        }
        Long id = Long.valueOf(pathParts[1]); // {id}
        String blobKey = pathParts[2];

        PhotoService.getInstance().serve(new BlobKey(blobKey), resp);
    }
}
