<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        System.out.println("signup lang: "+ lang);
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

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title id="signup"><%out.println(getText(language, index, "signup"));%></title>
        <link rel="stylesheet" type="text/css" href="css/login.css">
        <link rel="stylesheet" type="text/css" href="css/link.css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">
    </head>
    <body>
        <%
            if (session.getAttribute("user_id") != null) {
                response.sendRedirect("userfiles.jsp?lang="+lang);
            }
        %>

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
            <div class='link-7 thick' id="home"><a href='index.jsp?lang=<%=lang%>'><%out.println(getText(language, index, "home"));%></a></div></center>
        <div class="login">
            <h1 id="signup"><%out.println(getText(language, index, "createaccount"));%></h1>
            <form action="Signup" method="post">
                <input type="hidden" value="<%out.println(lang);%>"name="lang">
                <input type="text" name="username" placeholder="<%out.println(getText(language, index, "username"));%>" required="required" />
                <input type="password" name="newpass" placeholder="<%out.println(getText(language, index, "newpass"));%>" required="required" />
                <input type="password" name="conpass" placeholder="<%out.println(getText(language, index, "confirm"));%>" required="required" />
                <button type="submit" class="btn btn-primary btn-block btn-large" value="login" id="signup"><%out.println(getText(language, index, "signup"));%></button>
            </form>
            <%
                if (!(session.getAttribute("signuperror") == "" || session.getAttribute("signuperror") == null)) {
                    String error = (String)session.getAttribute("signuperror");
                    String res = null;
                    if(error.equals("alreadyexists"))
                      out.print("<center>" + getText(language, index, "alreadyexists") + "</center>");
                    else if(error.equals("notequal"))
                      out.print("<center>" + getText(language, index, "notequal") + "</center>");

                }
            %>
            <br><center>or</center><br>
            <button onclick="location.href = 'login.jsp?lang=<%=lang%>'"  class="btn btn-primary btn-block btn-large" value="login" id="login"><%out.println(getText(language, index, "login"));%></button>
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
              window.location.replace("signup.jsp?lang="+val);

          }
        </script>
    </body>
</html>
