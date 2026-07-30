package com.mycompany.makrobank.security;

import com.mycompany.makrobank.model.domain.User;
public interface SecurityInterceptor {
    boolean makePixPayment(User sender, String receiverName, double amount);
    boolean usernameExist(String name);
    boolean deleteAccount(User user);
    boolean makeDeposit(User user, double amount);
}
