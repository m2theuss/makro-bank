package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;
import com.mycompany.makrobank.util.*;

public class UserController{
    public UserController(){
    }
    public boolean create(User user) { //true if the user has been created sucesufully
        user.setSalt(PasswordUtils.fromByteToString(PasswordUtils.saltGenerator()));
        user.setPassword(
                PasswordUtils.fromByteToString(
                        PasswordUtils.hashGenerator(user.getPassword(), PasswordUtils.fromStringToByte(user.getSalt())
                ))
        );
        return new UserDAO().create(user);
    }
    public boolean login(String name){
        String value = new UserDAO().getSalt(name);
        System.out.println(value);
        return true;
        
    }
}
