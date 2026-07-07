package com.mycompany.makrobank.view;
import java.util.Scanner;

import com.mycompany.makrobank.controller.UserController;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
import java.io.Console;
import java.util.Arrays;
public class UserLogin {
    private final Scanner scan; 
    public UserLogin(Scanner scan){
        this.scan = scan;
    }

    public void start(){
        clearConsole();
        System.out.println("Welcome to the Makro bank, you're allways welcome!");
        System.out.println("Type some of the options bellow to continue:");
        Integer firstAction = null;
        while(true){
            System.out.println("""
                [1] - To create a account
                [2] - Login
                [3] - To exit""");
            System.out.print("> ");
            try{
                firstAction = scan.nextInt();
                if(!(firstAction >= 1 && firstAction <= 3)){
                    throw new Exception();
                }
                scan.nextLine();
            }catch(Exception e){
                clearConsole();
                System.out.println("Please, write some valid option.");
                continue;
            }

            switch (firstAction) {
                case 1 -> {
                    if(create()){
                        clearConsole();
                        System.out.println("Your account was created!");
                    }else{
                        clearConsole();
                        System.out.println("This name already exists, try again "
                                + "with other name.");
                    }
                }
                case 2 -> {
                    if(login()){
                        clearConsole();
                        System.out.println("Your ir now logged");
                    }else{
                        clearConsole();
                        System.out.println("Your password or username is incorrect, try again.");
                    }
                }
                case 3 -> {
                    clearConsole();
                    System.out.println("Bye!");
                    return;
                }
                default -> {
                }
            }
        }
    }
    private boolean create(){ 
        String name = readUserName();
        String password = readUserPassword();
        int age = readUserAge();
        User newUser = new User(name,password,age, new Balance(0));
        UserController controller = new UserController();
        return controller.create(newUser);
    }
    private boolean login(){
        String name = readUserName();
        String userPassword = readUserPassword();
        UserController controller = new UserController();
        return controller.login(name,userPassword);
    }
    private String readUserName(){
        String name = "";
        clearConsole();
        while(true){
            System.out.print("Type a name: ");
            name = scan.nextLine();
            if(name.isEmpty()){
                System.out.println("Empty names are not accepted.");
            }else if(name.length() > 30){
                System.out.println("Write a name with at most 30 letters.");
            }else if(name.contains(" ")){
                System.out.println("The name dont must contain spaces.");
            }else{
                break;
            }
        }
        return name;
    }
    private String readUserPassword(){
        String password = "";
        String passwordConfirmation = "";
        System.out.print("Do you want show your password while typing it? (Type: 'Y' to accept): ");
        String showPassWhileTyping = scan.nextLine().toUpperCase();
        System.out.println("Type a password (at least with the size of 8).");
        while(true){
            if(showPassWhileTyping.equals("Y")){
                password = readUserPasswordSafety();
                if(password == null){
                    showPassWhileTyping = "N";
                    continue; 
                }
            }else{
                System.out.print("Type: ");
                password = scan.nextLine();
                if(passwordIsValid(password)){
                    System.out.print("Type again: ");
                    passwordConfirmation = scan.nextLine();
                    if(password.equals(passwordConfirmation)){
                        return password;
                    }
                    System.out.println("The passwords dont match. Try again!");
                }
            }
        }
    }
    public String readUserPasswordSafety(){
        Console console = System.console();
        String passwordSafe = "";
        String passwordSafeConfirm = "";
        if(console == null){
            System.out.println("No console avaliable. "
                    + "Please, dont run this program inside of some IDE terminal. "
                    + "(The password will be showed while its typing.\n)");
            return null;
        }
        while(true){
            System.out.print("Type: ");
            passwordSafe = String.valueOf(console.readPassword());
            if(passwordIsValid(passwordSafe)){
                System.out.print("Type again: ");
                passwordSafeConfirm = String.valueOf(console.readPassword());
                if(passwordIsValid(passwordSafeConfirm)){
                    if(passwordSafe.equals(passwordSafeConfirm)){
                        return passwordSafe;
                    }
                    System.out.println("The passwords dont match. Try again!");
                }

            }
        }
    }

    private boolean passwordIsValid(String password){
        if(password.isEmpty()){
            System.out.println("Empty passwords are not accepted.");
            return false;
        }else if(password.length() < 8 || password.length() > 64){
            System.out.println("Write a password with at least 8 characters "
                    + "and at most 64 characters.");
            return false;
        }else if(password.contains(" ")){
            System.out.println("The password dont must contain spaces.");
            return false;
        }else{
            return true;
        }
    }

    private int readUserAge(){
        Integer age = null;
        while(true){
            System.out.print("Type your age (like: 20) minimum is 18 and max 99"
                    + " years old: ");
            try{
                age = scan.nextInt();
            }catch(Exception e){
                System.out.println("Write a valid age (just number).");
                continue;
            }            
            if(age < 18){
                System.out.println("Minimum age is 18 years old.");
                continue;
            }
            if(age > 99){
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
