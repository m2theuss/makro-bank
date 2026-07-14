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
    private String payload;
    private String salt;

    public User(){}
    public User(String name, String password, int age, Balance balance) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.balance = balance;
        this.payload = "";
        this.salt = "";
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
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    public String getPayload(){
        return payload;
    }
    public void setPayload(String payload){
        this.payload = payload;
    }
    public Balance getInstanceOfBalance(){
        return balance;
    }
    public static boolean isAdult(int age){
        if(age > 18 && age < 99){
            return true;
        }
        return false;
    }
}