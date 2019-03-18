<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONException"%>
<%@page import="org.json.JSONObject"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <title><%out.println(getText(language, index, "download"));%></title>
        <link rel="stylesheet" type="text/css" href="css/download.css">
    </head>
    <body>
        <center>

            <form method="get" action="
                <%
                    out.print((String) session.getAttribute("link"));
                %>
            ">
                <div style="margin: 20">
                    <%
                        String data = session.getAttribute("link").toString();
                        String ext = data.substring(data.lastIndexOf('.')+1);
                        //data = data.substring(data.lastIndexOf('/') + 1);
                        if (ext.equalsIgnoreCase("mp4") || ext.equalsIgnoreCase("mkv") || ext.equalsIgnoreCase("webm")|| ext.equalsIgnoreCase(".3gp")) {
                            out.print("<video width='1024' height=\"576\" controls=\"controls\" autoplay=\"autoplay\"> "
                                    + "<source src='" + data + "' type=\"video/"+ ext +"\"> <object data=\"\" width=\"1024\" height=\"576\">"
                                    + "<embed width=\"1024\" height=\"576\" src='" + data + "'> </object> ");
                        }
                        else if(ext.equalsIgnoreCase("mp3") || ext.equalsIgnoreCase("m4a") || ext.equalsIgnoreCase("aac")){
                            out.println("<audio controls=\"controls\"><source src='"+ data +"' type=\"audio/"+ ext +"\" />+"
                            + "<source src='"+ data +"' type=\"audio/"+ ext +"\" />Your browser does not support the audio element."
                            + "</audio>");
                        }
                        else if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("gif")){
                            data = data.substring(data.lastIndexOf('/') + 1);
                            out.println("<img href='"+ data +"'>");
                        }
                    %>
                </div>

                <button type="submit" style="position: relative" class="button"><%out.println(getText(language, index, "download"));%></button>
            </form>
        </center>
    </body>
</html>
