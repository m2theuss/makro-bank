package com.mycompany.makrobank.model.dao;
import java.sql.SQLException;

import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.model.domain.*;
public class UserDAO {
    public UserDAO(){
        
    }
    public boolean create(User user){
        String name = user.getName();
        String password = user.getPassword();
         
        int age = user.getAge();
        double bal = user.getInstanceOfBalance().getBalance();

        var query = "INSERT INTO user "
                    + "(name, password, age, balance, salt)"
                    + "VALUES (?,?,?,?,?)";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
            pstmt.setString(1,user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getAge());
            pstmt.setDouble(4, user.getInstanceOfBalance().getBalance());
            pstmt.setString(5, user.getSalt());
            pstmt.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return false;
        }
    }
}
