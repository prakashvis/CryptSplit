<%-- 
    Document   : decryptFile
    Created on : 4 Mar, 2019, 7:28:41 PM
    Author     : prakash-pt2590
--%>

<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="ISO-8859-1"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <c:import var="data" url="/language.json"/>
        <%

        String lang = request.getParameter("lang");
        System.out.println("login lang1: "+ lang);
        int index = -1;
        if(lang == null){
            lang="english";
            index=0;
        }
        else{
            if(lang.equals("english")) index = 0;
            if(lang.equals("japanese")) index = 1;
            if(lang.equals("espanol")) index = 2;
        }
        String name=(String)pageContext.getAttribute("data");

        JSONObject language = new JSONObject(name);
        %>
        <%!

          public String getText(JSONObject obj, int index, String key){

            JSONArray jr = obj.getJSONArray(key);
            return jr.getString(index);

          }

        %>

        <title id="login"><%out.println(getText(language, index, "login"));%></title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="css/login.css">
        <link rel="stylesheet" href="css/link.css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">
    </head>
    <body>
        <%
            if (session.getAttribute("user_id") == null) {
                response.sendRedirect("login.jsp?lang=" + lang);
            }
        %>
    <center>
        <div class='link-7' id="home"><a href='index.jsp?lang=<%=lang%>'><span class='thick'><%out.println(getText(language, index, "home"));%></span></a></div></center>
        <div class="login">
            <h1 >Decrypt using Key</h1>
            <form action="DownloadFile" method="post">
                <input type="hidden" value="<%out.println(lang);%>" name="lang">
                <input type="hidden" value="<%=request.getParameter("id")%>" name="id">
                <input type="password" name="enckey" placeholder=<%out.println(getText(language, index, "enckey"));%> required/>
                <button type="submit" class="btn btn-primary btn-block btn-large" value="decrypt">Decrypt</button>
            </form>
        </div>
        <script>
          function loading(){
            $('#loading-overlay').toggle();
            $(this).hide();
          }

          function loadLanguage(){

              console.log("loaded language");
              var val = document.getElementById("slct").value;
              console.log(val);
              window.location.replace(window.location+";lang="+val);

          }
        </script>
    </body>
</html>

