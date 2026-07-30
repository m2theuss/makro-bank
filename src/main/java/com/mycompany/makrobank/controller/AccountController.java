package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.security.SecurityInterceptor;
import com.mycompany.makrobank.service.AccountService;

public class AccountController implements SecurityInterceptor {
    private AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @Override
    public boolean makePixPayment(User sender, String receiverName, double amount){
        return accountService.makePixPayment(sender, receiverName, amount);
    }
    @Override
    public boolean makeDeposit(User user,double amount){
        return accountService.makeDeposit(user, amount);
    }
    @Override
    public boolean deleteAccount(User user){
        return accountService.deleteAccount(user);
    }
    @Override
    public boolean usernameExist(String name){
        return accountService.usernameExist(name);
    }
}
