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
        String name = readName();
        String password = readPassword();
        int age = readAge();
        User newUser = new User(name,password,age, new Balance(0));
        AuthController controller = new AuthController();
        return controller.create(newUser);
    }
    private User login(){
        System.out.print("Type the user: ");
        String name = scan.nextLine();
        System.out.print("Type the password: ");
        String password = null;
        System.out.print("Hide password while typing (Type: 'Y' to accept)? ");
        String showPassword = scan.nextLine().toUpperCase();
        while(true){
            if("Y".equals(showPassword)){
                password = readHiddenInput();
                if(password != null){
                    break;
                }
                showPassword = null;
            }else{
                password = scan.nextLine();
            }
        }
        AuthController controller = new AuthController();
        return controller.login(name,password);
    }
    private String readName(){
        String nameVerificationResult = null;
        String name = null;
        while(true){
            System.out.print("Type name: ");
            name = scan.nextLine();
            nameVerificationResult = nameValidation(name);
            if(nameVerificationResult == null){
                return name;
            }
            System.out.println(nameVerificationResult);
            continue;
        }
    }
    private String nameValidation(String name){
        if(name.isEmpty()){
            return "Empty names are not accepted.";
        }else if(name.length() > 30){
            return "Write a name with at most 30 letters.";
        }else if(name.contains(" ") || name.contains(".")){
            return "The name dont must contain spaces or points.";
        }else if(new AuthController().nameUserExist(name)){
            return "This name already exists, try again "
                            + "with other name.";
        }else{
            return null;
        }
    }
    private String readPassword(){
        String password = null;
        String passwordVerificationResult = null;
        while(true){
            System.out.print("Type a password: ");
            password = scan.nextLine();
            passwordVerificationResult = passwordValidation(password);
            if(passwordVerificationResult == null){
                System.out.print("Type again to confirm your password: ");
                if(password.equals(scan.nextLine())){
                    return password;
                }
                System.out.println("The password dont match, try again!");
            }else{
                System.out.println(passwordVerificationResult);
            }
        }
    }
    
    public String readHiddenInput(){
        Console console = System.console();
        if(console == null){
            return null;
        }
        while(true){
            System.out.print("Type (the password wont show): ");
            return String.valueOf(console.readPassword());
        }
    }

    private String passwordValidation(String password){
        if(password.isEmpty()){
            return "Empty passwords are not accepted.";
        }else if(password.length() < 8 || password.length() > 64){
            return "Write a password with at least 8 characters "
                    + "and at most 64 characters.";
        }else if(password.contains(" ")){
            return "The password dont must contain spaces.";
        }else{
            return null;
        }
    }

    private int readAge(){
        Integer ageConverted = null;
        String ageResultValidation = null;
        while(true){
            System.out.print("Type your age (Just integers like '20'): ");
            ageConverted = convertAge(scan.nextLine());
            if(ageConverted == null){
                System.out.println("Write a valid age!");
                continue;
            }
            ageResultValidation = validateAge(ageConverted);
            if(ageResultValidation == null){
                return ageConverted;
            }
            System.out.println(ageResultValidation);
        }
    }
    public Integer convertAge(String ageInSring){
        try{
            Integer age = null;
            if(!ageInSring.isEmpty() && !(ageInSring == null)){
                age = Integer.valueOf(ageInSring);
                return age;
            }else{
                return null;
            }
        }catch(NumberFormatException  e){
            return null;
        }
    }
    public String validateAge(int age){
        if(age < 18 || age > 99){
            return "Minimum age is 18 years old and max is 99 years old.";
        }
        return null;
            
    }

    private void clearConsole() { 
        final String ANSI_CLS = "\u001b[2J"; 
        final String ANSI_HOME = "\u001b[H"; 
        System.out.print(ANSI_CLS + ANSI_HOME); 
        System.out.flush(); 
    }
}
