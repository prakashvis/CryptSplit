
package com.mini.dao;

import java.sql.Connection;
import java.sql.Statement;

//delete record from files table with given file_id
public class deleteDao {
    public static int deleteFromDb(int file_id){
        
        try{
            Connection con = SqlConnection.getConnection();
            String sql = "delete from files where file_id=" + file_id + " limit 1";
            Statement st=con.createStatement();
            return st.executeUpdate(sql);
        }
        catch(Exception e){
            return -1;
        }
    }
}
