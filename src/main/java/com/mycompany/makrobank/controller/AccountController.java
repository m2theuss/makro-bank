package com.mycompany.makrobank.controller;

import com.mycompany.makrobank.model.dao.UserDAO;

public class AccountController {
    private UserDAO userDAO;
    public AccountController(){
        this.userDAO = new UserDAO();
    }
    public boolean receiverExist(String receiver){
        if(userDAO.findUserByName(receiver) == null){
            return false;
        }
        return true;
    }
    public boolean makePixPayment(String sender, String receiver, double amount){
        UserDAO.takeAmount(sender, amount);
        UserDAO.setAmount(receiver, amount);
    }
}
