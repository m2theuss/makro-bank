package com.mycompany.makrobank.model.dao;
import java.sql.SQLException;

import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.model.domain.*;
public class UserDAO {
    public UserDAO(){
        
    }
    public static boolean create(User user){
        String name = user.getName();
        String password = user.getPassword();
        int age = user.getAge();
        double bal = user.getInstanceOfBalance().getBalance();

        var query = "INSERT INTO User "
                    + "(name, password, age, balance)"
                    + "VALUES (?,?,?,?)";
        var db = DBConnection.getConnection();
        try{
            System.out.println("entra no try");
            var pstmt = db.prepareStatement(query);
            pstmt.setString(1,name);
            pstmt.setString(2, password);
            pstmt.setInt(3, age);
            pstmt.setDouble(4, bal);
            pstmt.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("A error happen when try set values for the new user.");
            return false;
        }
    }
}
