package br.com.celula.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.EstadosReuniao;
import br.com.celula.entidade.Reuniao;

/**
*
* @author MAURICIO CRUZ
*/
public class ReuniaoDao {
	
	private EntityManager manager;
	
	public ReuniaoDao(){
		this.manager = EttMnger.manager();
	}
	
	public ReuniaoDao(EntityManager m){
		this.manager = m;
	}


	@SuppressWarnings("unchecked")
	public List<Reuniao> reunioes(EstadosReuniao status,Celula celula) {
		  Query query = this.manager.createQuery(" FROM reuniao r where r.estado= :status and r.celula= :celula order by r.datareuniao,r.horareuniao"); 
	      return query.setParameter("status", status)
	    		  .setParameter("celula", celula).getResultList();   
	
	}
	
	public Reuniao reuniao(Reuniao reuniao) {
		  Query query = this.manager.createQuery(" FROM reuniao r where r.idreuniao= :reuniao"); 
	      return (Reuniao) query.setParameter("reuniao", reuniao.getIdreuniao()).getSingleResult();   
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Reuniao> reunioes() {		  
	      Query query = this.manager.createQuery(" FROM reuniao r"); 
	      return query.getResultList();   
	}


	public boolean existeReuniaoComMenosDeUmaSemana(Celula celula, Date date) {
	    System.out.println(date);  
		Query query = this.manager.createQuery(" FROM reuniao r where r.datareuniao> :date and r.celula= :celula"); 
		if(query.setParameter("date", date).setParameter("celula", celula).getResultList().size()>0)
	    	 return true;
		return false;
	}  

}
