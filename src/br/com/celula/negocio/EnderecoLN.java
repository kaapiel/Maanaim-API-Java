package br.com.celula.negocio;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.EntityManager;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.EnderecoDao;
import br.com.celula.dao.GenericDao;
import br.com.celula.entidade.Endereco;

public class EnderecoLN implements Serializable{

	private static final long serialVersionUID = 1L;
	private GenericDao<Endereco> dao;
	private EnderecoDao edao;
	private String msg;
	private EntityManager manager;
	
	public Endereco getEndereco(String s) throws IOException {
		edao = new EnderecoDao();
		if(exiteEnderecoNoBanco(s))
			return edao.getEndereco(s);
		else
			if(edao.localizaCepArquivo(s))
				return edao.carregaEnderecoArquivo(s);
		return new Endereco();
	}
	
	public boolean exiteEnderecoNoBanco(String cep){
		edao = new EnderecoDao();	
		return edao.existeCepNoBanco(cep);
	}
	
	public boolean exiteEnderecoNoBanco(String cep, EntityManager em){
		edao = new EnderecoDao(em);	
		return edao.existeCepNoBanco(cep, em);
	}
	
	public String adiciona(Endereco e, EntityManager em){
		dao = new GenericDao<Endereco>(em);
		dao.adiciona(e); 
		msg="Endereco Adicionado com sucesso.";
		return msg;
	}
	
	public String adiciona(Endereco e){
		dao = new GenericDao<Endereco>(EttMnger.manager());
		dao.adiciona(e); 
		msg="Adicionado com sucesso.";
		return msg;
	}
}
