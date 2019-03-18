
package com.mini.process;

import com.mini.download.Merger;
import java.io.File;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DownloadFile", urlPatterns = {"/DownloadFile"})

public class DownloadFile extends HttpServlet {

    @Override
    protected void doPost  (HttpServletRequest request, HttpServletResponse response){
        try{
            System.out.println("Download started");
            String fileName = request.getParameter("id");
            System.out.println("Map name=" + fileName);
            String deckey = request.getParameter("enckey");
            System.out.println("Deckey=" + deckey);
            String appPath = getServletContext().getRealPath("");
            String mapPath = appPath + "Maps" + File.separator + fileName;
            System.out.println(mapPath);
            String mergedFile = "Merged/" + Merger.merge(mapPath, appPath, deckey);
            HttpSession session = request.getSession();
            session.setAttribute("link", mergedFile);
            response.sendRedirect("downloadPage.jsp");
        }
        catch(IOException e){
            try{
                response.sendRedirect("/errorPage.jsp");
            }
            catch(IOException ex){}
        }
    }
}
