package com.mycompany.makrobank.security;

import com.mycompany.makrobank.controller.AccountController;
import com.mycompany.makrobank.model.domain.User;

public class Operations implements SecurityInterceptor{
    private final TokenService tokenService;
    private final AccountController accountController;
    public Operations(AccountController accountController,TokenService tokenService){
        this.accountController = accountController;
        this.tokenService = tokenService;
    }
    @Override
    public boolean makePixPayment(User sender, String receiverName, double amount){
        return isTokenValid(sender.getToken()) && accountController.makePixPayment(sender, receiverName, amount);
    }
    @Override
    public boolean deleteAccount(User user){
        return isTokenValid(user.getToken()) && accountController.deleteAccount(user);
    }
    @Override
    public boolean makeDeposit(User user, double amount){
        return isTokenValid(user.getToken()) && accountController.makeDeposit(user, amount);
    }
    private boolean isTokenValid(String token){
        return tokenService.isTokenValid(token);
    }
    public boolean usernameExist(String name){
        return accountController.usernameExist(name);
    }
    

}
