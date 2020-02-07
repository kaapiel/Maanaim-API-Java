package br.com.celula.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.entidade.EstadosReuniao;


public class EstadosReuniaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<EstadosReuniao> dao;
	private EntityManager manager;
	private String msg;
	
	public EstadosReuniaoLN(){		
		manager = EttMnger.manager();
	}
	
	public String adicionaEstadosReuniao(EstadosReuniao sub){
		dao = new GenericDao<EstadosReuniao>(manager);
		dao.adiciona(sub); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String atualizaEstadosReuniao(EstadosReuniao sub){
		dao = new GenericDao<EstadosReuniao>(manager);
		dao.atualiza(sub);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeEstadosReuniao(EstadosReuniao x){
		dao = new GenericDao<EstadosReuniao>(manager);
		dao.deleta(x);
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<EstadosReuniao> getList(){
		dao = new GenericDao<EstadosReuniao>(manager);	
		return dao.lista("estadosreuniao","id");
	}

	@SuppressWarnings("null")
	public EstadosReuniao getEstadosReuniao(Integer id){	
		dao = new GenericDao<EstadosReuniao>(manager);
		EstadosReuniao x = null;
		return (EstadosReuniao) dao.buscaPorId(x.getClass(), id);
	}

	

}
