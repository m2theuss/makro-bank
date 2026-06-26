package com.mycompany.makrobank.controller;
import com.mycompany.makrobank.model.dao.UserDAO;
import com.mycompany.makrobank.model.domain.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
public class UserLoginController{
    public UserLoginController(){
    }
    public boolean createUser(User user) { //true if the user has been created sucesufully
        user.setSalt(fromByteToString(saltGenerator()));
        user.setPassword(fromByteToString(hashGenerator(user.getPassword(), fromStringToByte(user.getSalt()))));
        return new UserDAO().create(user);
    }
    public byte[] hashGenerator(String plainText, byte[] salt){
        try{
            PBEKeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt, 200000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            System.out.println("A error happen when try run login controller: " + e.getMessage());
            return null;
        }
    }
    public byte[] saltGenerator(){
        SecureRandom randomValue = new SecureRandom();
        byte[] bytes = new byte[16];
        randomValue.nextBytes(bytes);
        return bytes;
    }
    public String fromByteToString(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    public byte[] fromStringToByte(String value){
        return Base64.getDecoder().decode(value);
    }

}
