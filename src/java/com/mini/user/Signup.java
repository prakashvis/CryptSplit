package com.mini.user;

import com.mini.dao.Authenticate;
import com.mini.dao.SignupDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//Servlet to add new user

@WebServlet("/Signup")
public class Signup extends HttpServlet { 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String newpass = request.getParameter("newpass");
        String conpass = request.getParameter("conpass");
        String language = request.getParameter("lang");

        if (!newpass.equals(conpass) || username == null || conpass == null) {
            request.getSession().setAttribute("signuperror", "notequal");
            response.sendRedirect("signup.jsp?lang="+language);
        }
        else if (Authenticate.isValidUser(username, conpass)) {
            request.getSession().setAttribute("signuperror", "alreadyexists");
            response.sendRedirect("signup.jsp?lang="+language);
        }
        else {
            SignupDao dao = new SignupDao();
            int user_id = dao.signup(username, conpass, language);
            if (user_id == -1) {
                request.getSession().setAttribute("signuperror", "alreadyexists");
                response.sendRedirect("signup.jsp");
            }
            else {
                request.getSession().setAttribute("signuperror", user_id);
                response.sendRedirect("login.jsp?lang=" + language);
            }
        }
    }
}
