package br.com.celula.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Avisos;

/**
*
* @author Gabriel Aguido
*/
public class AvisosDao {
	
	private EntityManager manager;
	
	public AvisosDao(){
		this.manager = EttMnger.manager();
	}

	public AvisosDao(EntityManager m) {
		this.manager = m;
	}


	public boolean existeAvisoCadastrado() {		  
	      Query query = this.manager.createQuery("Select count(id) FROM avisos "); 
	      Long i =(Long) query.getSingleResult();
          return (i>0);   
	}  
	
	@SuppressWarnings("unchecked")
	public List<Avisos> getAvisos() {	
		Query query = this.manager.createQuery(" FROM avisos a order by a.dataEvento"); 
	    return query.getResultList();   
	}  	
	
}
