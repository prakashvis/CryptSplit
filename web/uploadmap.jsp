<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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

        <title id="upload"><%out.println(getText(language, index, "upload") + " " + getText(language, index, "files"));%></title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/link.css">
        <link rel="stylesheet" type="text/css" href="css/select.css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">

    </head>
    <body>

        <center>
            <div class="topright" style="top:20px; right:40px">
                    <div class="select">
                      <select name="slct" id="slct" onchange="loadLanguage()">
                        <option><%out.println(getText(language, index, "selectlanguage"));%></option>
                        <option value="english">English</option>
                        <option value="japanese">Japanese</option>
                        <option value="espanol">Espanol</option>
                      </select>
                    </div>
                  </div>
            <div class='link-7' id="home"><a href='index.jsp?lang=<%=lang%>'><span class='thick'><%out.println(getText(language, index, "home"));%></span></a></div></center>
        <div class="login">
            <form action="MapDownload" method="post" enctype="multipart/form-data">
                <center>
                    <input type="file" name="file" placeholder="Select your file" required="required" />
                    <input type="password" name="enckey" required="required" placeholder="Decryption key"/>
                </center>
                <button type="submit" class="btn btn-primary btn-block btn-large" value="Upload" id="download"><%out.println(getText(language, index, "download"));%></button>
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
              window.location.replace("uploadmap.jsp?lang="+val);

          }
        </script>
      </body>
</html>
