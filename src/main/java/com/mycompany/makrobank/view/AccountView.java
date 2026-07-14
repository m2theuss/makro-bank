package com.mycompany.makrobank.view;

import java.util.Scanner;

import com.mycompany.makrobank.model.domain.User;

public class AccountView {
    private User user;
    private Scanner scan;
    public AccountView(User user, Scanner scan){
        this.user = user; 
        this.scan = scan;
    }

    public void start(){
        if(user == null){
            System.out.println("User cannot be null.");
            return;
        }

    }
    public int readOptions(){
        while(true){
            System.out.println("Type the option that you want.");
            System.out.println("[1] - See my balance\n");
            
        }
    }

}
