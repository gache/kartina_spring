package com.formation.kartina_spring.utils;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class MotPassCodifier {

    public byte[] genererSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new  byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public  byte[] genererMotPasse(String motPasse, byte[] salt){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(salt);
            return messageDigest.digest(motPasse.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

}
