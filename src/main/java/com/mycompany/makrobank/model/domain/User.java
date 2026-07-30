package com.mycompany.makrobank.model.domain;

public class User {
    private String name;
    private String password;
    private int age;
    private Balance balance;
    private String token;
    private String salt;

    public User(String name, String password, int age) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.balance = new Balance(0);
        this.token = "";
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
    public String getToken(){
        return token;
    }
    public void setToken(String payload){
        this.token = payload;
    }
    public Balance getBalance(){
        return balance;
    }
    public static boolean isAdult(int age){
        if(age > 18 && age < 99){
            return true;
        }
        return false;
    }
}