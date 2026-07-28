/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.makrobank;
import java.util.Scanner;

import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.controller.AuthController;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.service.AuthService;
import com.mycompany.makrobank.view.*;
/**
 *
 * @author matheus
 */
public class Main {

    public static void main(String[] args) {
        DBInitializer.verifyDB();
        Scanner scan = new Scanner(System.in);

        UserDAO userDAO = new UserDAO();
        AuthService authService = new AuthService(userDAO);
        AuthController authController = new AuthController(authService);
        AuthView userLogin = new AuthView(scan, authController,authService);
        userLogin.start();
    }
}
