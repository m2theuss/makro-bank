package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.domain.*;
import com.mycompany.makrobank.service.AuthService;

public class AuthController{
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    public boolean create(User user) {
        return authService.createUser(user);
    }
    public User login(String userName, String userPassword){
        return authService.login(userName, userPassword);
    }
    public boolean isNameAvaliable(String name){
        return authService.isNameAvaliable(name);
    }
}
