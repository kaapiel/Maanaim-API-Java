package br.com.celula.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Criptografa {
    public static String md5(String s) throws NoSuchAlgorithmException {   
        MessageDigest md = MessageDigest.getInstance("MD5");   
        md.reset();  
        byte[] array = md.digest(s.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) 
          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(0, 3));          
        return sb.toString();   
    }           
}
