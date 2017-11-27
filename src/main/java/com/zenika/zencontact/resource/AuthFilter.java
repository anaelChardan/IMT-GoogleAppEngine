package com.zenika.zencontact.resource;

import com.zenika.zencontact.resource.auth.AuthenticationService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"api/v0/users/*"})
public class AuthFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String pathInfo = request.getPathInfo();
        if (null != pathInfo) {
            String[] pathParts = pathInfo.split("/");

            if ("DELETE".equals(request.getMethod()) && !AuthenticationService.getInstance().isAdmin()) {
                response.setStatus(403);
                return;
            }

            if (AuthenticationService.getInstance().isAuthenticated() && null != AuthenticationService.getInstance().getUsername()) {
                response.setHeader("username", AuthenticationService.getInstance().getUsername());
                response.setHeader("Logout", AuthenticationService.getInstance().getLogoutURL("/#/clear"));
            } else {
                response.setHeader("Location", AuthenticationService.getInstance().getLoginURL("/#/edit/" + pathParts[1]));
                response.setHeader("Logout", AuthenticationService.getInstance().getLogoutURL("/#/clear"));
                response.setStatus(401);
            }
        }



        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
