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
        System.out.println("From selectfile: " + lang);
        int index = -1;
        if(lang == null){
            index = 0;
            lang = "english";
        }
        else{
            if(lang.equals("english")) index = 0;
            if(lang.equals("japanese")) index = 1;
            if(lang.equals("espanol")) index = 2;
        }
            System.out.println("lang"+lang);
            System.out.println("userid: "+ session.getAttribute("user_id"));
        String name=(String)pageContext.getAttribute("data");

        JSONObject language = new JSONObject(name);
        %>
        <%!

          public String getText(JSONObject obj, int index, String key){

            JSONArray jr = obj.getJSONArray(key);
            return jr.getString(index);

          }

        %>
        <title id="upload"><%out.println(getText(language, index, "upload") + getText(language, index, "files"));%></title>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/link.css">
        <link rel="stylesheet" type="text/css" href="css/select.css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">

        <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('input[type="file"]').change(function (e) {
                    var fileName = e.target.files[0].name;
                    document.getElementById("filename").value=fileName;
                });
            });
        </script>

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
        <%
            String username = (String) session.getAttribute("username");
            if (username == null) {
                response.sendRedirect("login.jsp?lang="+lang);
            }
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        %>
    <div class="login">
        <form action="Uploader" method="post" enctype="multipart/form-data">
            <center><input type="file" name="file" placeholder="Select your file" required="required"/></center>
            <input type="text" id="filename" name="filename" placeholder="<%out.println(getText(language, index, "filenamewithextension"));%>" required="required" />
            <input type="password" id="enckey" name="enckey" placeholder="Key" required="required"/>
            <button type="submit" class="btn btn-primary btn-block btn-large" value="Upload" id="upload"><%out.println(getText(language, index, "upload"));%></button>
        </form>
        <br>
        <button onclick="location.href = 'logout'"  class="btn btn-primary btn-block btn-large" value="login" id="logout"><%out.println(getText(language, index, "logout"));%></button>
    </div>
    <script>
      function loading(){
        $('#loading-overlay').toggle();
        $(this).hide();
      }

      function loadLanguage(){

          console.log("loaded language");
          var val = document.getElementById("slct").value;
          var userid1=<%=session.getAttribute("user_id") %>
          console.log(val);
          $.get({
              url: "ChangeLanguage",
              data:{
                lang: val,
                user_id:userid1
              },
              success:function(){
                  window.location.replace("selectFile.jsp?lang="+val);
              },
              error:function(data){
                  console.log(data);
              }
          });

      }
    </script>
</body>
</html>
