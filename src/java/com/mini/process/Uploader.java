package com.mini.process;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

//Servlet to perform upload operation

@WebServlet(name = "Uploader", urlPatterns = {"/Uploader"})
@MultipartConfig
public class Uploader extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String fname = request.getParameter("filename");
            String encKey = request.getParameter("enckey");
            Part filePart = request.getPart("file");
            String applicationPath = request.getServletContext().getRealPath("");
            String newname= getNewName(applicationPath + File.separator, fname);
            File file = new File(applicationPath + File.separator + newname);
            if(!file.exists()) file.createNewFile();
            InputStream is = filePart.getInputStream();
            writeToFile(is, file);
            HttpSession session = request.getSession();
            int id = (int) session.getAttribute("user_id");
            Splitter split = new Splitter(file, newname, id, applicationPath, encKey);
            split.splitFile();
            System.out.println("file uploaded at " + file.getAbsolutePath());
            file.delete();
            response.sendRedirect("userfiles.jsp");
        } catch (IOException | ServletException ex) {
            System.out.println(ex);
        }
    }
    
    //Get new file name if file already exists with the same name
    
    public static String getNewName(String path, String fname){
        
        int pos = fname.indexOf('.');
        String name=fname.substring(0, pos-1);
        String ext = fname.substring(pos);
        String newpath = path + fname;
        String newname = fname;
        int counter = 1;
        while((new File(newpath)).exists()){
            newname = name + '_' + counter + ext;
            newpath = path + newname;
            counter++;
        }
        return newname;
    }
    
    //Save uploaded file to disk
    public static void writeToFile(InputStream is, File file) throws IOException{
        OutputStream os = new FileOutputStream(file);
            byte[] b =new byte[102400];
            int k;
            while((k = is.read(b))!=-1)
                os.write(b, 0, k);
    }
}
