package com.mycompany.makrobank.view;

import java.util.Random;
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

    public void start(){
        if(user == null){
            System.out.println("User cannot be null.");
            return;
        }
        System.out.println("Hi, " + user.getName() + ".");
        System.out.println("Type some options to make in your account.");
        while(true){
            String error = executeAction(readOptions());
            if(error != null){
                System.out.println(error);
            }
            break;
        }
    }
    public int readOptions(){ //return a integer value from 1 to 5 representing a option.
        final String INVALID_OPTION = "Please, write some valid option.";
        while(true){
            System.out.println("""
                [1] - See my balance
                [2] - Make a pix
                [3] - Deposit
                [4] - Delete my account
                [5] - Exit""");
            System.out.print("> ");
            try{
                String tmp = scan.nextLine();
                if(!tmp.isEmpty()){
                    int option = Integer.valueOf(tmp);
                    if(option >= 1 && option <= 5){
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
            case 3 ->{
                if(makeDeposit()){
                    return "The deposit was succesfully made!";
                }
                return "A error happen when try make a deposit. Try again or later.";
            }
            case 4 ->{
                if(deleteAccount()){
                   System.out.println("Your account was deleted");
                   return null;
                }
                return "Action was canceled";
            }
            case 5 -> {
                return null;
            }
        }
        return null;
    }
    public boolean preparePixPayment(){
        while(true){
            System.out.println("How much money do you want to transfer? ");
            double amountToSend = readAmount();
            if(isPixAmountValid(amountToSend)){
                if(hasEnoughBalance(amountToSend)){
                    String receiver = readReceiver();
                    if(receiver.equals(user.getName())){
                        System.out.println("You cannot tranfer to yourself.");
                        continue;
                    }
                    return makePixPayment(receiver, amountToSend);
                }
                System.out.println("You dont have enough balance, try again.");
                continue; 
            }
            System.out.println("Write a valid pix value to transfer (more than 0)");
        }
    }
    public double readAmount() {
        while (true) {
            System.out.print("Type: ");
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
    public String readReceiver(){
        while(true){
            System.out.print("Write the name of the receiver: ");
            String receiver = scan.nextLine();
            if(accountController.receiverExist(receiver)){
                return receiver;
            }
            System.out.println("The user dont exist, try again!");
        }
    }
    public boolean makePixPayment(String receiverName, double amountToSend){
        if(accountController.receiverExist(receiverName)){
            return accountController.makePixPayment(user,receiverName, amountToSend);
        }
        return false;
    }
    public boolean makeDeposit(){
        System.out.println("Write a value to be deposited. ");
        double amount = readAmount();
        if(accountController.makeDeposit(user, amount)){
            return true;
        }
        return false;
    }
    public int readDeposit(){
        while(true){
        }
    }
    public boolean deleteAccount(){
        if (shouldDeleteAccount()) {
            if (readAndValidateConfirmationCode()) {
                return accountController.deleteAccount(user);
            }
        }
        return false;
    }

    public boolean readAndValidateConfirmationCode() {
        System.out.println("Write the check code below to confirm this action: ");
        while (true) {
            String code = generateConfirmationCode();
            System.out.println("Code: " + code);
            String codeInput = scan.nextLine();
            if (codeInput == null) {
                System.out.println("Input stream closed. Action canceled.");
                return false;
            }
            if (codeInput.equals(code)) {
                return true;
            }
            System.out.println("The confirmation code doesn't match. Try again.");
        }
    }
    public boolean shouldDeleteAccount(){
        System.out.println("WARNING: THIS ACTION CANNOT BE UNDONE!");
        while (true) {
            System.out.println("You are about to delete your account, type 'N' to cancel this action or 'Y' to continue. ");
            System.out.print("Type: ");
            String decision = scan.nextLine();
            if (decision == null) {
                System.out.println("Input stream closed. Action canceled.");
                return false;
            }
            decision = decision.trim();
            if ("Y".equalsIgnoreCase(decision)) {
                return true;
            } 
            if ("N".equalsIgnoreCase(decision)) {
                return false;
            }
            System.out.println("Invalid input. Please type only 'Y' or 'N'.");
        }
    }
    public String generateConfirmationCode(){
        Random random = new Random();
        char randomWord = (char)(65 + random.nextInt(26));
        return String.valueOf(random.nextInt(9000) + 1000 + randomWord);
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
