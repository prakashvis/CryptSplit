/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mini.user;

import com.mini.dao.Language;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author prakash-pt2590
 */
@WebServlet(name = "ChangeLanguage", urlPatterns = {"/ChangeLanguage"})
public class ChangeLanguage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("ChangeLanguage");
        String lang = request.getParameter("lang");
        String user_id = request.getParameter("user_id");
        System.out.println("user-id: "+user_id);
        Language.changeLanguageInDb(lang, user_id);
    }
}
