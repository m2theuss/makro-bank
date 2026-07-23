package com.mycompany.makrobank.view;

import java.util.Scanner;
import com.mycompany.makrobank.controller.AccountController;
import com.mycompany.makrobank.model.domain.User;

public class AccountView {
    private User user;
    private Scanner scan;
    private AccountController accountController;
    public AccountView(User user, Scanner scan){
        this.user = user; 
        this.scan = scan;
        this.accountController = new AccountController();
    }

    public void start(User user){
        if(user == null){
            System.out.println("User cannot be null.");
            return;
        }
        executeAction(readOptions());

    }
    public int readOptions(){ //return a integer value from 1 to 3 representing a option.
        final String INVALID_OPTION = "Please, write some valid option.";
        while(true){
            System.out.println("""
                [1] - See my balance
                [2] - Make a pix
                [3] - Delete my account
                [4] - Exit""");
            System.out.print("> ");
            try{
                String tmp = scan.nextLine();
                if(!tmp.isEmpty()){
                    int option = Integer.valueOf(tmp);
                    if(option >= 1 && option <= 3){
                        return option;
                    }
                }
                System.out.println(INVALID_OPTION);
            }catch(Exception e){
                System.out.println(INVALID_OPTION);
                continue;
            }
        }
    }
    public void executeAction(int option){
        switch (option){
            case 1 ->{
                System.out.println("Your balance is actually: " + user.getInstanceOfBalance().getBalance());
            }
            case 2 -> {
                String error = accountController.makePix(user);
                if(error == null){
                    return "Your PIX was successfully done!";
                }
                return error;
            }
        }
    }
    public void makePixPayment(){
        int amount = readAndValidatePixAmount();    
    }
    public int readAndValidatePixAmount(){
        while(true){
            System.out.print("How much money do you want to tranfer? ");
            try{
                String tmp = scan.nextLine();
                if(tmp.isEmpty() || tmp == null){
                    System.out.println("Empty value are not accepted!");
                    continue;
                }
                int amount = Integer.valueOf(tmp);
                if(amount > 0){
                    return amount;
                }
                System.out.println("Minimum value is 1.");
            }catch (NumberFormatException e){
                System.out.println("Write just a number!");
            }
        }
    }
    public void printUserDetails(){
        System.out.println("=========== USER INFORMATIONS ============");
        System.out.println("Name of the user:" + user.getName());
        System.out.println("Current balance: " + user.getInstanceOfBalance().getBalance());
        System.out.println("==========================================");
    }
}
