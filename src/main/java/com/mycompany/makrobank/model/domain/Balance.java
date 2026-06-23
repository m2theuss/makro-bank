package com.mycompany.makrobank.model.domain;

public class Balance {
    private double balanceValue;

    public Balance(double balanceValue){
        this.balanceValue = balanceValue;
    }
    public double getBalance(){
        return balanceValue;
    }
    public void addAmount(double amount){
        this.balanceValue += amount;
    }
    public boolean takeAmount(double amount){
        if((balanceValue - amount) < 0){
            System.out.println("You cannot take this amout. Otherwise your balance will become less than 0.");
            return false;
        }
        balanceValue -= amount;
        return true;
    }
}
