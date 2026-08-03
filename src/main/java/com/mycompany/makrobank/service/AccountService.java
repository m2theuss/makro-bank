package com.mycompany.makrobank.service;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.User;
import com.mycompany.makrobank.security.SecurityInterceptor;

public class AccountService implements SecurityInterceptor {
    private final UserDAO userDAO;

    public AccountService(UserDAO userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public boolean makePixPayment(User sender, String receiverName, double amount){
        if(sender.getName() == null || receiverName == null){
            return false;
        }
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
        if(sender == null || receiverName == null){
            return false;
        }
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
        if(name == null){
            return false;
        }
        return userDAO.updateBalanceByName(name, amount);
    }
    @Override
    public boolean usernameExist(String name){
        User result = userDAO.findUserByName(name);
        if(result == null){
            return false;
        }
        return true;
    }
    @Override
    public boolean deleteAccount(User user){
        if(user == null){
            return false;
        }
        return userDAO.deleteAccount(user);
    }
    @Override
    public boolean makeDeposit(User user,double amount){
        if(user == null){
            return false;
        }
        if(userDAO.updateBalanceByName(user.getName(), amount)){
            user.getBalance().addAmount(amount);
            return true;
        }
        return false;
    }
}
