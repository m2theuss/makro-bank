package com.mycompany.makrobank.controller;

import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.service.AccountService;

public class AccountController {
    private UserDAO userDAO;
    private AccountService accountService;
    public AccountController(UserDAO userDAO, AccountService accountService){
        this.userDAO = userDAO;
        this.accountService = accountService;
    }
    public boolean receiverExist(String receiver){
        if(userDAO.findNameByName(receiver) == null){
            return false;
        }
        return true;
    }
    public boolean makePixPayment(User sender, String receiverName, double amount){
        return accountService.makePixPayment(sender, receiverName, amount);
        
    }
    public boolean makeDeposit(User user,double amount){
        if(userDAO.updateBalanceByName(user.getName(), amount)){
            user.getBalance().addAmount(amount);
            return true;
        }
        return false;
    }
    public boolean deleteAccount(User user){
        return userDAO.deleteAccount(user);
    }
    public boolean nameExist(String userName){
        return userDAO.findNameByName(userName) != null;
    }
}
