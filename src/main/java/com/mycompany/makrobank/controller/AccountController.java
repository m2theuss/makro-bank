package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.service.AccountService;

public class AccountController {
    private AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    public boolean makePixPayment(User sender, String receiverName, double amount){
        return accountService.makePixPayment(sender, receiverName, amount);
        
    }
    public boolean makeDeposit(User user,double amount){
        return accountService.makeDeposit(user, amount);
    }
    public boolean deleteAccount(User user){
        return accountService.deleteAccount(user);
    }
    public boolean usernameExist(String name){
        return accountService.usernameExist(name);
    }
}
