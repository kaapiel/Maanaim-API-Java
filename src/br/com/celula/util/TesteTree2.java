package br.com.celula.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import org.apache.commons.codec.binary.Base64;

public class TesteTree2 {

	public static void main(String[] args) throws ParseException, NoSuchAlgorithmException {

	    String password = "123456";

	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(password.getBytes());

	    byte byteData[] = md.digest();

	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < byteData.length; i++)
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

	    System.out.println("Digest(in hex format):: " + sb.toString());
	    byte[] bytes = Base64.encodeBase64(byteData);  
	      String s = new String(bytes);  
	      System.out.println(s);  

	}
	

}
