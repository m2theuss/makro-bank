/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.makrobank;
import java.util.Scanner;

import com.mycompany.makrobank.config.*;
import com.mycompany.makrobank.view.*;
/**
 *
 * @author matheus
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");
        DBInitializer.verifyDB();
        Scanner scan = new Scanner(System.in);
        UserLogin userLogin = new UserLogin(scan);
        userLogin.startLogin();
    }
}
