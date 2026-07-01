package com.mycompany.makrobank.view;
import java.util.Scanner;

import com.mycompany.makrobank.controller.UserController;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
public class UserLogin {
    private Scanner scan; 
    public UserLogin(Scanner scan){
        this.scan = scan;
    }

    public void startLogin(){
        System.out.println("Welcome to the Makro bank, you're allways welcome.");
        System.out.println("Create a account with us, or log into your accoutn.");
        System.out.println("Type some of the options bellow to continue:");
        String firstAction = "";
        while(true){
            System.out.println("[1] - To create a account\n[2] - Login\n[3] - To exit");
            firstAction = scan.nextLine();
            if(!(firstAction.equals("1") || (firstAction.equals("2") ||
                    !(firstAction.equals("3"))))){
                System.out.println("Please, write some valid option!");
                continue;
            }
            if(firstAction.equals("1")){
                if(create()){
                    System.out.println("Your account has been created successfully!\n");
                }else {
                    return;
                }
            }else if(firstAction.equals("2")){
                if(login()){
                    System.out.println("logged");
                }
            }
        }
    }
    public boolean create(){ // returns true if the account can be created
        String name = "";
        String password = "";
        while(true){
            System.out.print("Type a name: ");
            name = scan.nextLine();
            if(name.isEmpty()){
                System.out.println("Write a valid name.");
                continue;
            }
            System.out.print("Type a password: ");
            password = scan.nextLine();

            System.out.print("Type your age (like: 20) minimum is 18 and max 99 years old: ");
            String strAge = scan.nextLine();
            System.out.println("");
            
            int age = User.canBeParsed(strAge);
            if(age == -1){
                System.out.println("Please, write a valid age value, like '30' (write just the number).");
                continue;
            }
            if(!User.haveAgeEnough(age)){
                System.out.println("You dont have age enough to create a account.");
                return false;
            }
            User newUser = new User(name,password,age, new Balance(0));
            UserController ul = new UserController();
            return ul.create(newUser);
        }
    }
    public boolean login(){
        System.out.print("Type your name: ");
        String name = scan.nextLine();
        System.out.print("Type your password: ");
        String userPassword = scan.nextLine();
        UserController ul = new UserController();

        ul.login(name,userPassword);
        return true;
    }
}
