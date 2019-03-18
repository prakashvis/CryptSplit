
package com.mini.dao;

import static com.mini.dao.Authenticate.con;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Language {
    
    public static void changeLanguageInDb(String lang, String user_id){
        
        if(lang != null){
            try{
                Connection con = SqlConnection.getConnection();
                System.out.println(lang);
                String sql = "update user set language = '" + lang +"' where user_id = " + user_id;
                Statement st = con.createStatement();
                System.out.println(sql);
                st.executeUpdate(sql);
                System.out.println("language db updated");
            }
            catch(Exception e){
                System.out.println("Error");
            }
        }     
    }  
    
    public static String getLanguage(int user_id){
        String s = Integer.toString(user_id);
        return getLanguage(s);
    }
    
    public static String getLanguage(String user_id){
        try {
            con = SqlConnection.getConnection();
            String sql = "select language from user where user_id=" + user_id;
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            rs.next();
            String lang = rs.getString("language");
            System.out.println(lang + " from getLang");
            return lang;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
