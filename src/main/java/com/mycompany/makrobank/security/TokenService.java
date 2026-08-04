package com.mycompany.makrobank.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import com.mycompany.makrobank.util.PasswordUtils;

public class TokenService {
    private final String secreteKey = "3mrHOsC3qB8ia4O8"; 
    //the secrete key is intentionaly hardcoded.
    public TokenService(){}

    public String generateToken(String userName){ 
        String expiration = LocalDateTime.now().plusMinutes(1).truncatedTo(ChronoUnit.MINUTES).toString();
        return generateTokenHash(expiration, userName);
    }
    public String generateTokenHash(String expiration, String userName){ 
    //return a String in base64 with expirationTime.username.hash
        String baseHash = expiration+userName+secreteKey;
        byte[] hash = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(baseHash.getBytes(StandardCharsets.UTF_8));
        }catch (NoSuchAlgorithmException e){
            System.out.println("Sha 256 algorithm dont was found.");
        }
        String finalHash = PasswordUtils.fromByteToStringInBase64(hash);
        //return a string that contain a payload.
        return Base64.getEncoder().encodeToString(String.join(".", expiration, userName, finalHash).getBytes());
    }
    public boolean isTokenValid(String payload){
        try{
            String[] tmpPayload = getToken(payload);
            if(tmpPayload == null){
                return false;
            }
            String userHash = tmpPayload[2];
            //the order of the payload is expirationTime, userName and hash in base64 to be compared.
            String newHash = getToken(generateTokenHash(tmpPayload[0], tmpPayload[1]))[2];
            if(newHash.equals(userHash)){
                LocalDateTime ldt = LocalDateTime.parse(tmpPayload[0]);
                if(ldt.isAfter(LocalDateTime.now())){
                    return true;
                }
            }
            return false;
        }catch(IllegalArgumentException e){
            System.out.println("The value passed to token checker isn't a base 64.");
            return false;
        }
    }
    private String[] getToken(String payload){
        String[] tmpArray = new String(
                Base64.getDecoder().decode(payload),StandardCharsets.UTF_8
            ).split("\\.");
        if(tmpArray.length == 3){
            return tmpArray;
        }
        return null;
    }
}
