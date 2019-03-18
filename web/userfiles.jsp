<%@page import="java.util.ArrayList"%>
<%@page import="com.mini.bean.FileDetails"%>
<%@page import="com.mini.dao.FilesDBdao"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
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
        System.out.println("userfiles lang1: "+ lang);
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
        <meta charset="ISO-8859-1">
        <title id="userdetails"><%out.println(getText(language, index, "userdetails"));%></title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/table.css">
        <link rel="stylesheet" type="text/css" href="css/link.css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>

        </script>
    </head>
    <body>
    <center>

        <div style="position: relative" >
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
            <table>
                <%
                    if (session.getAttribute("user_id") == null) {
                        response.sendRedirect("login.jsp?lang="+lang);
                    } else {
                        out.println("<h1 id='hello'>" + getText(language, index, "hello") + " " + session.getAttribute("username")
                                + " </h1> <div class='link-1' id='logout'><a href='logout'><span class='thin'>"
                                + getText(language, index, "log") + "</span><span class='thick'>" + getText(language, index, "out") + "</span></a></div>");
                    }
                %>
                <div class='link-7' id="upload"><a href='selectFile.jsp?lang=<%=lang%>'><span class='thin'>
                  <%out.println(getText(language, index, "up"));%></span><span class='thick'><%out.println(getText(language, index, "load"));%></span></a></div>
                  <div class='link-1 thick' id="home"><a href='index.jsp?lang=<%=lang%>'><%out.println(getText(language, index, "home"));%></a></div>
            </table>
        </div>
        <div class="container">
            <table>
                <thead>
                    <tr>
                      <th>
                        <%out.println(getText(language, index, "filename"));%>
                      </th>
                      <th>
                        <%out.println(getText(language, index, "download"));%>
                      </th>
                      <th>
                        <%out.println(getText(language, index, "delete"));%>
                      </th>
                    </tr>
                </thead>
                <%
                    ArrayList<FileDetails> details = FilesDBdao.getUserFiles((Integer) session.getAttribute("user_id"));
                    for (FileDetails d : details) {
                        out.println("<tr id='" + d.getFile_id() + "'>");
                        out.println("<td>"+ d.getFileName() +"</td><td><a href=Maps/" + d.getFileName()
                                + " download><button class='btn btn-primary btn-block btn-large'>"
                                + getText(language, index, "download") +"</button></a></td><td><button class='btn btn-primary btn-block btn-large'"
                                + "onclick='deleterow(" + d.getFile_id() + ")' id='delete'>" + getText(language, index, "delete") + "</button></td></tr>");
                    }
                %>
            </table>
        </div>
    </center>
    </body>
    <script>
        function deleterow(fileid) {
            var id = "#" + fileid;
            console.log(id);
            $.ajax({
                url: "deleteserv?id=" + fileid,

                success: function(result) {
                    console.log(result);
                },
                error: function(result){
                    console.log(result);
                }
            });
            $(id).hide();
        }
        function loading(){
          $('#loading-overlay').toggle();
          $(this).hide();
        }

        function loadLanguage(){

            console.log("loaded language");
            var val = document.getElementById("slct").value;
            console.log(val);
            window.location.replace("userfiles.jsp?lang="+val);

        }
    </script>
</html>
