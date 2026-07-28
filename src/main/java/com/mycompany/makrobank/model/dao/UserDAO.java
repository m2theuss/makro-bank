package com.mycompany.makrobank.model.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
            pstmt.setDouble(4, user.getInstanceOfBalance().getBalance());
            pstmt.setString(5, user.getSalt());
            pstmt.executeUpdate();
            return true;
        } catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return false;
        }
    }
    public String findSaltByName(String userName){
        var query = "SELECT salt FROM user "
                    +"WHERE name LIKE ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
            pstmt.setString(1,userName);
            return pstmt.executeQuery().getString("salt");
        } catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return null;
        }
    }
    public String findPasswordByName(String userName){
        var query = "SELECT password FROM user "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setString(1, userName);
            return pstmt.executeQuery().getString("password");
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return null;
        }
    }
    public Double findBalanceByName(String userName){
        var query = "SELECT balance FROM user "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setString(1, userName);
            return pstmt.executeQuery().getDouble("balance");
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return -1.0;
        }
    }
    public Integer findAgeByName(String userName){
        var query = "SELECT age FROM user "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setString(1, userName);
            return pstmt.executeQuery().getInt("age");
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return null;
        }
    }
    public String findNameByName(String userName){
        var query = "SELECT name FROM user "
                + "WHERE name like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
        pstmt.setString(1, userName);
            return pstmt.executeQuery().getString("name");
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return null;
        }
    }
    public User findUserByName(String userName){
        var query = "SELECT name FROM user "
        + "WHERE * like ?";
        var db = new DBConnection().getConnection();
        try(var pstmt = db.prepareStatement(query)){
            pstmt.setString(1, userName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                Balance balanceObj = new Balance(rs.getDouble("balance"));
                return new User(
                    rs.getString("name"),
                    rs.getString("password"),
                    rs.getInt("age"),
                    balanceObj
                );
            }
            return null;
        }catch(SQLException e){
            System.out.println("A error happen when try set values for the new user." + e.getMessage());
            return null;
        }
    }
    public String updateToken(String userName, String token){
        return "";
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
