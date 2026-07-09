package com.mycompany.makrobank.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Base64;
import com.mycompany.makrobank.util.PasswordUtils;

public class TokenService {
    private final String secreteKey = "3mrHOsC3qB8ia4O8"; 
    //the secrete key is intentionaly hardcoded.
    public TokenService(){}

    public String generateToken(String userName){
        String expiration = LocalDateTime.now().plusMinutes(1).toString();
        return tokenHashGenerator(expiration.replace(".",":"), userName);
    }
    public String tokenHashGenerator(String expiration, String userName){
        String baseHash = expiration+userName+secreteKey;
        byte[] hash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(baseHash.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException e){
            System.out.println("Sha 256 algorithm dont was found.");
        }
        String finalHash = PasswordUtils.fromByteToString(hash);
        //return a string that contain a payload.
        return Base64.getEncoder().encodeToString(String.join(".", expiration, userName, finalHash).getBytes());
    }
    public boolean tokenIsValid(String payload){
        try{
            String[] tmpPayload = PasswordUtils.fromByteToString(
                Base64.getDecoder().decode(payload)).split(".");
                //the order of the payload is expiration time, userName and hash.
            if(tmpPayload.length != 3){
                return false;
            }
            String userHash = tmpPayload[3];
            if(tokenHashGenerator(tmpPayload[1], tmpPayload[2]).compareTo(userHash) == 0){
                return true;
            }
            return false;

        }catch(IllegalArgumentException e){
            System.out.println("The value pass to token checker its not a base 64.");
            return false;
        }
    }
}
