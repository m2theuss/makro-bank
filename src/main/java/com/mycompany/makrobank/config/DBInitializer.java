package com.mycompany.makrobank.config;
import java.sql.SQLException;
public class DBInitializer {
    public static void veriryDB(){
        var conn = DBConnection.getConnection();
        System.out.print(conn);
        var query = "CREATE TABLE IF NOT EXISTS User("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "password TEXT,"
                + "age INTEGER,"
                + "balance DOUBLE"
                + ");";
        try(var stmt = conn.createStatement()){
            stmt.execute(query);
        }catch(SQLException e){
            System.out.println("A error happen when try to run DBInitializer: " + e.getMessage());
        }
    }
}
