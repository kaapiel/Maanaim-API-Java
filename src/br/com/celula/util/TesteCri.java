package br.com.celula.util;

import java.security.NoSuchAlgorithmException;

public class TesteCri {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		String senhadigitada = "mauricio";
		String senhadobanco = Criptografa.md5(senhadigitada);
System.out.println(senhadigitada);
System.out.println(senhadobanco);
System.out.println(senhadobanco.equals(Criptografa.md5(senhadigitada)));


	}

}
