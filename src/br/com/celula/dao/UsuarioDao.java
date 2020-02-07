package br.com.celula.dao;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Usuario;
import br.com.celula.util.Criptografa;

/**
*
* @author MAURICIO CRUZ
*/
public class UsuarioDao {
	
	private EntityManager manager;
	
	public UsuarioDao(){
		this.manager = EttMnger.manager();
	}

	public UsuarioDao(EntityManager m) {
		manager = m;
	}

	public Boolean valida(String usuario, String senha) { 
		  Usuario m = null;
	      Query query = this.manager.createQuery("From usuario m WHERE m.usuario = :usuario AND m.senha = :senha");   
	     
	      query.setParameter("usuario", usuario);   
	      try {
	    	  query.setParameter("senha", Criptografa.md5(senha));
	      } catch (NoSuchAlgorithmException e) {			
			System.out.println(e.getMessage());
	      }    
	  
	      try{
	    	  m = (Usuario) query.getSingleResult(); 
	      }catch(NoResultException e){
	    	  m = null;
	      }      
		   return (m != null);   
	}

	public Usuario retornaUsuario(String usuario, String senha) { 
		Usuario m = null;
		   try {   
		      Query query = this.manager.createQuery(" FROM usuario m WHERE m.usuario = :usuario AND m.senha = :senha");   
		     
		      query.setParameter("usuario", usuario);   
		      try {	    	  
		    	  query.setParameter("senha", Criptografa.md5(senha));
		      } catch (NoSuchAlgorithmException e) {			
				System.out.println(e.getMessage());
		      }  
		      try{
		    	  m = (Usuario) query.getSingleResult(); 
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
		Usuario m = null;
	      Query query = this.manager.createQuery(" FROM membro m WHERE m.usuario = :usuario AND m.senha = :senha");   
	      query.setParameter("usuario", usuario);   
	      try {
				query.setParameter("senha", Criptografa.md5(senha));
	      } catch (NoSuchAlgorithmException e) {			
			System.out.println(e.getMessage());
	      }  
	      try{
	    	  m = (Usuario) query.getSingleResult(); 
	      }catch(NoResultException e){
	    	  m = null;
	      }	            
		   return (m != null);   
	}  

	public void atualiza(Usuario u){
		try {
			u.setSenha(Criptografa.md5(u.getSenha()));
			manager.merge(u);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void adiciona(Usuario u){
		try {
			u.setSenha(Criptografa.md5(u.getSenha()));
			manager.persist(u);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	public List<Usuario> usuarios(){
		return manager.createQuery(" FROM usuario u order by u.nome").getResultList();		
	}
}
