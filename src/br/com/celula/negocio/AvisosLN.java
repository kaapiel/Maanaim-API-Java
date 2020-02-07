package br.com.celula.negocio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.AvisosDao;
import br.com.celula.dao.GenericDao;
import br.com.celula.entidade.Avisos;
import br.com.celula.util.SaveFile;


public class AvisosLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Avisos> dao;
	private EntityManager manager;
	private String msg;
	
	public AvisosLN(){		
		manager = EttMnger.manager();
	}
	
	public AvisosLN(EntityManager m){		
		manager = m;
	}
	
	public List<Avisos> getListAvisos(){
		dao = new GenericDao<Avisos>(manager);
		List<Avisos> avisos = new ArrayList<Avisos>();
		avisos = dao.lista("avisos","dataEvento DESC");		
		return avisos;
	}
	
	public String adicionaAviso(Avisos x){
		dao = new GenericDao<Avisos>(manager);
		dao.adiciona(x); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String recuperaFoto(Avisos a){
		if(a.getImagemEvento()!=null){
			ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			File folder = new File(sContext.getRealPath("/resources/tmp/"));
			String arquivo = sContext.getRealPath("/resources/tmp/") + File.separator + a.getIdaviso() +".jpg";
			
	        if (!folder.exists())
	            folder.mkdirs();
	        SaveFile.gravaArquivoWeb(a.getImagemEvento(), arquivo);
	        
	        System.out.println("return: "+a.getImagemEvento() +".jpg");
	        return a.getIdaviso() +".jpg";
		}
		return "foto.jpg";
	}
	
	public String exibeFoto(FileUploadEvent event) {
		SaveFile sf = new SaveFile();
		try {
		 return	sf.criaArquivoTemp(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String atualizaAviso(Avisos x){
		dao = new GenericDao<Avisos>(manager);
		dao.atualiza(x);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeAviso(Avisos x){
		dao = new GenericDao<Avisos>(manager);
		
		dao.deleta(getAvisos(x.getIdaviso()));
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<Avisos> getListAll(){
		dao = new GenericDao<Avisos>(manager);	
		return dao.lista("avisos","idaviso");
	}
	
	public List<Avisos> getList(){
		AvisosDao cdao = new AvisosDao();	
		return cdao.getAvisos();
	}
	
	public boolean existeAvisosCadastrados(){
		AvisosDao cdao = new AvisosDao();
		return cdao.existeAvisoCadastrado();
	}
	
	public Avisos getAvisos(Integer id){	
		dao = new GenericDao<Avisos>(manager);
		Avisos x = new Avisos();
		return (Avisos) dao.buscaPorId(x.getClass(), id);
	}
	
}
