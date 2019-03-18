/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mini.process;

import com.mini.download.Merger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Administrator
 */
@WebServlet(name = "MapDownload", urlPatterns = {"/MapDownload"})
@MultipartConfig
public class MapDownload extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Part mapPart = request.getPart("file");
        String applicationPath =request.getServletContext().getRealPath("");
        String deckey = request.getParameter("enckey");
        String tempFile =applicationPath + File.separator + "UploadedMaps";
        File f = new File(tempFile);
        if(!f.exists()) f.mkdirs();
        tempFile += File.separator + "temp.map";
        f = new File(tempFile);
        new PrintWriter(f).close();
        InputStream is = mapPart.getInputStream();
        writeToFile(is, f);
        String mergedFile = "Merged/" + Merger.merge(tempFile, applicationPath, deckey);
        HttpSession session = request.getSession();
        session.setAttribute("link", mergedFile);
        f.delete();
        response.sendRedirect("/downloadPage.jsp");
        
    }
    
    public static void writeToFile(InputStream is, File file) throws IOException{
        OutputStream os = new FileOutputStream(file);
            byte[] b =new byte[102400];
            int k;
            while((k = is.read(b))!=-1)
                os.write(b, 0, k);
    }
}
