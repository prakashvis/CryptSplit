package com.mini.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

//Dao for Signup process

public class SignupDao {

    public int signup(String username, String password, String language) {
        try {
            Connection con = SqlConnection.getConnection();
            if (!Authenticate.isValidUser(username, password)) {
                String sql = "insert into user values(null,'" + username + "','" + password + "','" + language + "', 0);";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                sql = "select user_id from user where username='" + username + "'";
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                return rs.getInt("user_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return -1;
    }
}
