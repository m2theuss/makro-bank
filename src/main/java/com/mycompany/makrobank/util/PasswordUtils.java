/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.makrobank.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author matheus
 */
public class PasswordUtils {
    public static byte[] hashGenerator(String plainText, byte[] salt){
        try{
            PBEKeySpec spec = new PBEKeySpec(plainText.toCharArray(), salt, 200000, 256);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return factory.generateSecret(spec).getEncoded();
        } catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            System.out.println("A error happen when try run login controller: " + e.getMessage());
            return null;
        }
    }
    public static byte[] saltGenerator(){
        SecureRandom randomValue = new SecureRandom();
        byte[] bytes = new byte[16];
        randomValue.nextBytes(bytes);
        return bytes;
    }
    public static String fromByteToString(byte[] bytes){
        return Base64.getEncoder().encodeToString(bytes);
    }
    public static byte[] fromStringToByte(String value){
        return Base64.getDecoder().decode(value);
    }
}
