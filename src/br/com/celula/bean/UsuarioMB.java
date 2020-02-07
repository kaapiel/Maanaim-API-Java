package br.com.celula.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Usuario;
import br.com.celula.negocio.UsuarioLN;

@ManagedBean(name="usuarioMB")
@ViewScoped
public class UsuarioMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List<Usuario> usuarios = new ArrayList<>();
	private UsuarioLN usuarioLN;
	private int controlaCadastro = 0;
	private String msg;
	private String nomeMembro="";
	private String senha1="";
	private String senha2="";
	private Membro membro = new Membro();
	private List<String> erros = new ArrayList<String>();

	public UsuarioMB(){
		listar();
		controlaCadastro = 0;
		this.usuario = new Usuario();	
	}
	
	public void listar(){
		usuarioLN = new UsuarioLN();
		this.usuarios = usuarioLN.getListUsuarios();				
	}
	
	public void novo(){
		usuarioLN = new UsuarioLN();
		this.usuario = new Usuario();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		if(this.usuario.getIdusuario()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			usuarioLN = new UsuarioLN();
			msg = usuarioLN.removeUsuario(usuario); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos()){
			usuarioLN = new UsuarioLN();
			if(controlaCadastro==1)		
				msg = usuarioLN.adicionaUsuario(this.usuario);

			if(controlaCadastro==2)
				msg = usuarioLN.atualizaUsuario(this.usuario);
			
			mensagens();
			limpaCadastro();
			listar();
		}else{
			//for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * são obrigatórios.")); 			
			//erros.clear(); 
		}
	}

	private boolean validaCampos() {
		boolean b = true;
		if(usuario.getNome().length()==0)
			b= false;
		if(usuario.getUsuario().length()==0)
			b= false;		

		return b;
			
	}
		
	public void gravaSenha(){
		if(!senha1.equals(senha2)){
			msg="As senhas não conferem.";
			mensagens();
		}else{
			if(senha1=="" || senha2==""){
				msg="Informe a nova senha.";
				mensagens();
			}else{
				usuario.setSenha(senha1);
				grava();
			}
		}
	}
	
	public void limpaCadastro(){
		this.usuario = new Usuario();
		controlaCadastro=0;
		membro = new Membro();
		nomeMembro="";
		senha1="";
		senha2="";
	}
	
	public void usuarioSelected(SelectEvent event){
		this.usuario = (Usuario) event.getObject();
		controlaCadastro=0;
	}

	public void membroSelected(SelectEvent event){
		this.membro = (Membro) event.getObject();
		usuario.setMembro(membro);
		nomeMembro = membro.getNome();
	}

	public void usuarioSelecionado(){
		if(usuario.getMembro()!=null)
			nomeMembro = usuario.getMembro().getNome();
	}
	
	public void redefineSenha(){
		controlaCadastro=2;
		senha1="";
		senha2="";
	}
	
    public void usuarioSelecionadoPeloDialog(Usuario r) {  
        RequestContext.getCurrentInstance().closeDialog(r);  
    } 
    

	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioLN getUsuarioLN() {
		return usuarioLN;
	}

	public void setUsuarioLN(UsuarioLN usuarioLN) {
		this.usuarioLN = usuarioLN;
	}

	public int getControlaCadastro() {
		return controlaCadastro;
	}

	public void setControlaCadastro(int controlaCadastro) {
		this.controlaCadastro = controlaCadastro;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public String getNomeMembro() {
		return nomeMembro;
	}

	public void setNomeMembro(String nomeMembro) {
		this.nomeMembro = nomeMembro;
	}

	public String getSenha1() {
		return senha1;
	}

	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}

	public String getSenha2() {
		return senha2;
	}

	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}

}
