package br.com.celula.bean;


import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.io.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Cep;
import br.com.celula.entidade.Endereco;
import br.com.celula.entidade.Membro;
import br.com.celula.negocio.CelulaLN;
import br.com.celula.negocio.EnderecoLN;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.RelatorioLN;
import br.com.celula.util.ValidaCPF;
import br.com.celula.util.ValidarData;

@ManagedBean(name="membroMB")
@ViewScoped
public class MembroMB implements Serializable{

	private static final long serialVersionUID = 6594673382888334470L;
	private Membro membro;
	private List<Membro> membros = new ArrayList<>();
	private MembroLN membroLN;
	private int controlaCadastro = 0;
	private String msg;
	private String senha1;
	private String senha2;
	private Cep cep=new Cep();
	private String sCep;
	private String rua;
	private String bairro;
	private String cidade;
	private String uf;
	private String nascimento;
	private String batismo;
	private String datacadastro;
	private Integer situacao=0;
	private Celula celula = new Celula();
	private List<String> erros = new ArrayList<String>();
	private boolean existecelulas=false;
	private String foto="foto.jpg";
	private Endereco endereco = new Endereco();
	
	
	public MembroMB(){
		membroLN = new MembroLN();
		controlaCadastro = 0;
		this.membro = new Membro();
	}
	
	public void listar(){		
		membroLN = new MembroLN();
		this.membros = membroLN.getListMembros();		
	}
	
	public void buscaCep(){		
		EnderecoLN eln = new EnderecoLN();
		try {
			endereco = eln.getEndereco(sCep);
		} catch (IOException e) {
			msg = e.getMessage();
			mensagens();
		}
		atualizaEnderecoMembro();	
	}
	
	private void atualizaEnderecoMembro() {
		this.bairro=this.endereco.getBairro();
		//this.membro.setCep(this.cep.getCep());
		this.cidade=this.endereco.getCidade();
		this.rua=this.endereco.getLogradouro();
		this.uf=this.endereco.getUf();
	}

	public void novo(){
		this.membro = new Membro();
		limpaCadastro();
		controlaCadastro=1;
	}

	public void edita(){
		controlaCadastro=2;
	}
	
	public void exclui(){
		if(this.membro.getIdmembro()==null){
			msg = "Nenhum registro selecionado para exclus�o.";
			mensagens();			
		}else{
			membroLN = new MembroLN();
			msg = membroLN.removeMembro(membro); 
			mensagens();
			limpaCadastro();
			listar();
		}
	}	

