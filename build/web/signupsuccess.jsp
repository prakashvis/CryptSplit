
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Success</title>
        <link rel="stylesheet" href="css/signsuccess.css" type="text/css">
        <link rel="stylesheet" type="text/css" href="css/select_1.css">
    </head>
    <body>
        <div class="text-wrapper">
            <div class="title" data-content="404">
                <%
                    out.print((Integer)session.getAttribute("signuperror"));
                %>
            </div>

            <div class="subtitle">
                Use the above User ID to login
            </div>

            <div class="buttons">
                <a class="button" href="login.jsp">login</a>
            </div>
        </div>
    </body>
</html>
