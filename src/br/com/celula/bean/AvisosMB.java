package br.com.celula.bean;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.celula.entidade.Avisos;
import br.com.celula.negocio.AvisosLN;
import br.com.celula.util.ValidarData;

@ManagedBean(name="avisosMB")
@ViewScoped
public class AvisosMB implements Serializable{

	private static final long serialVersionUID = 6594673382888334470L;
	private Avisos aviso;
	private List<Avisos> avisos = new ArrayList<>();
	private AvisosLN avisosLN;
	private int controlaCadastro = 0;
	private String msg;
	private String titulo;
	private String dataEvento;
	private List<String> erros = new ArrayList<String>();
	private String foto="foto.jpg";
	
	
	public AvisosMB(){
		avisosLN = new AvisosLN();
		controlaCadastro = 0;
		this.aviso = new Avisos();
	}
	
	public void listar(){		
		avisosLN = new AvisosLN();
		this.avisos = avisosLN.getListAvisos();		
	}
	
	public void novo(){
		this.aviso = new Avisos();
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		if(this.aviso.getIdaviso()==null){
			msg = "Nenhum registro selecionado para exclus�o.";
			mensagens();			
		}else{
			avisosLN = new AvisosLN();
			msg = avisosLN.removeAviso(aviso); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos(this.aviso)){
			avisosLN = new AvisosLN();
			carregarCamposParaGravar();
			if(controlaCadastro==1)		
				msg = avisosLN.adicionaAviso(this.aviso);
			if(controlaCadastro==2)
				msg = avisosLN.atualizaAviso(this.aviso);
			
			mensagens();
			limpaCadastro();
			listar();
		}else{
			//for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * s�o obrigatorios.")); 
		//	erros.clear(); 
		}
	}

	private void carregarCamposParaGravar() {
		
		if(dataEvento!="")
			try {
				aviso.setDataEvento(ValidarData.stringToDate(dataEvento));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		else
			aviso.setDataEvento(null);
		Date d = new Date();
			aviso.setDataPublicacao(d);
		
	}
	
	private void carragaCamposNaPagina(){
		if(aviso.getDataEvento()!=null)
			dataEvento=ValidarData.dataParaString(aviso.getDataEvento());
		else
			dataEvento="";
		
		if(aviso.getTitulo()!=null)
			this.titulo=(aviso.getTitulo());
		else
			this.titulo="";
		
	}

 	private boolean validaCampos(Avisos a) {
		boolean b = true;
		if(a.getTitulo().length()==0){
			msg = "Campo T�tulo e obrigatorio!";
			erros.add(msg);
			b= false;
		}
		
		try{	
			if(dataEvento!=""){
				if(!ValidarData.dataOk(dataEvento)){
					msg = "A data de cadastro e invalida!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				
				if(ValidarData.menorQueTalAno(dataEvento, 2000)){
					msg = "A data de cadastro nao pode ser menor que 2000!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
			}else{
				//msg = "A data de cadastro e obrigatoria!";
				//mensagens();
				//erros.add(msg);			
				b = false;
			}
			
		}catch(Exception e){
			b = false;
			System.out.println(e.getMessage());
		}
	
		return b;
	}
	
    public void upload(FileUploadEvent e) {
    	foto = avisosLN.exibeFoto(e); 
    	try {
			aviso.setImagemEvento(IOUtils.toByteArray(e.getFile().getInputstream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
	
   public void limpaCadastro(){
		this.aviso = new Avisos();
		controlaCadastro=0;
		dataEvento="";
		foto="foto.jpg";
	}
	
	public void avisoSelected(SelectEvent event){
		this.aviso = (Avisos) event.getObject();
		carragaCamposNaPagina();	
		controlaCadastro=0;
	}
	
    public void avisoSelecionadoPeloDialog() {
        RequestContext.getCurrentInstance().closeDialog(aviso);
    }    
    
    public void avisoSelecionado(SelectEvent event) {
        this.aviso = (Avisos) event.getObject();
        avisosLN = new AvisosLN();
        foto = avisosLN.recuperaFoto(aviso);
        carragaCamposNaPagina();        
    }	    
 
   public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}	
//===============================================================================================================//

	public Avisos getAviso() {
		return aviso;
	}

	public void setAvisos(Avisos aviso) {
		this.aviso = aviso;
	}

	public List<Avisos> getAvisos() {
		return avisos;
	}

	public void setAvisos(List<Avisos> avisos) {
		this.avisos = avisos;
	}

	public AvisosLN getAvisosLN() {
		return avisosLN;
	}

	public void setAvisosLN(AvisosLN avisosLN) {
		this.avisosLN = avisosLN;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	
	
	public void setAviso(Avisos aviso) {
		this.aviso = aviso;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
