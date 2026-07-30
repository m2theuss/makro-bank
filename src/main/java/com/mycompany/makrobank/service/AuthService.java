package com.mycompany.makrobank.service;

import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.security.TokenService;
import com.mycompany.makrobank.util.PasswordUtils;

public class AuthService {
    final UserDAO userDAO;
    public AuthService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public boolean createUser(User user){
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
        if(isNameAvaliable(user.getName())){
            return userDAO.create(user);
        }
        return false;
    }
    public boolean isNameAvaliable(String name){
        return userDAO.findNameByName(name) == null;
    }
    
    public User login(String userName, String userPassword){
        String saltValue = getSalt(userName);
        if(saltValue != null){
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
                user.setToken(new TokenService().generateToken(userName));
                return user;
            } 
            return null;
        }
        return null;

    }
    public String getSalt(String userName){
        return userDAO.findSaltByName(userName);
    }
}

