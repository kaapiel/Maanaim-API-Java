package br.com.celula.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Reuniao;
import br.com.celula.entidade.ReuniaoMembro;

/**
*
* @author MAURICIO CRUZ
*/
public class ReuniaoMembroDao {
	
	private EntityManager manager;
	
	public ReuniaoMembroDao(){
		this.manager = EttMnger.manager();
	}
	
	public ReuniaoMembroDao(EntityManager m){
		this.manager = m;
	}

	public void adiciona(List<ReuniaoMembro> list){
		
		manager.getTransaction().begin();
		for(ReuniaoMembro r:list){
			manager.merge(r);
			manager.flush();
			manager.clear();
		}
		manager.getTransaction().commit();
	}
	
	public void adiciona(ReuniaoMembro rm){
			manager.getTransaction().begin();
			manager.merge(rm);
			manager.getTransaction().commit();
	}
	
	@SuppressWarnings("unused")
	public void removeMembroReuniao(Reuniao r){
		Query query = manager.createQuery(
			      "DELETE FROM reuniao_membro c WHERE c.reuniao= :r");
			  int deletedCount = query.setParameter("r", r).executeUpdate();	
	}
	
	@SuppressWarnings("unchecked")
	public List<ReuniaoMembro> membrosPresentesNaReuniao(Reuniao reuniao) {
		boolean freq = true;
		String sql = " From reuniao_membro rm Where rm.reuniao= :reuniao and rm.estevepresente= :freq";
		return this.manager.createQuery(sql).setParameter("freq", freq).setParameter("reuniao", reuniao).getResultList();		 
	} 
}
