package br.com.celula.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Celula;

/**
*
* @author MAURICIO CRUZ
*/
public class CelulaDao {
	
	private EntityManager manager;
	
	public CelulaDao(){
		this.manager = EttMnger.manager();
	}

	public CelulaDao(EntityManager m) {
		this.manager = m;
	}


	public boolean existeCelulaCadastrada() {		  
	      Query query = this.manager.createQuery("Select count(idcelula) FROM celula "); 
	      Long i =(Long) query.getSingleResult();
          return (i>0);   
	}  
	
	@SuppressWarnings("unchecked")
	public List<Celula> celulasAtivas() {	
		boolean ativo = true;
	    Query query = this.manager.createQuery(" FROM celula c where c.ativo= :ativo order by c.idcelula"); 
	    return query.setParameter("ativo", ativo).getResultList();   
	}  	

	public boolean existeCelulaOrigemInicial(Celula c) {
	    Query query = this.manager.createQuery("Select Count(c.idcelula) FROM celula c where c=c.celulaorigem"); 
	    long t=0;
	    t = (long) query.getSingleResult(); 
	    try{
		    if(c.getIdcelula().equals(c.getCelulaorigem().getIdcelula())){
			    if(t>0){
			    	if(t==1){
			    		Celula x = (Celula) this.manager.createQuery(" FROM celula c where c=c.celulaorigem").getSingleResult();
			    		if(x.getIdcelula().equals(c.getIdcelula()))
			    			return false;
			    		else
			    			return true;
			    	}else{
			    		return true;
			    	}
			    }else{
			    	return false;
			    }
		    }else{
		    	return false;
		    }
	    }catch(NullPointerException e){
	    	return false;
	    }
	}

	public boolean estaUsadaNaCelulaOrigem(Celula c) {
	    Query query = this.manager.createQuery(" FROM celula c where c=c.celulaorigem"); 
	    if(query.getResultList().size()>0)
	    	return true;
	    return false;
	}

}
