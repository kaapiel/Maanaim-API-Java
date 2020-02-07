package br.com.celula.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.DateTime;

import br.com.celula.dao.RelatorioDao;
import br.com.celula.modelo.Rel02;

public class TesteRekCelula2 {
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();
		RelatorioDao dao = new RelatorioDao(manager);
		List<String> regioes = new ArrayList<String>();
		regioes.add("verde");
		regioes.add("azul");
		DateTime di = new DateTime(2013,10,01,0,0);
		DateTime df = new DateTime(2013,10,31,0,0);
System.out.println(di.toDate());
System.out.println(df.toDate());
System.out.println("-----------");
		List<Rel02> list = dao.frequenciaMembroPorMes(di.toDate(),df.toDate(),0,30,0.5,1.0,regioes);
	
		System.out.println(list.size());
		for(Rel02 r : list){
			System.out.println(r.getMembro());
			System.out.println(r.getCelula());
			System.out.println(r.getSubregiao());
			System.out.println(r.getRegiao());
			System.out.println(r.getFrequencia());
		}
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}
	


}
