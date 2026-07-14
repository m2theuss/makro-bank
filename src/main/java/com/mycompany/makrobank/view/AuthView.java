package com.mycompany.makrobank.view;
import java.io.Console;
import java.util.Scanner;
import java.util.function.Supplier;

import com.mycompany.makrobank.controller.AuthController;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.security.TokenService;
public class AuthView {
    private final Scanner scan; 
    private final String SHOW_PASSWORD_WHILE_TYPING = "Do you want hide your password while typing (Y or N)? ";
    public AuthView(Scanner scan){
        this.scan = scan;
    }

    public void start(){
        clearConsole();
        System.out.println("Welcome to the Makro bank, you're allways welcome!");
        System.out.println("Type some of the options bellow to continue:");
        while(true){
            String action = executeAction(readOptions());
            if(action == null){
                System.out.println("Bye");
                return;
            }
            System.out.println(action);
        }

    }
    public int readOptions(){
        final String INVALID_OPTION = "Please, write some valid option.";
        while(true){
            System.out.println("""
                [1] - To create a account
                [2] - Login
                [3] - To exit""");
            System.out.print("> ");
            try{
                int option = scan.nextInt();
                scan.nextLine();
                if(option >= 1 && option <= 3){
                    return option;
                }
                System.out.println(INVALID_OPTION);
            }catch(Exception e){
                System.out.println(INVALID_OPTION);
                continue;
            }
        }
    }
    public String executeAction(int option){
        switch (option) {
                case 1 -> {
                    if(create()){
                        return "Your account was created!";
                    }else{
                        return "A error happen, try again or later.";
                    }
                }
                case 2 -> {
                    User user = login();
                    if(user == null){
                        return "Your password or username is incorrect, try again.";   
                    }
                    AccountView accountView = new AccountView(user,scan);
                    accountView.start();
                    return "Your is now logged";
                }
                case 3 -> {
                    return null;
                }
                default -> {
                    return "A valid option is 1,2 or 3. Try again.";
                }
            }
    }

    private boolean create(){ 
        System.out.println("Let's create an account!");
        String name = readName();
        String password = null;
        while(true){
            System.out.print(SHOW_PASSWORD_WHILE_TYPING);
            String decision = scan.nextLine().toUpperCase();
            if("Y".equals(decision)){
                password = readHiddenPassword();
                break;
            }else if("N".equals(decision)){
                password = readPassword();
                break;
            }else{
                System.out.println("Invalid option, try again.");
            }
        }
        if(password == null){
            return false;
        }
        int age = readAge();
        User newUser = new User(name,password,age, new Balance(0));
        AuthController controller = new AuthController();
        return controller.create(newUser);
    }
    private User login(){
        System.out.print("Type the user: ");
        String name = scan.nextLine();
        System.out.print(SHOW_PASSWORD_WHILE_TYPING);
        String showPassword = scan.nextLine().toUpperCase();
        String password = null;
        while(true){
            if("Y".equals(showPassword)){
                System.out.print("Type (won't show): ");
                password = readHiddenInput();
                break;
            }else if("N".equals(showPassword)){
                System.out.print("Type: ");
                password = scan.nextLine();
                break;
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
            nameVerificationResult = validateName(name);
            if(nameVerificationResult == null){
                return name;
            }
            System.out.println(nameVerificationResult);
            continue;
        }
    }
    private String validateName(String name){
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
        return genericPasswordReader(
            () -> scan.nextLine(),
            () -> System.out.print("Type: ")
        );
    }
    private String readHiddenPassword(){
        return genericPasswordReader(
            () -> readHiddenInput(),
            () -> System.out.print("Type (won't show): ")
        );
    }
    private String genericPasswordReader(Supplier<String> supplier, Runnable runnable){
        while(true){
            runnable.run();
            String password = supplier.get();
            String error = validatePassword(password);
            if(error == null){
                for(int i = 3; i >= 0; i--){
                    System.out.print("Type again to confirm your password: ");
                    if(password.equals(supplier.get())){
                        return password;
                    }
                    System.out.println("The password do not match, try again (you have " + i + " more attempts)." );
                }
                return null;
            }else{
                System.out.println(error);
            }
        }
    }
    public String readHiddenInput(){
        Console console = System.console();
        if(console == null){
            System.out.println("Warning: The program cannot find a console, which means that it's running in an IDE. Please run it in a simple terminal.");
            System.out.print("Type your password (it will be visible in the IDE terminal): ");
            return scan.nextLine();
        }
        return String.valueOf(console.readPassword());
    }

    private String validatePassword(String password){
        if(password == null){
            return "Null value are not accepted";
        }else if(password.isEmpty()){
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
        while(true){
            System.out.print("Type your age (just integers like '20'): ");
            Integer ageConverted = convertAge(scan.nextLine());
            if(ageConverted == null){
                System.out.println("Write a valid age!");
                continue;
            }
            if(ageConverted < 18 || ageConverted > 99){
                System.out.println("Minimum age is 18 years old and max is 99 years old.");
                continue;
            }
            return ageConverted;
        }
    }
    public Integer convertAge(String ageInSring){
        try{
            if((ageInSring != null) && (!ageInSring.isEmpty())){
                return Integer.valueOf(ageInSring);
            }else{
                return null;
            }
        }catch(NumberFormatException  e){
            return null;
        }
    }

    private void clearConsole() { 
        final String ANSI_CLS = "\u001b[2J"; 
        final String ANSI_HOME = "\u001b[H"; 
        System.out.print(ANSI_CLS + ANSI_HOME); 
        System.out.flush(); 
    }
}
