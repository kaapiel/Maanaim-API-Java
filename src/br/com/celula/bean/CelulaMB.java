package br.com.celula.bean;


import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.CelulaLN;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.SubRegiaoLN;
import br.com.celula.util.ValidarData;


@ManagedBean(name="celulaMB")
@ViewScoped
public class CelulaMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Celula celula = new Celula();
	private Celula celulaorigem = new Celula();
	private List<Celula> celulas = new ArrayList<>();
	private CelulaLN celulaLN;
	private Membro membro;
	private List<Membro> membros = new ArrayList<Membro>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> erros = new ArrayList<String>();
	private boolean existemsubregioes=false;
	private String datacadastro;
	
	@PostConstruct
	public void vai(){
		listar();
		controlaCadastro = 0;
		this.celula = new Celula();
		this.celula.setCelulaorigem(new Celula());		
	}
	
	public void listar(){
		celulaLN = new CelulaLN();
		this.celulas = celulaLN.getList();	
	}

	public void listarMembros(){
		MembroLN membroLN = new MembroLN();
		this.membros = membroLN.getListMembrosAtivos(celula.getSubregiao().getRegiao());		
	}

	public void novo(){
		celulaLN = new CelulaLN();
		this.celula = new Celula();
		celulaorigem = new Celula();
		this.celula.setCelulaorigem(celula);
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
		celulaorigem = celula.getCelulaorigem();
		if(celula.getDatacriacao()!=null)
			datacadastro=ValidarData.dataParaString(celula.getDatacriacao());
		else
			datacadastro="";
		listarMembros();
	}
	
	public void exclui(){
		if(this.celula.getIdcelula()==null){
			msg = "Nenhum registro selecionado para exclus�o.";
			mensagens();			
		}else{
			celulaLN = new CelulaLN();
			msg = celulaLN.removeCelula(celula); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos()){
			if(datacadastro!="")
				try {
					celula.setDatacriacao(ValidarData.stringToDate(datacadastro));
				} catch (ParseException e) {
					celula.setDatacriacao(null);
				}
			else
				celula.setDatacriacao(new Date(System.currentTimeMillis()));
			celulaLN = new CelulaLN();
			if(verificarOrigemRepetida()){
				msg="A celula origem nao pode ser igual a celula selecionada!";
			}else{
				if(controlaCadastro==1)		
					msg = celulaLN.adicionaCelula(this.celula);
	
				if(controlaCadastro==2)
					msg = celulaLN.atualizaCelula(this.celula);
				limpaCadastro();
				listar();
			}
			mensagens();
		}else{
			//for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * s�o obrigatorios.")); 
			//erros.clear(); 
		}
	}

	private boolean verificarOrigemRepetida() {
		return (celulaLN.existeCelulaOrigemInicial(celula));
	}

	private boolean validaCampos() {
		boolean b = true;
		if(celula.getDescricao().equals("")){
			b= false;
			erros.add("Campo descricao e obrigatorio.");
		}
		if(celula.getNome().equals("")){
			b= false;		
			erros.add("Campo nome e obrigatorio.");
		}
		if(celula.getSubregiao()==null){
			b= false;		
			erros.add("Campo nome e obrigatorio.");
		}
		try{	
			if(datacadastro!=""){
				if(!ValidarData.dataOk(datacadastro)){
					msg = "A data de cadastro e invalida!";
					erros.add(msg);			
					b = false;
				}	
				if(ValidarData.maiorQueHoje(datacadastro)){
					msg = "A data de cadastro nao pode ser maior que hoje!";
					erros.add(msg);			
					b = false;
				}	
				if(ValidarData.menorQueTalAno(datacadastro, 2000)){
					msg = "A data de cadastro nao pode ser menor que 2000!";
					erros.add(msg);			
					b = false;
				}	
			}else{
				msg = "A data de cadastro e obrigatoria!";
				erros.add(msg);			
				b = false;
			}
		}catch(Exception e){
			b = false;
			System.out.println(e.getMessage());
		}
		return b;
	}
		
	public void limpaCadastro(){
		this.celula = new Celula();
		this.celulaorigem = new Celula();
		this.celula.setCelulaorigem(new Celula());
		datacadastro="";
		controlaCadastro=0;
		membro = new Membro();
	}
	
	public void celulaSelected(SelectEvent event){
		this.celula = (Celula) event.getObject();
		if(celula.getDatacriacao()!=null)
			datacadastro=ValidarData.dataParaString(celula.getDatacriacao());
		else
			datacadastro="";
		controlaCadastro=0;
	}

    public void celulaOrigemSelecionada(SelectEvent event) {
        this.celulaorigem = (Celula) event.getObject();
        this.celula.setCelulaorigem(celulaorigem);
    }	

    public void subregiaoSelected(SelectEvent event) {
        this.celula.setSubregiao((SubRegiao) event.getObject());
    }	
    
    public void celulaSelecionadaPeloDialog(Celula c) {  
        RequestContext.getCurrentInstance().closeDialog(c);  
    } 
     
    public void existemSubRegiaoCadastrada(){
    	SubRegiaoLN rln = new SubRegiaoLN();
    	if(!rln.existeSubRegiaoCadastrada()){
	    	msg = "Cadastro nao pode ser aberto pois nao ha subregioes cadastradas.";
	    	mensagens();
	    	existemsubregioes = false;
    	}else{
    		existemsubregioes = true;
    	}
    }	
	
    public void adicionaResponsavel(){
    	if(celula.getLideres().size()==2){
    		msg="� permitido apenas 2 lideres. Remova algum para depois adicionar.";
    		mensagens();
    	}else{
    	    			celula.getLideres().add(membro);
            			membro = new Membro();
        }  		
    }

    public void removeResponsavel(){
    	celula.getLideres().remove(membro);
		membro = new Membro();  		
    }

	public List<Membro> autocompletar(String x){
		String s =x.toLowerCase();
		List<Membro> filtrados = new ArrayList<Membro>();
        for (int i = 0; i < membros.size(); i++) {
            Membro m = membros.get(i);
            if(m.getNome().toLowerCase().contains(s)) 
            	filtrados.add(m);            
        }
		return filtrados;
	}
    
    
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public List<Celula> getCelulas() {
		return celulas;
	}

	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}

	public CelulaLN getCelulaLN() {
		return celulaLN;
	}

	public void setCelulaLN(CelulaLN celulaLN) {
		this.celulaLN = celulaLN;
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

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isExistemsubregioes() {
		return existemsubregioes;
	}

	public void setExistemsubregioes(boolean existemsubregioes) {
		this.existemsubregioes = existemsubregioes;
	}

	public Celula getCelulaorigem() {
		return celulaorigem;
	}

	public void setCelulaorigem(Celula celulaorigem) {
		this.celulaorigem = celulaorigem;
	}

	public String getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(String datacadastro) {
		this.datacadastro = datacadastro;
	}


}