	public void grava(){
		if(validaCampos(this.membro)){
			membroLN = new MembroLN();
			carregarCamposParaGravar();
			if(controlaCadastro==1)		
				msg = membroLN.adicionaMembro(this.membro);
			if(controlaCadastro==2)
				msg = membroLN.atualizaMembro(this.membro);
			
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
		if(batismo!="")
			try {
				membro.setDataBatismo(ValidarData.stringToDate(batismo));
			} catch (ParseException e) {
				membro.setDataBatismo(null);
			}
		else
			membro.setDataBatismo(null);
		if(nascimento!="")
			try {
				membro.setNascimento(ValidarData.stringToDate(nascimento));
			} catch (ParseException e) {
				membro.setDataBatismo(null);
			}
		else
			membro.setNascimento(null);
		if(datacadastro!="")
			try {
				membro.setDatacadastro(ValidarData.stringToDate(datacadastro));
			} catch (ParseException e) {
				membro.setDatacadastro(null);
			}
		else
			membro.setDatacadastro(null);
		endereco = new Endereco();
		endereco.setBairro(bairro);
		endereco.setLogradouro(rua);
		endereco.setCep(sCep);
		endereco.setUf(uf);
		endereco.setCidade(cidade);
		membro.setEndereco(endereco);
	}
	
	private void carragaCamposNaPagina(){
		if(membro.getNascimento()!=null)
			nascimento=ValidarData.dataParaString(membro.getNascimento());
		else
			nascimento="";
		if(membro.getDataBatismo()!=null)
			batismo=ValidarData.dataParaString(membro.getDataBatismo());
		else
			batismo="";		
		if(membro.getDatacadastro()!=null)
			datacadastro=ValidarData.dataParaString(membro.getDatacadastro());
		else
			datacadastro="";		

		this.bairro=membro.getEndereco().getBairro();
		this.sCep=membro.getEndereco().getCep();
		this.cidade=membro.getEndereco().getCidade();
		this.rua=membro.getEndereco().getLogradouro();
		this.uf=membro.getEndereco().getUf();
	}

 	private boolean validaCampos(Membro m) {
		boolean b = true;
		if(m.getNome().length()==0){
			msg = "Campo nome e obrigatorio!";
			erros.add(msg);
			b= false;
		}
		if(rua.length()==0){
			msg = "Campo Rua/Av. e obrigatorio!";
			erros.add(msg);			
			b = false;
		}
		if(bairro.length()==0){
			msg = "Campo Bairro e obrigatorio!";
			erros.add(msg);			
			b = false;
		}
		if(cidade.length()==0){
			msg = "Campo Cidade e obrigatorio!";
			erros.add(msg);			
			b = false;
		}
		if(uf.length()==0){
			msg = "Campo UF e obrigatorio!";
			erros.add(msg);			
			b = false;
		}
		if(sCep.length()==0){
			msg = "Campo CEP e obrigatorio!";
			erros.add(msg);			
			b = false;
		}
		if(m.getSexo()==null||m.getSexo()==""){
			msg = "Campo sexo e obrigatorio!";
			erros.add(msg);			
			b = false;
		}				
		if(m.getCpf().length()>0){
			String cpf = m.getCpf().replace(".", "").replace("-", "");						
			if(!ValidaCPF.isCPF(cpf)){
				msg = "Campo cpf invalido!";
				mensagens();
				//erros.add(msg);	
				b =  false;	
				m.setCpf(null);
			}				
		}else{
			m.setCpf(null);
		}
		if(m.getEmail().length()==0){
			m.setEmail(null);			
		}
		if(m.getRg().length()==0){
			m.setRg(null);			
		}
		if(m.getApelido().length()==0){
			m.setApelido(null);			
		}	
		try{	
			if(datacadastro!=""){
				if(!ValidarData.dataOk(datacadastro)){
					msg = "A data de cadastro e invalida!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				if(ValidarData.maiorQueHoje(datacadastro)){
					msg = "A data de cadastro nao pode ser maior que hoje!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				if(ValidarData.menorQueTalAno(datacadastro, 2000)){
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
			if(batismo!=""){
				if(!ValidarData.dataOk(batismo)){
					msg = "A data de batismo e invalida!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				if(ValidarData.maiorQueHoje(batismo)){
					msg = "A data de batismo nao pode ser maior que hoje!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}
				if(ValidarData.maiorQueNascimento(batismo,nascimento)){
					msg = "A data de batismo nao pode ser menor que a data de nascimento!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				/*	if(ValidarData.menorQueTalAno(batismo, 1915)){
					msg = "A data de batismo nao pode ser menor que 1915!";
					erros.add(msg);			
					b = false;
				}	*/
			}
			if(nascimento!=""){
				if(!ValidarData.dataOk(nascimento)){
					msg = "A data de nascimento e invalida!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				if(ValidarData.maiorQueHoje(nascimento)){
					msg = "A data de nascimento nao pode ser maior que hoje!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
				if(ValidarData.menorQueTalAno(nascimento, 1915)){
					msg = "A data de nascimento nao podeser menor que 1915!";
					mensagens();
					//erros.add(msg);			
					b = false;
				}	
			}
			if(membro.getCelula().getIdcelula()==null){
				//msg = "A C�lula e obrigatoria.";
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
    	foto = membroLN.exibeFoto(e); 
    	try {
			membro.setFoto(IOUtils.toByteArray(e.getFile().getInputstream()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    }
	
    public void celulaSelecionadaPeloDialog(Membro m) {
        RequestContext.getCurrentInstance().closeDialog(m);
    }
    		
	public void limpaCadastro(){
		this.membro = new Membro();
		controlaCadastro=0;
		senha1=null;
		senha2=null;
		nascimento="";
		datacadastro = "";
		batismo="";
		this.bairro="";
		this.sCep="";
		this.cidade="";
		this.rua="";
		this.uf="";
		this.celula=new Celula();
		foto="foto.jpg";
	}
	
	public void situacaoSelecionada(ValueChangeEvent s){
		/*if(s.getNewValue()!="0")
			this.membro.setIdSituacao(((int) s.getNewValue()));		*/
	}
	
	public void membroSelected(SelectEvent event){
		this.membro = (Membro) event.getObject();
		carragaCamposNaPagina();	
		controlaCadastro=0;
	}
	
    public void celulaSelecionadaPeloDialog(Celula c) {
        RequestContext.getCurrentInstance().closeDialog(c);
    }
 
    public void membroSelecionadoPeloDialog(Membro m) {
        RequestContext.getCurrentInstance().closeDialog(m);
        carragaCamposNaPagina();
    }
    
    public void membroSelecionadoPeloDialog() {
        RequestContext.getCurrentInstance().closeDialog(membro);
    }    
    
    public void membroSelecionado(SelectEvent event) {
        this.membro = (Membro) event.getObject();
        this.celula = membro.getCelula();
        membroLN = new MembroLN();
        foto = membroLN.recuperaFoto(membro);
        carragaCamposNaPagina();        
    }	    
 
    public void celulaSelecionada(SelectEvent event) {
        this.celula = (Celula) event.getObject();
        this.membro.setCelula(celula);
    }	
        
    public void existemCelulaCadastrada(){
    	CelulaLN cln = new CelulaLN();
    	if(cln.existeCelulaCadastrada()){
	    	existecelulas = true;
    	}else{
	    	msg = "Cadastro nao pode ser aberto pois nao ha c�luas cadastradas.";
	    	mensagens();
    		existecelulas = false;
    	}
    }
    
	public void imprimirFichaMembro(){
		RelatorioLN ln = new RelatorioLN();
		try {
			ln.geraFichaMembro(membro);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}	
//===============================================================================================================//

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

	public MembroLN getMembroLN() {
		return membroLN;
	}

	public void setMembroLN(MembroLN membroLN) {
		this.membroLN = membroLN;
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

	public Cep getCep() {
		return cep;
	}

	public void setCep(Cep cep) {
		this.cep = cep;
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

	public String getNascimento() {
		return nascimento;
	}

	public void setNascimento(String nascimento) {
		this.nascimento = nascimento;
	}

	public String getBatismo() {
		return batismo;
	}

	public void setBatismo(String batismo) {
		this.batismo = batismo;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public boolean isExistecelulas() {
		return existecelulas;
	}

	public void setExistecelulas(boolean existecelulas) {
		this.existecelulas = existecelulas;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDatacadastro() {
		return datacadastro;
	}

	public void setDatacadastro(String datacadastro) {
		this.datacadastro = datacadastro;
	}

}
