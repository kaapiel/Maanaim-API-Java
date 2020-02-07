package br.com.celula.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.UsuarioDao;
import br.com.celula.entidade.Usuario;

public class UsuarioLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Usuario> dao;
	private EntityManager manager;
	private String msg;
	
	public UsuarioLN(){		
		manager = EttMnger.manager();
	}
	
	public String adicionaUsuario(Usuario m){
		UsuarioDao daoM = new UsuarioDao();
		m.setDatacadastro(new Date(System.currentTimeMillis()));
		daoM.adiciona(m); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String atualizaUsuario(Usuario m){
		UsuarioDao daoM = new UsuarioDao();
		daoM.atualiza(m);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeUsuario(Usuario m){
		dao = new GenericDao<Usuario>(manager);
		dao.deleta(getUsuario(m.getIdusuario()));
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<Usuario> getListUsuarios(){
		dao = new GenericDao<Usuario>(manager);
		List<Usuario> Usuarios = new ArrayList<Usuario>();
		Usuarios = dao.lista("usuario","nome");		
		return Usuarios;
	}

	public Usuario getUsuario(Integer id){	
		dao = new GenericDao<Usuario>(manager);
		Usuario m = new Usuario();
		return (Usuario) dao.buscaPorId(m.getClass(), id);
	}

	public boolean validaUsuario(String Usuario, String senha) {
		UsuarioDao daoM = new UsuarioDao();
		boolean b=false;
		b=daoM.exiteUnico(Usuario,senha);
		return b;
	}
	
	public Usuario getUsuario(String Usuario, String senha){
		UsuarioDao daoM = new UsuarioDao();
		return daoM.retornaUsuario(Usuario, senha);		
	}


}
