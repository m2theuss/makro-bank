package com.mycompany.makrobank.model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.model.domain.*;
public class UserDAO {
    
    public boolean create(User user){
        var query = "INSERT INTO user "
                    + "(name, password, age, balance, salt)"
                    + "VALUES (?,?,?,?,?)";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
            pstmt.setString(1,user.getName());
            pstmt.setString(2, user.getPassword());
            pstmt.setInt(3, user.getAge());
            pstmt.setDouble(4, user.getBalance().getBalance());
            pstmt.setString(5, user.getSalt());
            pstmt.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return false;
        }
    }
    public User findUserByName(String userName){
        var query = "SELECT * FROM user "
        + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                User user = new User(
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("age")
                );
                user.setSalt(rs.getString("salt"));
                user.getBalance().addAmount(rs.getDouble("balance"));
                return user;
            }
            return null;
        }catch(SQLException e){
            System.out.println("A error happen when try get information about the user." + e.getMessage());
            return null;
        }
    }
    public boolean updateBalanceByName(String name, Double amount){
        var query = "UPDATE user SET balance = balance + ? "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setDouble(1, amount);
        pstmt.setString(2, name);
        if(pstmt.executeUpdate() > 0){
            return true;
        }
        return false;
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return false;
        }
    }
    public boolean deleteAccount(User user){
        var query = "DELETE FROM user "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setString(1, user.getName());
        if(pstmt.executeUpdate() > 0){
            return true;
        }
        return false;
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return false;
        }
    }
}
