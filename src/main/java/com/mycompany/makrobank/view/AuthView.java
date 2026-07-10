package com.mycompany.makrobank.view;
import java.io.Console;
import java.util.Scanner;
import java.util.function.Supplier;

import com.mycompany.makrobank.controller.AuthController;
import com.mycompany.makrobank.model.domain.Balance;
import com.mycompany.makrobank.model.domain.User;
public class AuthView {
    private final Scanner scan; 
    public AuthView(Scanner scan){
        this.scan = scan;
    }

    public void start(){
        clearConsole();
        System.out.println("Welcome to the Makro bank, you're allways welcome!");
        System.out.println("Type some of the options bellow to continue:");
        while(true){
            System.out.println("""
                [1] - To create a account
                [2] - Login
                [3] - To exit""");
            System.out.print("> ");
            Integer firstAction = null;
            try{
                firstAction = scan.nextInt();
                scan.nextLine();
                if(!(firstAction >= 1 && firstAction <= 3)){
                    System.out.println("Please, write some valid option.");
                }
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
        System.out.println("Let's create an account!");
        String name = readName();
        String password = null;
        while(true){
            System.out.print("Do you want hide your password while typing (Y or N)? ");
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
        System.out.print("Type the password: ");
        String password = null;
        System.out.print("Hide password while typing (Type: 'Y' to accept)? ");
        String showPassword = scan.nextLine().toUpperCase();
        while(true){
            if("Y".equals(showPassword)){
                password = readHiddenPassword();
                break;
            }else if("N".equals(showPassword)){
                password = readPassword();
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
            if(!ageInSring.isEmpty() && !(ageInSring == null)){
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
