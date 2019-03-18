package com.mini.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//Servlet to logout and erase all credentials

@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class logout extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("user_id");
        session.invalidate();
        response.sendRedirect("index.jsp");
    }
}
