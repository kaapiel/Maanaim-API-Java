package br.com.celula.bean;


import java.io.Serializable;
import java.util.ArrayList;
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
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.RegiaoLN;
import br.com.celula.negocio.SubRegiaoLN;

@ManagedBean(name="subregiaoMB")
@ViewScoped
public class SubRegiaoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private SubRegiao subregiao;
	private List<SubRegiao> subregioes = new ArrayList<SubRegiao>();
	private List<Regiao> regioes = new ArrayList<Regiao>();
	private Regiao regiao = new Regiao();
	private SubRegiaoLN subregiaoLN;
	private Membro membro;
	private List<Membro> membros = new ArrayList<Membro>();
	private int controlaCadastro = 0;
	private String msg;
	private List<String> erros = new ArrayList<String>();
	private boolean existemregioes=false;
	
	@PostConstruct
	public void vai(){
		controlaCadastro = 0;
		this.subregiao = new SubRegiao();
		listar();
		listarRegioes();
	}
	
	public void listar(){	
		subregiaoLN = new SubRegiaoLN();
		this.subregioes = subregiaoLN.getListSubRegioes();	
	}
	
	public void listarMembros(){
		MembroLN membroLN = new MembroLN();
		this.membros = membroLN.getListMembrosAtivos();
		listarRegioes();
		controlaCadastro=0;	
	}
	
	public void listarRegioes(){
		RegiaoLN rln = new RegiaoLN();
		regioes = rln.getListRegioes();
	}
	
	public void novo(){
		this.subregiao = new SubRegiao();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
		regiao = subregiao.getRegiao();
	}
	
	public void exclui(){
		if(this.subregiao.getIdsubregiao()==null){
			msg = "Nenhum registro selecionado para exclus�o.";
			mensagens();			
		}else{
			subregiaoLN = new SubRegiaoLN();
			msg = subregiaoLN.removeSubRegiao(subregiao); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos()){
			subregiaoLN = new SubRegiaoLN();
			if(controlaCadastro==1)		
				msg = subregiaoLN.adicionaSubRegiao(this.subregiao);

			if(controlaCadastro==2)
				msg = subregiaoLN.atualizaSubRegiao(this.subregiao);
			
			mensagens();
			listar();
			limpaCadastro();
		}else{
			//for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * s�o obrigatorios.")); 
			//erros.clear(); 
		}
	}

	private boolean validaCampos() {
		boolean b = true;
		if(subregiao.getNome()==""){
			erros.add("Campo nome e obrigatorio.");
			b= false;
		}
		if(subregiao.getDescricao()==""){
			erros.add("Campo nome e obrigatorio.");
			b= false;
		}
		if(regiao==null){
			erros.add("Campo Regi�o e obrigatoria.");
			b= false;
		}
		if(regiao.getCor()==""){
			erros.add("Campo Regi�o e obrigatoria.");
			b= false;
		}		
		return b;
	}
		
	public void carregaRegiao(){
		
	}
	
	public void limpaCadastro(){
		this.subregiao = new SubRegiao();
		controlaCadastro=0;
		membro = new Membro();
	}
	
	public void subRegiaoSelected(SelectEvent event){
		this.subregiao = (SubRegiao) event.getObject();
		regiao = subregiao.getRegiao();
		controlaCadastro=0;
	}    
	
    public void existemRegiaoCadastrada(){
    	RegiaoLN rln = new RegiaoLN();
    	if(!rln.existeRegiaoCadastrada()){
	    	msg = "Cadastro nao pode ser aberto pois nao ha regioes cadastradas.";
	    	mensagens();
	    	existemregioes = false;
    	}else{
    		existemregioes = true;
    	}
    		
    }

    public void subRegiaoSelecionadoPeloDialog(SubRegiao r) {  
        RequestContext.getCurrentInstance().closeDialog(r);  
    } 
    
	public void regiaoSelected(SelectEvent event){
		this.subregiao.setRegiao((Regiao) event.getObject());
		regiao = subregiao.getRegiao();
	}	

	private List<Celula> celulasRegiao(){
		List<Celula> celulas = new ArrayList<Celula>();
		for(Regiao r:regioes)
			for(SubRegiao sr:r.getSubregioes())
				for(Celula c:sr.getCelulas())
					celulas.add(c);	
		return celulas;
	}
	
    public void adicionaResponsavel(){
    	if(subregiao.getSupervisores().size()==2){
    		msg="� permitido apenas 2 supervisores. Remova algum para depois adicionar.";
    		mensagens();
    	}else{
			subregiao.getSupervisores().add(membro);
			membro = new Membro();
    	}
    }

    public void removeResponsavel(){
    	subregiao.getSupervisores().remove(membro);
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

	public SubRegiao getSubregiao() {
		return subregiao;
	}

	public void setSubregiao(SubRegiao subregiao) {
		this.subregiao = subregiao;
	}

	public List<SubRegiao> getSubregioes() {
		return subregioes;
	}

	public void setSubregioes(List<SubRegiao> subregioes) {
		this.subregioes = subregioes;
	}

	public SubRegiaoLN getSubregiaoLN() {
		return subregiaoLN;
	}

	public void setSubregiaoLN(SubRegiaoLN subregiaoLN) {
		this.subregiaoLN = subregiaoLN;
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

	public boolean isExistemregioes() {
		return existemregioes;
	}

	public void setExistemregioes(boolean existemregioes) {
		this.existemregioes = existemregioes;
	}

	public List<Regiao> getRegioes() {
		return regioes;
	}

	public void setRegioes(List<Regiao> regioes) {
		this.regioes = regioes;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public Membro getMembro() {
		return membro;
	}

	public void setMembro(Membro membro) {
		this.membro = membro;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}
	
}
