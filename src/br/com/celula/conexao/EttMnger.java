package br.com.celula.conexao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EttMnger {

	public static EntityManager manager(){
		Conexao c = new Conexao();
		return c.getEntityManager();
	}
	
	public static EntityManager getManager(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		return factory.createEntityManager();
	}
}
