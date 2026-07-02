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
        clearConsole();
        System.out.println("Welcome to the Makro bank, you're allways welcome.");
        System.out.println("Create a account with us, or log into your accoutn.");
        System.out.println("Type some of the options bellow to continue:");
        String firstAction = "";
        while(true){
            System.out.println("[1] - To create a account\n[2] - Login\n[3] - To exit");
            System.out.print("> ");

            firstAction = scan.nextLine();
            if(!(firstAction.equals("1") || (firstAction.equals("2") ||
                    !(firstAction.equals("3"))))){
                System.out.println("Please, write some valid option!");
                continue;
            }
            if(firstAction.equals("1")){
                if(create()){
                    clearConsole();
                    System.out.println("Your account has been created successfully!\n");
                }else{
                    clearConsole();
                    System.out.println("This name already exists, try again with other name.");

                }
            }else if(firstAction.equals("2")){
                if(login()){
                    clearConsole();
                    System.out.println("logged");
                }else{
                    clearConsole();
                    System.out.println("Your password or username is incorrect, try again.");
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
        return ul.login(name,userPassword);
    }
    public static void clearConsole() { 
        final String ANSI_CLS = "\u001b[2J"; 
        final String ANSI_HOME = "\u001b[H"; 
        System.out.print(ANSI_CLS + ANSI_HOME); 
        System.out.flush(); 
    }
}
