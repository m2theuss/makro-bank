package com.mycompany.makrobank.service;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;
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
        return userDAO.findUserByName(name) == null;
    }
    
    public User login(String userName, String userPassword){
        User dbUser = userDAO.findUserByName(userName);
        if(dbUser == null){
            return null;
        }
        String userPasswordHash = PasswordUtils.fromByteToStringInBase64(
                PasswordUtils.hashGenerator(
                        userPassword, PasswordUtils.fromStringToByteInBase64(dbUser.getSalt())
                )
        );
        if(userPasswordHash.equals(dbUser.getPassword())){
            return dbUser;
        } 
        return null;

    }
}

