package com.mycompany.makrobank.service;

import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.util.PasswordUtils;

public class AuthService {
    final UserDAO userDAO;
    public AuthService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public void create(User user){
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
    }
    public boolean isNameAvaliable(User user){
        if(!nameUserExist(user.getName())){
            return userDAO.create(user);
        }else{
            return false;
        }
    }
    public boolean nameUserExist(String name){
        if(userDAO.findNameByName(name) == null){
            return false;
        }else{
            return true;
        }
    }
}

