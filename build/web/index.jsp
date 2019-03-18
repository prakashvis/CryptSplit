<%@page import="java.util.ArrayList"%>
<%@page import="com.mini.bean.FileDetails"%>
<%@page import="com.mini.dao.FilesDBdao"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html dir="ltr" charset="utf-8">
  <head>
    <c:import var="data" url="http://localhost:8080/language.json"/>
    <%

    String lang = request.getParameter("lang");
    System.out.println("indexlang: "+lang);
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
        System.out.println("lang: "+lang);
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

    <META http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><%out.println(getText(language, index, "title"));%></title>
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <link rel="stylesheet" type="text/css" href="css/table.css">
    <link rel="stylesheet" type="text/css" href="css/link.css">
    <link rel="stylesheet" type="text/css" href="css/loading.css">
    <link rel="stylesheet" type="text/css" href="css/select_1.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.3.1.js"></script>

  </head>
  <body>
      <center>
    <div style="position: relative">
      <form name="form1" action="ChangeLanguage" method="post">
        <input type="hidden" name="action" value="checking">
        <div class="topright">
          <div class="select">
            <select name="slct" id="slct" onchange="loadLanguage()">
              <option><%out.println(getText(language, index, "selectlanguage"));%></option>
              <option value="english">English</option>
              <option value="japanese">Japanese</option>
              <option value="espanol">Espanol</option>
            </select>
          </div>
        </div>
      </form>

      <table>
        <%
          if(session.getAttribute("username") == null){
            out.println("<h1 id='welcome'>"+getText(language, index, "welcome"));
            out.println(",</h1><div class='link-1' ><a href='login.jsp?lang="+ lang +"' id='login'><span class='thin'>" + getText(language, index, "log") + "</span><span class='thick'>" + getText(language, index, "in") + "</span>");
            out.println("</a></div><div class='link-2 thick' ><a href='signup.jsp?lang="+ lang +"'><span class='thin'>"+getText(language, index, "sign") + "</span><span class='thick'>" + getText(language, index, "up") + "</span></a></div>");
          }
          else{
            out.println("<h1 id='welcome'> "+getText(language, index, "welcome") + " " + session.getAttribute("username") + ",</h1>");
            out.println("<div class='link-1' ><a href='logout' id='logout'><span class='thin'>"+getText(language, index, "log") + "</span><span class='thick'>" + getText(language, index, "out") + "</span>");
            out.println("</a></div><div class='link-2' ><a href='userfiles.jsp?lang="+ lang +"'><span class='thin'>"+getText(language, index, "user")+ "</span><span class='thick'>" + getText(language, index, "files") + "</span></a></div>");
          }

        %>
        <div class='link-7'><a href='selectFile.jsp?lang=<%=lang%>'>
          <span class='thin'>
          <%out.println(getText(language, index, "up"));%>
          </span>
          <span class='thick'>
          <%out.println(getText(language, index, "load"));%>
          </span>
        </a></div>
        <div class='link-6'><a href='uploadmap.jsp?lang=<%=lang%>'>
          <span class='thin'>
          <%out.println(getText(language, index, "from"));%>
          </span>
          <span class='thick'>
          <%out.println(getText(language, index, "map"));%>
          </span>
        </a></div>
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
              <%out.println(getText(language, index, "portable"));%>
            </th>
            <th>
              <%out.println(getText(language, index, "userid"));%>
            </th>
            <th>
              <%out.println(getText(language, index, "download"));%>
            </th>
          </tr>
        </thead>
        <%
          ArrayList<FileDetails> details = FilesDBdao.getFiles();
          for(FileDetails d : details){
            out.println("<tr><td>" + d.getFileName() + "</td><td><a href = Maps/" + d.getFileName()
                    +" download><button class='btn btn-primary btn-block btn-large'>"
                    + language.getJSONArray("download").getString(index)
                    + "</button></a></td><td>" + d.getUser_id() + "</td>");
            out.println("<td><a href='decryptFile.jsp?id=" + d.getFileName() + "'><button onclick='loading()' class='btn btn-primary btn-block btn-large' id='download'>"
            + language.getJSONArray("download").getString(index) +"</button></a></td></tr>");
          }
        %>
      </table>
    </div>
    <div id="loading-overlay">
      <div id="loading">
        <div class="sk-folding-cube">
          <div class="sk-cube1 sk-cube"></div>
          <div class="sk-cube2 sk-cube"></div>
          <div class="sk-cube4 sk-cube"></div>
          <div class="sk-cube3 sk-cube"></div>
        </div>
        <h2 class="loading-text" id="loading">LOADING...</h2>
      </div>
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
                  window.location.replace("index.jsp?lang="+val);
              },
              error:function(data){
                  console.log(data);
              }
          });
      }
    </script>
    <center>
  </body>
</html>
