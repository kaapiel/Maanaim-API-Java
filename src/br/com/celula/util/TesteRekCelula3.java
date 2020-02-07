package br.com.celula.util;

import java.text.ParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;

import br.com.celula.dao.RelatorioDao;
import br.com.celula.modelo.Rel03;

public class TesteRekCelula3 {
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		RelatorioDao dao = new RelatorioDao(manager);

		DateTime di = new DateTime(2013,10,01,0,0);
		DateTime df = new DateTime(2013,10,31,0,0);
		List<Rel03> list = dao.ofertaPorBairro(di.toDate(),df.toDate());
	
		System.out.println(list.size());
		for(Rel03 r : list){
			System.out.println(r.getBairro());
			System.out.println(r.getTotaloferta());
			System.out.println(r.getTotalmembros());
		}
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}
	


}
