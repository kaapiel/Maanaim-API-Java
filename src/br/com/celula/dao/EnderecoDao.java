package br.com.celula.dao;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.Endereco;

public class EnderecoDao implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public EnderecoDao(){
		this.setManager(EttMnger.manager());
	}

	public EnderecoDao(EntityManager m) {
		this.setManager(m);
	}

	public boolean localizaCepArquivo(String s) throws IOException{
		boolean achou = false;
		ServletContext cx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String nomeArq = cx.getRealPath("/resources/CEP.csv");
		String linha=""; 
	    try {   
	        @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(nomeArq));  
	        while ((linha = in.readLine()) != null) 
	            while (linha.lastIndexOf(s) >= 0) 
	                return true;  
	    } catch (IOException e) {  
	        throw new IOException("Erro na abertura do arquivo " + nomeArq + "  ",e);  
	    }  
	    return achou;  
	}
	public boolean localizaCepArquivoAndr(String s) throws IOException{
		boolean achou = false;
		String nomeArq = "D://Apache Software Foundation//Tomcat 7.0//webapps//celula//resources//CEP.csv";
		String linha=""; 
	    try {   
	        @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(nomeArq));  
	        while ((linha = in.readLine()) != null) 
	            while (linha.lastIndexOf(s) >= 0) 
	                return true;  
	    } catch (IOException e) {  
	        throw new IOException("Erro na abertura do arquivo " + nomeArq + "  ",e);  
	    }  
	    return achou;  
	}
	
	public Endereco carregaEnderecoArquivoAndr(String s) throws IOException{
		String nomeArq = "D://Apache Software Foundation//Tomcat 7.0//webapps//celula//resources//CEP.csv";
		String linha=""; 
	    try {   
	        @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(nomeArq));  
	        while ((linha = in.readLine()) != null)
	            while (linha.lastIndexOf(s) >= 0) 
	                return carregaEndereco(linha);
	    } catch (IOException e) {  
	        throw new IOException("Erro na abertura do arquivo " + nomeArq + "  ",e);  
	    }  
	    return new Endereco();  
	}

	public Endereco carregaEnderecoArquivo(String s) throws IOException{
		ServletContext cx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String nomeArq = cx.getRealPath("/resources/CEP.csv");
		String linha=""; 
	    try {   
	        @SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(nomeArq));  
	        while ((linha = in.readLine()) != null)
	            while (linha.lastIndexOf(s) >= 0) 
	                return carregaEndereco(linha);
	    } catch (IOException e) {  
	        throw new IOException("Erro na abertura do arquivo " + nomeArq + "  ",e);  
	    }  
	    return new Endereco();  
	}
	
	private Endereco carregaEndereco(String linha){
		Endereco e = new Endereco();
		String s[] = linha.split(";");
		e.setCep(s[0]);
		e.setUf(s[1]);
		e.setLogradouro(s[2]);
		e.setCidade(s[3]);
		e.setBairro(s[4]);
		return e;		
	}

	public boolean existeCepNoBanco(String cep){
		Endereco e = null;
		try{
			e = (Endereco) this.manager.createQuery("From endereco e WHERE e.cep = :cep").setParameter("cep", cep).getSingleResult();
		}catch(NoResultException ex){
			 e=null;
		}
		return (e != null);		
	}

	public boolean existeCepNoBanco(String cep, EntityManager em){
		this.manager = em;
		Endereco e = null;
		try{
			e = (Endereco) manager.createQuery("From endereco e WHERE e.cep = :cep").setParameter("cep", cep).getSingleResult();
		}catch(NoResultException ex){
			 e=null;
		}
		return (e != null);		
	}
	
	public Endereco getEndereco(String cep){
		return (Endereco) this.manager.createQuery("From endereco e WHERE e.cep = :cep")
				.setParameter("cep", cep)
				.getSingleResult();
	}
	
	
	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

	
}
