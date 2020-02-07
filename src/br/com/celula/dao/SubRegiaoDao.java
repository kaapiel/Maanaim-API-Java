package br.com.celula.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;

/**
*
* @author MAURICIO CRUZ
*/
public class SubRegiaoDao {
	
	private EntityManager manager;
	
	public SubRegiaoDao(){
		this.manager = EttMnger.manager();
	}


	public boolean existeSubRegiaoCadastrada() {		  
	      Query query = this.manager.createQuery("Select count(idsubregiao) FROM subregiao "); 
	      Long i =(Long) query.getSingleResult();
          return (i>0);   
	}  

}
