package br.com.celula.dao;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.Reuniao;
import br.com.celula.entidade.ReuniaoMembro;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.util.Criptografa;

/**
*
* @author MAURICIO CRUZ
*/
public class MembroDao {
	
	private EntityManager manager;
	
	public MembroDao(){
		this.manager = EttMnger.manager();
	}

	public MembroDao(EntityManager m){
		this.manager = m;
	}
	
	public Boolean validaMembro(String usuario, String senha) { 
		  Membro m = null;
	      Query query = this.manager.createQuery("From membro m WHERE m.usuario = :usuario AND m.senha = :senha");   
	     
	      query.setParameter("usuario", usuario);   
	      try {
	    	  query.setParameter("senha", Criptografa.md5(senha));
	      } catch (NoSuchAlgorithmException e) {			
			System.out.println(e.getMessage());
	      }    
	  
	      try{
	    	  m = (Membro) query.getSingleResult(); 
	      }catch(NoResultException e){
	    	  m = null;
	      }      
		   return (m != null);   
	}

	public Membro retornaUsuario(String usuario, String senha) { 
		Membro m = null;
		   try {   
		      Query query = this.manager.createQuery(" FROM membro m WHERE m.usuario = :usuario AND m.senha = :senha");   
		     
		      query.setParameter("usuario", usuario);   
		      try {	    	  
		    	  query.setParameter("senha", Criptografa.md5(senha));
		      } catch (NoSuchAlgorithmException e) {			
				System.out.println(e.getMessage());
		      }  
		      try{
		    	  m = (Membro) query.getSingleResult(); 
		      }catch(NoResultException e){
		    	  m = null;
		      }     
		   } catch (NoResultException e) {  
			   m = null;   
		   } catch (RuntimeException e) {  
		      e.printStackTrace();   
		   }   		     
		   return m;   
		}

	public boolean exiteUnico(String usuario, String senha) {
		  Membro m = null;
	      Query query = this.manager.createQuery(" FROM membro m WHERE m.usuario = :usuario AND m.senha = :senha");   
	      query.setParameter("usuario", usuario);   
	      try {
				query.setParameter("senha", Criptografa.md5(senha));
	      } catch (NoSuchAlgorithmException e) {			
			System.out.println(e.getMessage());
	      }  
	      try{
	    	  m = (Membro) query.getSingleResult(); 
	      }catch(NoResultException e){
	    	  m = null;
	      }	            
		   return (m != null);   
	}  

	@SuppressWarnings("unchecked")
	public List<Membro> membrosPresentesNaReuniao(Reuniao reuniao) {
		boolean freq = true;
		List<Membro> membros = new ArrayList<Membro>();
		List<ReuniaoMembro> reuniaomembros = new ArrayList<ReuniaoMembro>();
		String sql = " From reuniao_membro rm Where rm.reuniao= :reuniao and rm.estevepresente= :freq";
		reuniaomembros = this.manager.createQuery(sql).setParameter("freq", freq).setParameter("reuniao", reuniao).getResultList();
		for(ReuniaoMembro r:reuniaomembros)
			membros.add(r.getMembro());
		return membros;
	}

	@SuppressWarnings("unchecked")
	public List<Membro> aniversariantes(Integer mes) {	
		String sql = " From membro m where MONTH(m.nascimento)= :mes order by DAY(m.nascimento)";
		return manager.createQuery(sql).setParameter("mes", mes).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Membro> membrosAtivos() {		
		boolean ativo = true;
		String sql=" FROM membro m"
				+ " where m.ativo= :ativo order by m.celula.subregiao.regiao,m.celula.subregiao,m.celula,m.nome";
	      Query query = this.manager.createQuery(sql); 
	      return query.setParameter("ativo", ativo).getResultList();   
	}	
	
	public Membro membroPorId(Integer idmembro) {		
		
		String sql=" FROM membro m where m.idmembro= :idmembro";
	      Query query = this.manager.createQuery(sql); 
	      return (Membro) query.setParameter("idmembro", idmembro).getSingleResult();   
	}
	
	@SuppressWarnings("unchecked")
	public List<Membro> membrosAtivos(Celula celula) {		
		boolean ativo = true;
		String sql=" Select m FROM membro m"
				+ " join m.celula c"
				+ " join c.subregiao sb"
				+ " join sb.regiao r"
				+ " where m.ativo= :ativo and c= :celula order by m.nome";
	      Query query = this.manager.createQuery(sql); 
	      return query.setParameter("ativo", ativo)
	    		  .setParameter("celula", celula).getResultList();   
	}	
	
	@SuppressWarnings("unchecked")
	public List<Membro> membrosAtivos(Integer idCelula) {		
		boolean ativo = true;
		String sql=" Select m FROM membro m"
				+ " join m.celula c"
				+ " join c.subregiao sb"
				+ " join sb.regiao r"
				+ " where m.ativo= :ativo and c.idcelula= :idcelula order by m.nome";
	      Query query = this.manager.createQuery(sql); 
	      return query.setParameter("ativo", ativo)
	    		  .setParameter("idcelula", idCelula).getResultList();   
	}

	@SuppressWarnings("unchecked")
	public List<Membro> membrosAtivos(SubRegiao r) {		
		boolean ativo = true;
		String sql=" Select m FROM membro m"
				+ " join m.celula c"
				+ " join c.subregiao sb"
				+ " join sb.regiao r"
				+ " where m.ativo= :ativo and sb= :subregiao order by m.nome";
	      Query query = this.manager.createQuery(sql); 
	      return query.setParameter("ativo", ativo)
	    		  .setParameter("subregiao", r).getResultList();   
	}

	@SuppressWarnings("unchecked")
	public List<Membro> membrosAtivos(Regiao r) {		
		boolean ativo = true;
		String sql=" Select m FROM membro m"
				+ " join m.celula c"
				+ " join c.subregiao sb"
				+ " join sb.regiao r"
				+ " where m.ativo= :ativo and r= :regiao order by m.nome";
	      Query query = this.manager.createQuery(sql); 
	      return query.setParameter("ativo", ativo)
	    		  .setParameter("regiao", r).getResultList();   
	}

	public int totalMembrosAtivos(){		
		boolean ativo=true;
		return (int)(long) manager.createQuery("Select count(m.idmembro) FROM membro m WHERE m.ativo= :ativo")
				.setParameter("ativo", ativo)
				.getSingleResult();
	}
}
