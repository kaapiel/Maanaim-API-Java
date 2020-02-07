package br.com.celula.util;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.celula.entidade.Membro;
import br.com.celula.negocio.MembroLN;

public class Teste {

	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		MembroLN ln = new MembroLN(manager);
		System.out.println(ln.totalMembrosAtivos());
		List<Membro>  rs = ln.getListMembros();
/*
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		Random random = new Random();
		for(Membro m:rs){
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 6; i++) {
			    char c = chars[random.nextInt(chars.length)];
			    sb.append(c);
			}
			m.setNome(sb.toString().toUpperCase());
			manager.merge(m);
		}
*/
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}


}
