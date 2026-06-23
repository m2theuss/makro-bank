/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.makrobank.model.domain;

/**
 *
 * @author matheus
 */
public class User {
    private String name;
    private String password;
    private int age;
    private Balance balance;
    private String makroID;

    public User(String name, String password, int age, Balance balance) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    public Balance getInstanceOfBalance(){
        return balance;
    }
    public static boolean canBeParsed(String valueToParse){ //try parse
        try{
            Integer.valueOf(valueToParse);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
    public static boolean haveAgeEnough(int age){
        if(age > 18 && age < 99){
            return true;
        }
        System.out.println("You dont have age enogh to create a account.");
        return false;
    }
}