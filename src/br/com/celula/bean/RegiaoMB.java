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

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.RegiaoLN;

@ManagedBean(name="regiaoMB")
@ViewScoped
public class RegiaoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Regiao regiao;
	private List<Regiao> regioes = new ArrayList<>();
	private RegiaoLN regiaoLN;
	private int controlaCadastro = 0;
	private String msg;
	private Membro membro;
	private List<String> erros = new ArrayList<String>();
	private List<Membro> membros = new ArrayList<Membro>();

	public RegiaoMB(){
		listar();
		controlaCadastro = 0;
		this.regiao = new Regiao();	
	}
	
	public void listar(){
		regiaoLN = new RegiaoLN();
		this.regioes = regiaoLN.getListRegioes();				
	}

	public void listarMembros(){
		MembroLN membroLN = new MembroLN();
		this.membros = membroLN.getListMembrosAtivos(regiao);		
		controlaCadastro=0;		
	}
	
	public void novo(){
		regiaoLN = new RegiaoLN();
		this.regiao = new Regiao();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		if(this.regiao.getIdregiao()==null){
			msg = "Nenhum registro selecionado para exclus�o.";
			mensagens();			
		}else{
			regiaoLN = new RegiaoLN();
			msg = regiaoLN.removeRegiao(regiao); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos()){
			regiaoLN = new RegiaoLN();
			if(controlaCadastro==1)		
				msg = regiaoLN.adicionaRegiao(this.regiao);

			if(controlaCadastro==2)
				msg = regiaoLN.atualizaRegiao(this.regiao);
			
			mensagens();
			limpaCadastro();
			listar();
		}else{
			//for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * s�o obrigatorios.")); 
			//erros.clear(); 
		}
	}

	private boolean validaCampos() {
		boolean b = true;
		if(regiao.getCor().length()==0)
			b= false;
		if(regiao.getDescricao().length()==0)
			b= false;		
		return b;
	}
		
	public void limpaCadastro(){
		this.regiao = new Regiao();
		controlaCadastro=0;
		membro = new Membro();
	}
	
	public void regiaoSelected(SelectEvent event){
		this.regiao = (Regiao) event.getObject();
		controlaCadastro=0;
	}
	
    public void regiaoSelecionadoPeloDialog(Regiao r) {  
        RequestContext.getCurrentInstance().closeDialog(r);  
    } 
    
    private List<Celula> celulasRegiao(){
		List<Celula> celulas = new ArrayList<Celula>();
		for(Regiao r:regioes)
			for(SubRegiao sr:r.getSubregioes())
				for(Celula c:sr.getCelulas())
					celulas.add(c);	
		return celulas;
	} 

    private List<SubRegiao> subRegioes(){
		List<SubRegiao> sbs = new ArrayList<SubRegiao>();
		for(Regiao r:regioes)
			for(SubRegiao sr:r.getSubregioes())
					sbs.add(sr);	
		return sbs;
	} 
    
    public void adicionaResponsavel(){
    	if(regiao.getPastores().size()==2){
    		msg="� permitido apenas 2 respons�veis. Remova algum para depois adicionar.";
    		mensagens();
    	}else{             			
    		regiao.getPastores().add(membro);
    		membro = new Membro();
        }  		
    }
    
    public void removeResponsavel(){
		regiao.getPastores().remove(membro);
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

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public List<Regiao> getRegioes() {
		return regioes;
	}

	public void setRegioes(List<Regiao> regioes) {
		this.regioes = regioes;
	}

	public RegiaoLN getRegiaoLN() {
		return regiaoLN;
	}

	public void setRegiaoLN(RegiaoLN regiaoLN) {
		this.regiaoLN = regiaoLN;
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

	
}
