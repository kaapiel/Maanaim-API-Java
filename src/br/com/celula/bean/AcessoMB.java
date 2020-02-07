package br.com.celula.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.celula.entidade.Usuario;
import br.com.celula.negocio.UsuarioLN;
import br.com.celula.util.Util;

@ManagedBean(name="acessoMB")
@ViewScoped
public class AcessoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private String usuario="";
	private String senha="";
	private Usuario user;
	private UsuarioLN ln;
	private String msg="";
	private Boolean userLogado;
	private static AcessoMB instancia;
	
    @PostConstruct  
    public void inicializa() {  
        user = new Usuario();  
        userLogado = Boolean.FALSE;  
        instancia = this;  
    } 
	
    
    public static AcessoMB getInstancia() throws Exception{  
        if(instancia == null)  
            throw new Exception("Não há usuario logado no sistema!");          
        return instancia;  
    }  
    
	public void entrar(){
		ln=new UsuarioLN();
		if(validaCampos()){        
			try {  
				this.user=ln.getUsuario(usuario, senha); 
	            if(this.user.getIdusuario() == null){  
	            	usuario = senha = "";
	            	msg="Acesso Negado";
	    			mensagens();  
	            }else{  
	                userLogado = Boolean.TRUE;  
	                HttpSession session = Util.getSession();
	                session.setAttribute("usuarioLogado", this.user);
	            	usuario="";
	            	senha="";
	                FacesContext.getCurrentInstance().getExternalContext().redirect("/celula/sistema/index.jsf"); 
	            }  
	              
	        } catch (Exception e) {  
	        	msg="Usuário ou senha inválidos.";
				mensagens();
		    	usuario="";
		    	senha="";
	        } 		
		}else{
			msg="Usuário ou senha inválidos.";
			mensagens();
		}
	}
	
    public void logout(){  
        this.user = null;  
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();  
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/celula/");
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
    }  
    
	private boolean validaCampos() {
		boolean resultado = true;
		if(usuario=="")
			resultado=false;
		if(senha=="")
			resultado=false;
		return resultado;		
	}


	private void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}


	public String getUsuario() {
		return usuario;
	}


	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public Usuario getUser() {
		return user;
	}


	public void setUser(Usuario user) {
		this.user = user;
	}


	public UsuarioLN getLn() {
		return ln;
	}


	public void setLn(UsuarioLN ln) {
		this.ln = ln;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Boolean getUserLogado() {
		return userLogado;
	}


	public void setUserLogado(Boolean userLogado) {
		this.userLogado = userLogado;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public static void setInstancia(AcessoMB instancia) {
		AcessoMB.instancia = instancia;
	}	
	

	
}
