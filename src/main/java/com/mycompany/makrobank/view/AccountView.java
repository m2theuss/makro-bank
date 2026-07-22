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

    public void start(User user){
        if(user == null){
            System.out.println("User cannot be null.");
            return;
        }

    }
    public int readOptions(){ //return a integer value from 1 to 3 representing a option.
        final String INVALID_OPTION = "Please, write some valid option.";
        while(true){
            System.out.println("""
                [1] - See my balance
                [2] - Make a pix""");
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

}
