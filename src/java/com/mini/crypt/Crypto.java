/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mini.crypt;

/**
 *
 * @author prakash-pt2590
 */

import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    
    String password;
    byte[] salt;
    byte[] iv;
    Cipher ci;
    
    public Crypto(String password){
        
        this.password = password;
    }
    
    public byte[] getSalt(){
        
        return salt;
    }
    
    public byte[] getIv(){
        
        return iv;
    }
    
    public void encryptInit(){
        
        salt = new byte[8];
        SecureRandom srandom = new SecureRandom();
        srandom.nextBytes(salt);
        
        SecretKeySpec skey;
        SecretKeyFactory factory;
        
        iv = new byte[16];
        srandom.nextBytes(iv);
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        
        try {
            
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 128);
            SecretKey tmp = factory.generateSecret(spec);
            skey = new SecretKeySpec(tmp.getEncoded(), "AES");
            
            ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ci.init(Cipher.ENCRYPT_MODE, skey, ivspec);
            
        } 
        catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
        }
    }
    
    public void decryptInit(byte[] salt, byte[] iv){
        
        try {
            
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 128);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec skey = new SecretKeySpec(tmp.getEncoded(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv);
            
            ci = Cipher.getInstance("AES/CBC/PKCS5Padding");
            ci.init(Cipher.DECRYPT_MODE, skey, ivspec);
            
        } 
        catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException ex) {
        }
    }
    
    public void encryptFile(byte[] ibuf, OutputStream out){
        
        try {
            
            byte[] obuf = ci.doFinal(ibuf);
            if ( obuf != null ) out.write(obuf);
            
            out.flush();
            out.close();
            
        } catch (IOException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Crypto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] decryptFile(byte[] ibuf){
        
        try{
            
            byte[] obuf = ci.doFinal(ibuf);
            System.out.println("Decrypted");
            if(obuf != null) return obuf;
        }
        catch(BadPaddingException | IllegalBlockSizeException e){
            
            System.out.println(e);
            System.out.println("Error at decryption");
            return null;
        }
        
        System.out.println("Null is returned");
        return null;
    }
     
}
