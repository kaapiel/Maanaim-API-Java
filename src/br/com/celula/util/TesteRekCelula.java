package br.com.celula.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import br.com.celula.dao.CelulaDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.modelo.Rel01;

public class TesteRekCelula {
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		CelulaDao dao = new CelulaDao(manager);
		List<Celula>  rs = dao.celulasAtivas();
		List<Rel01> ts = new ArrayList<Rel01>();
		
		for(Celula c:rs){
			Rel01 t = new Rel01();
			t.setCelula(c);
			for(Membro m: c.getMembros()){
				if(estaNaFaixaEtaria(0, 99, m))
					t.somaTotal(1);				
			}
			ts.add(t);
		}
		for(Rel01 t:ts){
			System.out.println(t.getCelula().getIdcelula() +" - " + t.getCelula().getNome() +"  "+t.getTotal());
		}
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}
	
	private static boolean estaNaFaixaEtaria(Integer inicio, Integer fim, Membro m){
		LocalDate d = null;
		LocalDate hoje = new LocalDate();
		int i=0;
		if(m.getNascimento()!=null){
			d = new LocalDate(m.getNascimento());
			i = Years.yearsBetween(d, hoje).getYears();
			if(i>=inicio && i<=fim)
				return true;
		}
		return false;
	}
}
