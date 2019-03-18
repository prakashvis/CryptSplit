package com.mini.dao;

import com.mini.bean.FileDetails;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

//Dao for updating files database

public class FilesDBdao {

    //insert new record to files table when new file is uploaded
    public static void updateDB(String mapName, String url, int user_id, long file_size) {
        try {
            Connection con = SqlConnection.getConnection();
            String sql;
            sql = "insert into files values(null,'" + mapName + "'," + file_size + "," + user_id + ")";
            Statement st = con.createStatement(); 
            st.executeUpdate(sql);
            st.close();
            st = con.createStatement();
            sql = "update user set filecount = filecount + 1 where user_id = '" + user_id + "'";
            st.executeUpdate(sql);
            st.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //returns list of all the available map files available in files table
    public static ArrayList<FileDetails> getFiles() {
        ArrayList<FileDetails> details = new ArrayList();
        try {
            Connection con = SqlConnection.getConnection();
            String sql = "select * from files";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                FileDetails fd = new FileDetails();
                fd.setFileName(rs.getString("filename"));
                fd.setFile_id(Integer.parseInt(rs.getString("file_id")));
                fd.setUser_id(Integer.parseInt(rs.getString("user_id")));
                fd.setFileSize(Long.parseLong(rs.getString("filesize")));
                details.add(fd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return details;
    }

    //returns the location of the file (url) with given file_id
    public static String getFileLocation(int file_id){

        try{
            Connection con = SqlConnection.getConnection();
            String sql = "select * from files where file_id=" + file_id;
            Statement st = con.createStatement();
            ResultSet rs=st.executeQuery(sql);
            if(rs.next())
                return rs.getString("filename");
        }
        catch(Exception e){
        }
        return "";
    }

    //returns list of files uploaded by particular user from files table
    public static ArrayList<FileDetails> getUserFiles(int user_id) {
        ArrayList<FileDetails> details = new ArrayList();
        try{
            Connection con = SqlConnection.getConnection();
            String sql = "select * from files where user_id=" + user_id + ";";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                FileDetails fd = new FileDetails();
                fd.setFileName(rs.getString("filename"));
                fd.setFile_id(Integer.parseInt(rs.getString("file_id")));
                fd.setUser_id(Integer.parseInt(rs.getString("user_id")));
                fd.setFileSize(Long.parseLong(rs.getString("filesize")));
                details.add(fd);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return details;
    }
}
