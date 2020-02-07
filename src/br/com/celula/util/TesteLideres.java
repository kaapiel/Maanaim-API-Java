package br.com.celula.util;

import br.com.celula.integracao.get.GetLideres;

public class TesteLideres {

	public static void main(String[] args) {
		GetLideres g = new GetLideres();
		System.out.println(g.getLideres().size());

	}

}
