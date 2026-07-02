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

    public void start(){
        clearConsole();
        System.out.println("Welcome to the Makro bank, you're allways welcome!");
        System.out.println("Type some of the options bellow to continue:");
        Integer firstAction = null;
        while(true){
            System.out.println("[1] - To create a account\n[2] - Login\n[3] - To exit");
            System.out.print("> ");
            try{
                firstAction = scan.nextInt();
                if(!(firstAction >= 1 && firstAction <= 3)){
                    throw new Exception();
                }
            }catch(Exception e){
                clearConsole();
                System.out.println("Please, write some valid option.");
                continue;
            }

            if(firstAction == 1){
                if(create()){
                    clearConsole();
                    System.out.println("Your account has been created successfully!\n");
                }else{
                    clearConsole();
                    System.out.println("This name already exists, try again with other name.");
                }
            }else if(firstAction == 2){
                if(login()){
                    clearConsole();
                    System.out.println("Your ir now logged");
                }else{
                    clearConsole();
                    System.out.println("Your password or username is incorrect, try again.");
                }
            }else if(firstAction == 3){
                clearConsole();
                System.out.println("Bye!");
                return;
            }
        }
    }
    private boolean create(){ 
        String name = readUserName();
        String password = readUserPassword();
        int age = readUserAge();
        User newUser = new User(name,password,age, new Balance(0));
        UserController ul = new UserController();
        return ul.create(newUser);
    }
    private boolean login(){
        System.out.print("Type your name: ");
        scan.nextLine();
        String name = scan.nextLine();
        System.out.print("Type your password: ");
        String userPassword = scan.nextLine();
        
        UserController ul = new UserController();
        return ul.login(name,userPassword);
    }
    private String readUserName(){
        String name = "";
        while(true){
            System.out.print("Type a name: ");
            name = scan.nextLine();
            if(name.isEmpty()){
                clearConsole();
                System.out.println("Empty names are not accepted.");
            }else if(name.length() > 30){
                clearConsole();
                System.out.println("Write a name with at most 30 letters.");
            }else if(name.contains(" ")){
                clearConsole();
                System.out.println("The name dont must contain spaces.");
            }else{
                break;
            }
        }
        return name;
    }
    private String readUserPassword(){
        String password = "";
        while(true){
            System.out.print("Type a password (at least with the size of 8): ");
            password = scan.nextLine(); 
            if(password.isEmpty()){
                clearConsole();
                System.out.println("Empty passwords are not accepted.");
            }else if(password.length() < 8 || password.length() > 64){
                clearConsole();
                System.out.println("Write a password with at least 8 characters and at most 64 characters.");
            }else if(password.contains(" ")){
                clearConsole();
                System.out.println("The password dont must contain spaces.");
            }else{
                break;
            }
        }
        return password;
    }
    private int readUserAge(){
        Integer age = null;
        while(true){
            System.out.print("Type your age (like: 20) minimum is 18 and max 99 years old: ");
            try{
                age = scan.nextInt();
            }catch(Exception e){
                System.out.println("Write a valid age (just number).");
                clearConsole();
                continue;
            }            
            if(age < 18){
                clearConsole();
                System.out.println("Minimum age is 18 years old.");
                continue;
            }
            if(age > 99){
                clearConsole();
                System.out.println("Maximum age is 99 years old.");
                continue;
            }
            return age;
        }
    }
    private void clearConsole() { 
        final String ANSI_CLS = "\u001b[2J"; 
        final String ANSI_HOME = "\u001b[H"; 
        System.out.print(ANSI_CLS + ANSI_HOME); 
        System.out.flush(); 
    }
}
