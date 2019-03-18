package com.mini.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

//perform user authentication

public class Authenticate {

    static Connection con;

    //check with user_id and password
    public static boolean isValidUser(int user_id, String password) {
        
        try {
            con = SqlConnection.getConnection();
            System.out.println("connection established");
            String sql = "select * from user where username=" + user_id + " and password=" + "'" + password + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    //check with username and password
    public static boolean isValidUser(String username, String password) {
        try {
            con = SqlConnection.getConnection();
            System.out.println("connection established");
            String sql = "select * from user where username='" + username + "' and password=" + "'" + password + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    //get username with user_id from database
    public static String getUsername(int user_id) {
        try {
            con = SqlConnection.getConnection();
            String sql = "select username from user where user_id=" + user_id;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String name = rs.getString("user_name");
            System.out.println(name + " from authenticate");
            return name;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    //get user_id with username from database
    public static int getUser_id(String username) {
        try {
            con = SqlConnection.getConnection();
            String sql = "select * from user where username='" + username+"'";
            System.out.println(sql);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            int id = rs.getInt("user_id");
            System.out.println(id + " from authenticate");
            return id;
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
    }
}
