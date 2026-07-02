package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;
import com.mycompany.makrobank.util.*;

public class UserController{
    private final UserDAO userDAO; 
    public UserController(){
        this.userDAO = new UserDAO();
    }
    public boolean create(User user) { //true if the user has been created sucesufully
        user.setSalt(
                PasswordUtils.fromByteToString(PasswordUtils.saltGenerator())
        );
        user.setPassword(
                PasswordUtils.fromByteToString(
                        PasswordUtils.hashGenerator(
                                user.getPassword(), 
                                PasswordUtils.fromStringToByte(user.getSalt())
                ))
        );
        if(userDAO.findUserByName(user.getName()) == null){
            return userDAO.create(user);
        }else{
            return false;
        }
    }
    public boolean login(String userName, String userPassword){
        String saltValue = userDAO.findSaltByName(userName);
        if(saltValue == null){
            return false;
        }
        String toBeCompared = PasswordUtils.fromByteToString(
                PasswordUtils.hashGenerator(
                        userPassword, PasswordUtils.fromStringToByte(saltValue)
                )
        );
        return toBeCompared.equals(userDAO.findPasswordByName(userName));
        
    }
}
