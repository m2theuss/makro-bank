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
        boolean senderResult = userDAO.updateBalanceByName(sender.getName(), (amount * -1));
        sender.getBalance().takeAmount(amount);

        boolean receiverResult = userDAO.updateBalanceByName(receiverName, amount);
        if(senderResult && receiverResult){
            return true;
        }
        return false;
    }
    public boolean validateUsers()
    public boolean nameExist(String userName){
        return userDAO.findNameByName(userName) != null;
    }
}
