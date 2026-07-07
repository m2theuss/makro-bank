package com.mycompany.makrobank.config;
import java.sql.SQLException;
public class DBInitializer {
    public static void verifyDB(){
        var conn = new DBConnection().getConnection();
        var query = "CREATE TABLE IF NOT EXISTS user("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT,"
                + "password TEXT,"
                + "token TEXT,"
                + "salt TEXT,"
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
