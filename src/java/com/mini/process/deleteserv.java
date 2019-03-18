
package com.mini.process;

import com.mini.dao.FilesDBdao;
import com.mini.dao.deleteDao;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//servlet to perform delete operations on disk and database

@WebServlet(name = "deleteserv", urlPatterns = {"/deleteserv"})
public class deleteserv extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int file_id=Integer.parseInt((String)request.getParameter("id"));
        String realpath=request.getServletContext().getRealPath("");
        DeleteFiles.deleteFilesFromServer(FilesDBdao.getFileLocation(file_id), realpath + File.separator + "Maps" + File.separator);
        deleteDao.deleteFromDb(file_id);
    }
}
