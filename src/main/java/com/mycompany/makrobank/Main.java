package com.mycompany.makrobank;
import java.util.Scanner;

import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.controller.AccountController;
import com.mycompany.makrobank.controller.AuthController;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.security.Operations;
import com.mycompany.makrobank.security.TokenService;
import com.mycompany.makrobank.service.AccountService;
import com.mycompany.makrobank.service.AuthService;
import com.mycompany.makrobank.view.*;
/**
 * @author m2theus
 */
public class Main {

    public static void main(String[] args) {
        DBInitializer.verifyDB();
        Scanner scan = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();
        AuthService authService = new AuthService(userDAO);
        AuthController authController = new AuthController(authService);
        AccountService accountService = new AccountService(userDAO);
        AccountController accountController = new AccountController(accountService);
        TokenService tokenService = new TokenService();
        Operations operations = new Operations(accountController, tokenService);
        AccountView accountView = new AccountView(scan, operations, tokenService);
        AuthView authView = new AuthView(scan, authController, accountView);
        authView.start();
    }
}
