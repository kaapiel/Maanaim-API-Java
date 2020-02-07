package br.com.celula.bean;


import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Endereco;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Reuniao;
import br.com.celula.negocio.EnderecoLN;
import br.com.celula.negocio.ReuniaoLN;

@ManagedBean(name="agendareuniaoMB")
@ViewScoped
public class AgendarReuniaoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Reuniao reuniao;
	private List<Reuniao> reunioes = new ArrayList<Reuniao>();
	private ReuniaoLN reuniaoLN;
	private int controlaCadastro = 0;
	private String msg;
	private List<String> erros = new ArrayList<String>();
	private List<Membro> membros = new ArrayList<Membro>();
	private Celula  celula = new Celula();
	private String sCep;
	private String rua;
	private String bairro;
	private String cidade;
	private String uf;
	private Endereco endereco = new Endereco();
	private boolean existeReuniaoComMenosDeUmaSemana;

	
	public AgendarReuniaoMB(){
		listar();
		controlaCadastro = 0;
		this.reuniao = new Reuniao();	
	}
	
	public void listar(){
		reuniaoLN = new ReuniaoLN();
		this.reunioes = reuniaoLN.getListReunioes();				
	}
	
	public void novo(){
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		if(this.reuniao.getIdreuniao()==null){
			msg = "Nenhum registro selecionado para exclusão.";
			mensagens();			
		}else{
			reuniaoLN = new ReuniaoLN();
			msg = reuniaoLN.removeReuniao(reuniao); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void buscaCep(){		
		EnderecoLN eln = new EnderecoLN();
		try {
			endereco = eln.getEndereco(sCep);
			reuniao.setEndereco(endereco);
		} catch (IOException e) {
			msg = e.getMessage();
			mensagens();
		}
		atualizaEndereco();	
	}
	
	private void atualizaEndereco() {
		this.bairro=this.endereco.getBairro();
		this.cidade=this.endereco.getCidade();
		this.rua=this.endereco.getLogradouro();
		this.uf=this.endereco.getUf();
	}
	
	private void carregarCamposParaGravar() {
		endereco = new Endereco();
		endereco.setBairro(bairro);
		endereco.setLogradouro(rua);
		endereco.setCep(sCep);
		endereco.setUf(uf);
		endereco.setCidade(cidade);
		reuniao.setEndereco(endereco);
	}
	
	private void carragaCamposNaPagina(){		
		this.bairro=reuniao.getEndereco().getBairro();
		this.sCep=reuniao.getEndereco().getCep();
		this.cidade=reuniao.getEndereco().getCidade();
		this.rua=reuniao.getEndereco().getLogradouro();
		this.uf=reuniao.getEndereco().getUf();
	}
	
	public void grava(){
		if(validaCampos()){
			if(reuniao.getDatareuniao().before(new Date(System.currentTimeMillis()))){
				msg="A data da reunião não pode ser menor que hoje.";		
				mensagens();				
			}else{
				if(reuniaoLN.existeReuniaoComMenosDeUmaSemana(reuniao)){
					msg="Somente é possível o agendamento com diferença de uma semana entre as reuniões.";		
					mensagens();
				}else{
					reuniaoLN = new ReuniaoLN();
					carregarCamposParaGravar();
					if(controlaCadastro==1)		
						msg = reuniaoLN.adicionaReuniao(this.reuniao);
		
					if(controlaCadastro==2)
						msg = reuniaoLN.atualizaReuniao(this.reuniao);
					
					mensagens();
					limpaCadastro();
					listar();
				}
			}
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","Campos com * são obrigatórios.")); 
		}
	}

	private boolean validaCampos() {
		boolean b = true;
		if(reuniao.getDatareuniao()==null)			
			b= false;	
		if(reuniao.getHorareuniao()==null)			
			b= false;				
		if(celula.getIdcelula()==null)			
			b= false;		
		if(rua.length()==0){
			msg = "Campo Rua/Av. é obrigatório!";
			erros.add(msg);			
			b = false;
		}
		if(bairro.length()==0){
			msg = "Campo Bairro é obrigatório!";
			erros.add(msg);			
			b = false;
		}
		if(cidade.length()==0){
			msg = "Campo Cidade é obrigatório!";
			erros.add(msg);			
			b = false;
		}
		if(uf.length()==0){
			msg = "Campo UF é obrigatório!";
			erros.add(msg);			
			b = false;
		}
		if(sCep.length()==0){
			msg = "Campo CEP é obrigatório!";
			erros.add(msg);			
			b = false;
		}		
		return b;
	}

    public Date getMinDate() {
        return new Date(System.currentTimeMillis());
    }
    	
	public void limpaCadastro(){
		this.reuniao = new Reuniao();
		controlaCadastro=0;
		celula = new Celula();
		endereco = new Endereco();
		this.bairro="";
		this.sCep="";
		this.cidade="";
		this.rua="";
		this.uf="";
	}
	
	public void reuniaoSelected(SelectEvent event){
		this.reuniao = (Reuniao) event.getObject();
		carragaCamposNaPagina();
		controlaCadastro=0;
	}

	public void reuniaoSelecionada(){
		celula = reuniao.getCelula();
		carragaCamposNaPagina();
		controlaCadastro=0;
	}
	
    public void reuniaoSelecionadoPeloDialog(Reuniao r) {  
        RequestContext.getCurrentInstance().closeDialog(r);  
    } 

    public void celulaSelecionada(SelectEvent event) {
    	Celula c = (Celula) event.getObject();
    		this.celula = c;
    		this.reuniao.setCelula(c);		    		
    }
	  
    public String dataFormatada(Date d){
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
		return formatador.format(d);
	}

    public String horaFormatada(Date d){
		SimpleDateFormat formatador = new SimpleDateFormat("HH:mm");
		return formatador.format(d);
	}
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public Reuniao getReuniao() {
		return reuniao;
	}

	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}

	public List<Reuniao> getReunioes() {
		return reunioes;
	}

	public void setReunioes(List<Reuniao> reunioes) {
		this.reunioes = reunioes;
	}

	public ReuniaoLN getReuniaoLN() {
		return reuniaoLN;
	}

	public void setReuniaoLN(ReuniaoLN reuniaoLN) {
		this.reuniaoLN = reuniaoLN;
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

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getsCep() {
		return sCep;
	}

	public void setsCep(String sCep) {
		this.sCep = sCep;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isExisteReuniaoComMenosDeUmaSemana() {
		return existeReuniaoComMenosDeUmaSemana;
	}

	public void setExisteReuniaoComMenosDeUmaSemana(
			boolean existeReuniaoComMenosDeUmaSemana) {
		this.existeReuniaoComMenosDeUmaSemana = existeReuniaoComMenosDeUmaSemana;
	}
	
}
