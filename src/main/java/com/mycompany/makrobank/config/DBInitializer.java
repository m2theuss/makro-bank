package com.mycompany.makrobank.config;
import java.sql.SQLException;
import java.sql.Connection;
public class DBInitializer {
    public static void veriryDB(){
        var conn = DBConnection.getConnection();
        System.out.print(conn);
        var query = "CREATE TABLE IF NOT EXISTS User("
                + "name TEXT PRIMARY KEY,"
                + "password TEXT,"
                + "age TEXT"
                + ");";
        try(var stmt = conn.createStatement()){
            stmt.execute(query);
        }catch(SQLException e){
            System.out.println("A error happen when try to run DBInitializer: " + e.getMessage());
        }
    }
}
