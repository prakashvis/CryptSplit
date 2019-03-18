package com.mini.user;

import com.mini.bean.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mini.dao.Authenticate;
import com.mini.dao.Language;

//Servlet to verify login

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = (String)request.getParameter("username");
        String password = request.getParameter("password");
        String language = request.getParameter("lang");
        
        System.out.println(username + " " + password);

        if (Authenticate.isValidUser(username, password)) {
            User user = new User();
            user.setUserName(username);
            user.setUser_id(Authenticate.getUser_id(username));
            System.out.println(username);
            System.out.println(user.getUser_id());
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user_id", user.getUser_id());
            String lang = Language.getLanguage(user.getUser_id());
            session.setAttribute("lang", lang);
            session.setAttribute("loginerror", "");
            response.sendRedirect("selectFile.jsp?lang="+lang);
        } else {
            request.getSession().setAttribute("loginerror", "Incorrect User ID or password");
            response.sendRedirect("login.jsp?lang="+language);
        }
    }
}
