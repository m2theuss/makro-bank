package com.mycompany.makrobank.controller;

import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;

public class AccountController {
    private UserDAO userDAO;
    public AccountController(){
        this.userDAO = new UserDAO();
    }
    public boolean receiverExist(String receiver){
        if(userDAO.findNameByName(receiver) == null){
            return false;
        }
        return true;
    }
    public boolean makePixPayment(User sender, String receiverName, double amount){
        if(sender.getName().equals(receiverName)){
            return false;
        }
        boolean senderResult = userDAO.updateBalanceByName(sender.getName(), (amount * -1));
        sender.getInstanceOfBalance().takeAmount(amount);

        boolean receiverResult = userDAO.updateBalanceByName(receiverName, amount);
        if(senderResult && receiverResult){
            return true;
        }
        return false;
    }
    public boolean makeDeposit(User user,double amount){
        if(userDAO.updateBalanceByName(user.getName(), amount)){
            user.getInstanceOfBalance().addAmount(amount);
            return true;
        }
        return false;
    }
    public boolean deleteAccount(User user){
        return userDAO.deleteAccount(user);
    }
}
