package com.mycompany.makrobank.controller;

import com.mycompany.makrobank.model.dao.UserDAO;

public class AccountController {
    private UserDAO userDAO;
    public AccountController(){
        this.userDAO = new UserDAO();
    }
    public String makePix(int amount){
        
    }
}
