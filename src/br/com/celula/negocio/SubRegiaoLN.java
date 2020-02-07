package br.com.celula.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.SubRegiaoDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.SubRegiao;


public class SubRegiaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<SubRegiao> dao;
	private EntityManager manager;
	private String msg;
	
	public SubRegiaoLN(){		
		manager = EttMnger.manager();
	}
	
	public SubRegiaoLN(EntityManager em){ // android
		this.manager = em;
	}
	
	public String adicionaSubRegiao(SubRegiao sub){
		dao = new GenericDao<SubRegiao>(manager);
		dao.adiciona(sub); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String atualizaSubRegiao(SubRegiao sub){
		dao = new GenericDao<SubRegiao>(manager);
		dao.atualiza(sub);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeSubRegiao(SubRegiao x){
		dao = new GenericDao<SubRegiao>(manager);
		if(x.getCelulas()!=null){
			for(Celula c:x.getCelulas()){
				if(c.getMembros().size()!=0){
					msg="Não excluido, pois existem membros vinculadas a esta subregião.";
					return msg;	
				}
			}
		}			
		if(x.getCelulas().size()!=0){
			msg="Não excluido, pois existem células vinculadas a esta subregião.";
			return msg;					
		}
		
		dao.deleta(getSubRegiao(x.getIdsubregiao()));
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<SubRegiao> getListSubRegioes(){
		dao = new GenericDao<SubRegiao>(manager);	
		return dao.lista("subregiao","id");
	}

	public SubRegiao getSubRegiao(Integer id){	
		dao = new GenericDao<SubRegiao>(manager);
		SubRegiao x = new SubRegiao();
		return (SubRegiao) dao.buscaPorId(x.getClass(), id);
	}
	
	public SubRegiao getSubRegiao(Integer id, EntityManager em){// android
		this.manager = em;
		dao = new GenericDao<SubRegiao>(manager);
		SubRegiao x = new SubRegiao();
		return (SubRegiao) dao.buscaPorId(x.getClass(), id);
	}

	public boolean existeSubRegiaoCadastrada(){
		SubRegiaoDao rdao = new SubRegiaoDao();
		return rdao.existeSubRegiaoCadastrada();
	}
	
	
	public SubRegiao getSubRegiaoPorNome(String nome, EntityManager em){	
		
		SubRegiao x = new SubRegiao();
		Query query = em.createQuery(" From "+x.getClass().getName() + " Where nome = :nome");
		query.setParameter("nome", nome);
		query.setMaxResults(1);
		
		SubRegiao singleResult = (SubRegiao) query.getSingleResult();
		
		System.err.println(singleResult.getIdsubregiao());
		
		return (SubRegiao) query.getSingleResult();		
	}

}
