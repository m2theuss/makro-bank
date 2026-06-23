package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;

public class UserLoginController {
    public UserLoginController(){
    }
    public boolean createUser(User user){ //true if the user has been created sucesufully
        return UserDAO.create(user);
        
    }
}
