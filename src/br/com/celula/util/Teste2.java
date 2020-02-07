package br.com.celula.util;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.celula.dao.RelatorioDao;
import br.com.celula.entidade.ConsA;

public class Teste2 {

	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		RelatorioDao dao = new RelatorioDao(manager);
		List<ConsA>  rs = dao.frequenciaPorMes(10, 2013);
		for(ConsA s:rs){
			System.out.println(s.getCelula()); //nome celula
			System.out.println(s.getData()); //data
			System.out.println(s.getTotal()); //total
			System.out.println(s.getPresente()); //prese
			System.out.println(s.getAusente()); //ausente
			System.out.println(s.getPercPresente());
			System.out.println(s.getPercAusente());
		}
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}


}
