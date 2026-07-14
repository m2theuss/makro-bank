package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;
import com.mycompany.makrobank.security.TokenService;
import com.mycompany.makrobank.util.*;

public class AuthController{
    private final UserDAO userDAO; 
    public AuthController(){
        this.userDAO = new UserDAO();
    }
    public boolean create(User user) { //true if the user has been created sucesufully
        user.setSalt(
                PasswordUtils.fromByteToStringInBase64(PasswordUtils.saltGenerator())
        );
        user.setPassword(
                PasswordUtils.fromByteToStringInBase64(
                        PasswordUtils.hashGenerator(
                                user.getPassword(), 
                                PasswordUtils.fromStringToByteInBase64(user.getSalt())
                ))
        );
        if(!nameUserExist(user.getName())){
            return userDAO.create(user);
        }else{
            return false;
        }
    }
    public User login(String userName, String userPassword){
        String saltValue = userDAO.findSaltByName(userName);
        if(saltValue == null){
            return null;
        }
        String userPasswordHash = PasswordUtils.fromByteToStringInBase64(
                PasswordUtils.hashGenerator(
                        userPassword, PasswordUtils.fromStringToByteInBase64(saltValue)
                )
        );
        if(userPasswordHash.equals(userDAO.findPasswordByName(userName))){
            User tmp = new User(
                userName, 
                userPasswordHash, 
                userDAO.findAgeByName(userName),
                new Balance(userDAO.findBalanceByName(userName))
            );
            tmp.setPayload(new TokenService().generateToken(userName));
            return tmp;
        } 
        return null;
    }
    public boolean nameUserExist(String name){
        if(userDAO.findUserByName(name) == null){
            return false;
        }else{
            return true;
        }
    }
}
