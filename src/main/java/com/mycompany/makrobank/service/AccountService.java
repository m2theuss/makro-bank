package com.mycompany.makrobank.service;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;

public class AccountService {
    private final UserDAO userDAO;

    public AccountService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    public boolean makePixPayment(User sender, String receiverName, double amount){
        if(sender.getName().equals(receiverName)){
            return false;
        }
        if(!validateBalance(sender, amount)){
            return false;
        }
        return transfer(sender, receiverName, amount);
    }
    private boolean validateBalance(User user, double amountToTransfer){
        return (user.getBalance().getBalance() - amountToTransfer) >= 0;
    }
    private boolean transfer(User sender, String receiverName, double amount){
        boolean senderTransferResult = userDAO.updateBalanceByName(sender.getName(), (amount * -1));
        if(!senderTransferResult){
            return false;
        }
        boolean receiverTransferResult = userDAO.updateBalanceByName(receiverName, amount);
        if(!receiverTransferResult){
            reverseTransfer(sender.getName(), amount);
            return false;
        }
        sender.getBalance().takeAmount(amount);
        return true;
    }
    private boolean reverseTransfer(String name, double amount){
        return userDAO.updateBalanceByName(name, amount);
    }
    public boolean usernameExist(String name){
        return userDAO.findUserByName(name).getName() != null;
    }
    public boolean deleteAccount(User user){
        return userDAO.deleteAccount(user);
    }
    public boolean makeDeposit(User user,double amount){
        if(userDAO.updateBalanceByName(user.getName(), amount)){
            user.getBalance().addAmount(amount);
            return true;
        }
        return false;
    }
}
