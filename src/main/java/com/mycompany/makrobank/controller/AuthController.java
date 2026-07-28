package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;
import com.mycompany.makrobank.security.TokenService;
import com.mycompany.makrobank.service.AuthService;
import com.mycompany.makrobank.util.*;

public class AuthController{
    private final UserDAO userDAO; 
    private final AuthService authService;
    public AuthController(UserDAO userDAO, AuthService authService){
        this.userDAO = userDAO;
        this.authService = authService;
    }
    public boolean create(User user) { //true if the user has been created sucesufully
        authService.create(user);
        authService.nameUserExist(user);
        if(!nameUserExist(user.getName())){
            return userDAO.create(user);
        }else{
            return false;
        }
    }
    public User login(String userName, String userPassword){
        String saltValue = userDAO.findSaltByName(userName);
        if(saltValue == null){
            System.out.println("1");
            return null;
        }
        String userPasswordHash = PasswordUtils.fromByteToStringInBase64(
                PasswordUtils.hashGenerator(
                        userPassword, PasswordUtils.fromStringToByteInBase64(saltValue)
                )
        );
        if(userPasswordHash.equals(userDAO.findPasswordByName(userName))){
            
            User user = new User(
                userName, 
                userPasswordHash, 
                userDAO.findAgeByName(userName),
                new Balance(userDAO.findBalanceByName(userName))
            );
            user.setJWT(new TokenService().generateToken(userName));
            return user;
        } 
        return null;

    }
    public boolean nameUserExist(String name){
        if(userDAO.findNameByName(name) == null){
            return false;
        }else{
            return true;
        }
    }
}
