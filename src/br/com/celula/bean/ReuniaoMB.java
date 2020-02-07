package br.com.celula.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.EstadosReuniao;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Reuniao;
import br.com.celula.entidade.ReuniaoMembro;
import br.com.celula.negocio.EstadosReuniaoLN;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.ReuniaoLN;
import br.com.celula.negocio.ReuniaoMembroLN;

@ManagedBean(name="reuniaoMB")
@ViewScoped
public class ReuniaoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Reuniao reuniao = new Reuniao();
	private EntityManager manager;
	private List<Reuniao> reunioes = new ArrayList<Reuniao>();
	private List<Reuniao> reunioesDaCelula = new ArrayList<Reuniao>();
	private List<EstadosReuniao> status = new ArrayList<EstadosReuniao>();
	private EstadosReuniao statusselecionado = new EstadosReuniao();
	private List<ReuniaoMembro> participantes = new ArrayList<ReuniaoMembro>();
	private ReuniaoLN reuniaoLN;
	private String msg;
	private List<String> erros = new ArrayList<String>();
	private List<Membro> membros = new ArrayList<Membro>();
	private List<Membro> presentes = new ArrayList<Membro>();
	private Celula  celula = new Celula();
	private DualListModel<Membro> dualMembros = new DualListModel<Membro>();
	private boolean naoPermiteEditarReuniao=true;
	private boolean disabilitarBotaoEditar=true;
	private boolean naoexistemembrosnacelula=true;
	
	
	public ReuniaoMB(){
		this.reuniao = new Reuniao();	
		listarStatus();
		statusselecionado.setIdestado(1);
	}
	
	private void listarStatus() {
		EstadosReuniaoLN ln = new EstadosReuniaoLN();
		status = ln.getList();
	}

	public void grava(){
		if(validaCampos()){
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
			manager = factory.createEntityManager();
			reuniaoLN=new ReuniaoLN();
			reuniao.setEstado(statusselecionado);
			reuniaoLN.atualizaReuniao(this.reuniao);			
			ReuniaoMembroLN rmln = new ReuniaoMembroLN();
			
			msg=rmln.gravar(reuniao,dualMembros.getSource(),participantes);
			manager.close();
			mensagens();
			limpaCadastro();
		}else{
			for(String s:erros)
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"",s)); 
			erros.clear(); 
		}
	}

	private boolean validaCampos() {
		boolean b = true;
		if(celula.getIdcelula()==null){
			erros.add("Selecione um célula!");
			b= false;
		}
		if(statusselecionado.getIdestado()==null){
			erros.add("Selecione um estado da célula!");
			b= false;		
		} 
		if(reuniao.getIdreuniao()==null){
			erros.add("Selecione uma reunião!");
			b= false;		
		} 		
		return b;
	}
		
	public void limpaCadastro(){
		reuniao = new Reuniao();
		reunioesDaCelula = new ArrayList<Reuniao>();
		celula = new Celula();
		statusselecionado = new EstadosReuniao();		
		membros = new ArrayList<Membro>();
		participantes = new ArrayList<ReuniaoMembro>();
		dualMembros = new DualListModel<Membro>();
		naoPermiteEditarReuniao=true;
	}
	
	public void reuniaoSelected(SelectEvent event){
		reuniao = (Reuniao) event.getObject();
	}

	public void reuniaoSelecionada(){
		celula = reuniao.getCelula();
	}
	
    public void reuniaoSelecionadoPeloDialog(Reuniao r) {  
        RequestContext.getCurrentInstance().closeDialog(r);  
    } 

    public void celulaSelecionada(SelectEvent event) {
        this.celula = (Celula) event.getObject();
        carregaReunioesDaCelula();
        carregaMembrosDaCelula();
        carregaParticipantes();
    }	
    
    public void carregaMembrosDaCelula() {
    	MembroLN mln = new MembroLN();
    	List<Membro> rmv = new ArrayList<Membro>();
    	membros = new ArrayList<Membro>();
    	presentes = new ArrayList<Membro>();
    	naoPermiteEditarReuniao=true;
    	membros.addAll(mln.getListMembrosAtivos(celula));
    	if(membros.size()==0)
    		naoexistemembrosnacelula=true;
    	else
    		naoexistemembrosnacelula=false;
    	if(reunioesDaCelula.size()>0){
			presentes.addAll( mln.getListMembrosPresentesNaReuniao(reuniao));
			for(Membro m:presentes)
				for(Membro x:membros)
					if(x.getIdmembro().equals(m.getIdmembro()))
						rmv.add(m);
			for(Membro m:rmv)
				membros.remove(m);
			
    		dualMembros = new DualListModel<Membro>(membros,presentes);
    	}else{
    		dualMembros = new DualListModel<Membro>(membros, new ArrayList<Membro>());
    	}   	
    	carregaParticipantes();
	}

    public void statusDaReuniaoSelecionado(){
    	carregaReunioesDaCelula();
    	carregaMembrosDaCelula();
    	carregaParticipantes();
    	naoPermiteEditarReuniao=true;
    }
    
	private void carregaParticipantes() {
		if(reuniao.getIdreuniao()!=null){
			ReuniaoMembroLN rmln = new ReuniaoMembroLN();
			participantes = new ArrayList<ReuniaoMembro>();
			participantes.addAll(rmln.getListMembrosPresentesNaReuniao(reuniao));
		}
	}

	public void carregaReunioesDaCelula(){
		naoPermiteEditarReuniao=true;
		
    	if(statusselecionado.getIdestado()!=null&&celula.getIdcelula()!=null){
	    	reuniaoLN = new ReuniaoLN();
	    	reunioesDaCelula = new ArrayList<Reuniao>();
	    	reunioesDaCelula.addAll(reuniaoLN.getListReunioesPorCelula(celula,statusselecionado));    	    
    	}
    	if(reunioesDaCelula.size()>0){
    		reuniao=reunioesDaCelula.get(0);
    		disabilitarBotaoEditar=false;
    	}else{
    		reunioesDaCelula = new ArrayList<Reuniao>();
    		reuniao=new Reuniao();
    		disabilitarBotaoEditar=true;
    	}
    }

	@SuppressWarnings("unchecked")
	public void onTransfer(TransferEvent e) {
		Set<Membro> mbs = new HashSet<Membro>();
		mbs.addAll((List<Membro>) e.getItems());
		if(e.isAdd()){
			for(Membro m:mbs){
				ReuniaoMembro rm1 = new ReuniaoMembro();
				rm1.setEstevepresente(true);
				rm1.setMembro(m);
				//rm1.setOferta(new BigDecimal(0));
				rm1.setReuniao(reuniao);
				participantes.add(rm1);			
			}
		}	
		if(e.isRemove()){
			List<ReuniaoMembro> removidos = new ArrayList<ReuniaoMembro>();
			for(Membro m : mbs)
				for(ReuniaoMembro rm:participantes)
					if(m.getIdmembro().equals(rm.getMembro().getIdmembro()))
						removidos.add(rm);
			for(ReuniaoMembro rm:removidos)
				participantes.remove(rm);
				
		}
	}
	
	public void editarReuniao() {
		naoPermiteEditarReuniao=false;
		for(EstadosReuniao s:status)
			if(s.getIdestado().equals(2))
				statusselecionado=s;
	}

    public void onCellEdit(CellEditEvent event) {
  /*  	for(ReuniaoMembro r:participantes)
    		System.out.println(r.getOferta());
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue(); */
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

	public List<Reuniao> getReunioesDaCelula() {
		return reunioesDaCelula;
	}

	public void setReunioesDaCelula(List<Reuniao> reunioesDaCelula) {
		this.reunioesDaCelula = reunioesDaCelula;
	}

	public List<EstadosReuniao> getStatus() {
		return status;
	}

	public void setStatus(List<EstadosReuniao> status) {
		this.status = status;
	}

	public EstadosReuniao getStatusselecionado() {
		return statusselecionado;
	}

	public void setStatusselecionado(EstadosReuniao statusselecionado) {
		this.statusselecionado = statusselecionado;
	}

	public DualListModel<Membro> getDualMembros() {
		return dualMembros;
	}

	public void setDualMembros(DualListModel<Membro> dualMembros) {
		this.dualMembros = dualMembros;
	}

	public List<Membro> getPresentes() {
		return presentes;
	}

	public void setPresentes(List<Membro> presentes) {
		this.presentes = presentes;
	}

	public List<ReuniaoMembro> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<ReuniaoMembro> participantes) {
		this.participantes = participantes;
	}

	public boolean isNaoPermiteEditarReuniao() {
		return naoPermiteEditarReuniao;
	}

	public void setNaoPermiteEditarReuniao(boolean naoPermiteEditarReuniao) {
		this.naoPermiteEditarReuniao = naoPermiteEditarReuniao;
	}

	public boolean isDisabilitarBotaoEditar() {
		return disabilitarBotaoEditar;
	}

	public void setDisabilitarBotaoEditar(boolean disabilitarBotaoEditar) {
		this.disabilitarBotaoEditar = disabilitarBotaoEditar;
	}

	public boolean isNaoexistemembrosnacelula() {
		return naoexistemembrosnacelula;
	}

	public void setNaoexistemembrosnacelula(boolean naoexistemembrosnacelula) {
		this.naoexistemembrosnacelula = naoexistemembrosnacelula;
	}


	
}
