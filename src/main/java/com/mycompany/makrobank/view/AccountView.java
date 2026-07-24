package com.mycompany.makrobank.view;

import java.util.Scanner;
import com.mycompany.makrobank.controller.AccountController;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.security.TokenService;

public class AccountView {
    private User user;
    private Scanner scan;
    private AccountController accountController;
    private TokenService tokenService;
    public AccountView(User user, Scanner scan){
        this.user = user; 
        this.scan = scan;
        this.accountController = new AccountController();
        this.tokenService = new TokenService();
    }

    public void start(User user){
        if(user == null){
            System.out.println("User cannot be null.");
            return;
        }
        System.out.println(executeAction(readOptions()));

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
    public String executeAction(int option){
        switch (option){
            case 1 ->{
                return "Your balance is actually: " + user.getInstanceOfBalance().getBalance();
            }
            case 2 -> {
                if(preparePixPayment()){
                    return "Pix was made with succesfully!";
                }
                return "Some error happen, try again or later.";
            }
        }
        return null;
    }
    public boolean preparePixPayment(){
        while(true){
            double amountToSend = readPixAmount();
            if(isPixAmountValid(amountToSend)){
                if(hasEnoughBalance(amountToSend)){
                    String pixReceiver = scan.nextLine();
                    return makePixPayment(pixReceiver, amountToSend);
                }  
            }
            System.out.println("Write a valid pix value to transfer (more than 0)");
        }
    }
    public double readPixAmount() {
        while (true) {
            System.out.print("How much money do you want to transfer? ");
            try {
                String tmp = scan.nextLine();
                if (tmp == null || tmp.trim().isEmpty()) {
                    System.out.println("Empty values are not accepted!");
                    continue;
                }
                return Double.valueOf(tmp);
                
            } catch (NumberFormatException e) {
                System.out.println("Write just a number!");
            }
        }
    }
    public boolean isPixAmountValid(double amount) {
        if (amount <= 0) {
            return false;
        }
        return true;
    }
    public boolean hasEnoughBalance(double amount){
        if((user.getInstanceOfBalance().getBalance() - amount) < 0){
            return false;
        }
        return true;
    }
    public boolean makePixPayment(String receiverName, double amountToSend){
        if(accountController.receiverExist(receiverName)){
            return accountController.makePixPayment(user.getName(),receiverName, amountToSend);
        }
        return false;
    }

    public void printUserDetails(){
        System.out.println("=========== USER INFORMATIONS ============");
        System.out.println("Name of the user:" + user.getName());
        System.out.println("Current balance: " + user.getInstanceOfBalance().getBalance());
        System.out.println("==========================================");
    }
    public boolean checkJWT(){
        return tokenService.tokenIsValid(user.getJWT());
    }
}
