package br.com.celula.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;

/**
*
* @author MAURICIO CRUZ
*/
public class RegiaoDao {
	
	private EntityManager manager;
	
	public RegiaoDao(){
		this.manager = EttMnger.manager();
	}


	public boolean existeRegiaoCadastrada() {		  
	      Query query = this.manager.createQuery("Select count(idregiao) FROM regiao "); 
	      Long i =(Long) query.getSingleResult();
	      return (i>0);   
	}  

}
