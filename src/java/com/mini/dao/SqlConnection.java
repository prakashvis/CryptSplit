package com.mini.dao;

import java.sql.Connection;
import java.sql.DriverManager;

//Class to get Database connection

public class SqlConnection {

    public static Connection con = null;
    // Hostname
    private static final String dbHost = "127.0.0.1";

    // Port -- Standard: 3306
    private static final String dbPort = "3306";

    // Database name
    private static String database = "taskdb"; //

    // Database user
    private static final String dbUser = "root";

    // Datenbankpasswort
    private static final String dbPassword = "root";

    public static Connection getConnection() throws Exception {

        if (con == null){
                        
             Class.forName("com.mysql.cj.jdbc.Driver");
            
            System.out.println("Class forname obtained");
            con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + database + "?" + "user=" + dbUser + "&"
                + "password=" + dbPassword);
            
            System.out.println("Conn..");
            
            
        }
        return con;
    }
}
