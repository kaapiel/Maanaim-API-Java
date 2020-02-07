package br.com.celula.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.RegiaoDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.SubRegiao;


public class RegiaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Regiao> dao;
	private EntityManager manager;
	private String msg;
	
	public RegiaoLN(){		
		manager = EttMnger.manager();
	}
	
	public String adicionaRegiao(Regiao x){
		dao = new GenericDao<Regiao>(manager);
		dao.adiciona(x); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String atualizaRegiao(Regiao x){
		dao = new GenericDao<Regiao>(manager);
		dao.atualiza(x);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeRegiao(Regiao x){
		dao = new GenericDao<Regiao>(manager);
		for(SubRegiao r:x.getSubregioes()){
			for(Celula c:r.getCelulas()){
				if(c.getMembros().size()!=0){
					msg="Não excluido, pois existem membros vinculadas a esta região.";
					return msg;	
				}
			}
		}				
		for(SubRegiao r:x.getSubregioes()){
			if(r.getCelulas().size()!=0){
				msg="Não excluido, pois existem células vinculadas a esta região.";
				return msg;					
			}
		}
		if(x.getSubregioes().size()!=0){
			msg="Não excluido, pois existem sub-regiões vinculadas a esta região.";
			return msg;
		}
		
		dao.deleta(getRegiao(x.getIdregiao()));
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<Regiao> getListRegioes(){
		dao = new GenericDao<Regiao>(manager);	
		return dao.lista("regiao","id");
	}

	public Regiao getRegiao(Integer id){	
		dao = new GenericDao<Regiao>(manager);
		Regiao x = new Regiao();
		return (Regiao) dao.buscaPorId(x.getClass(), id);
	}

	public boolean existeRegiaoCadastrada(){
		RegiaoDao rdao = new RegiaoDao();
		return rdao.existeRegiaoCadastrada();
	}

}
