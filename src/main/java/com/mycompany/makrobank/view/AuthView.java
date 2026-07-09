package com.mycompany.makrobank.view;
import java.util.Scanner;

import com.mycompany.makrobank.controller.AuthController;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
import java.io.Console;
public class AuthView {
    private final Scanner scan; 
    public AuthView(Scanner scan){
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
                        System.out.println("A error happen, try again or later.");
                    }
                }
                case 2 -> {
                    User user = login();
                    if(user != null){
                        clearConsole();
                        System.out.println("Your is now logged, your token is: " + user.getPayload());
                        
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
        System.out.println("Lets create an account!");
        String name = readUserName();
        String password = readUserPassword();
        int age = readUserAge();
        User newUser = new User(name,password,age, new Balance(0));
        AuthController controller = new AuthController();
        return controller.create(newUser);
    }
    private User login(){
        System.out.print("Type the user: ");
        String name = scan.nextLine();
        System.out.print("Type the password: ");
        String userPassword = scan.nextLine();
        AuthController controller = new AuthController();
        return controller.login(name,userPassword);
    }
    private String readUserName(){
        String name = "";
        clearConsole();
        while(true){
            System.out.print("Type name: ");
            name = scan.nextLine();
            if(name.isEmpty()){
                System.out.println("Empty names are not accepted.");
            }else if(name.length() > 30){
                System.out.println("Write a name with at most 30 letters.");
            }else if(name.contains(" ") || name.contains(".")){
                System.out.println("The name dont must contain spaces or points.");
            }else if(new AuthController().nameUserExist(name)){
                System.out.println("This name already exists, try again "
                                + "with other name.");
            }else{
                break;
            }
        }
        return name;
    }
    private String readUserPassword(){
        String password = "";
        String passwordConfirmation = "";
        System.out.print("Hide password while typing (Type: 'Y' to accept)? ");
        String showPassWhileTyping = scan.nextLine().toUpperCase();
        System.out.println("Type a password (at least with the size of 8).");
        while(true){
            if(showPassWhileTyping.equals("Y")){
                password = readUserPasswordSafety();
                if(password == null){
                    showPassWhileTyping = "N";
                    continue; 
                }
                return password;
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
        String firtPasswordSafe = "";
        String secondPasswordSafe = "";
        if(console == null){
            System.out.println("No console avaliable. "
                    + "Please, dont run this program inside of some IDE terminal. "
                    + "(The password will be showed while its typing.\n)");
            return null;
        }
        while(true){
            System.out.print("Type (the password wont show): ");
            firtPasswordSafe = String.valueOf(console.readPassword());
            if(passwordIsValid(firtPasswordSafe)){
                System.out.print("Type again: ");
                secondPasswordSafe = String.valueOf(console.readPassword());
                if(firtPasswordSafe.equals(secondPasswordSafe)){
                    return firtPasswordSafe;
                }
                System.out.println("The passwords dont match. Try again!");
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
        String tmpAge = null;
        while(true){
            System.out.print("Type your age (like: 20) minimum is 18 and max 99"
                    + " years old: ");
            try{
                tmpAge = scan.nextLine();
                if(!tmpAge.isEmpty()){
                    age = Integer.valueOf(tmpAge);
                }else{
                    System.out.println("Empty values are not accepted.");
                    continue;
                }
            }catch(NumberFormatException  e){
                System.out.println("Write valid age (just intger number).");
                continue;
            }

            if(age < 18 || age > 99){
                System.out.println("Minimum age is 18 years old and max is 99 years old.");
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
