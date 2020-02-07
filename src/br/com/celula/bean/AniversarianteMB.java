package br.com.celula.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.celula.entidade.Membro;
import br.com.celula.negocio.MembroLN;

@ManagedBean(name="niverMB")
@ViewScoped
public class AniversarianteMB implements Serializable{

	private static final long serialVersionUID = 6594673382888334470L;
	private List<Membro> membros = new ArrayList<>();
	private Integer mes=null;
	private String msg;

	
	public void listar(){
		if(mes==null||mes==0){
			msg="Informe o mês desejado.";
			mensagens();
		}else{
			MembroLN membroLN = new MembroLN();
			this.membros = membroLN.getListArniversariantes(mes);
		}
	}
	
	public void mensagens(){
        FacesContext context = FacesContext.getCurrentInstance();  	          
        context.addMessage(null, new FacesMessage("",msg));  		
	}

	public List<Membro> getMembros() {
		return membros;
	}


	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public Integer getMes() {
		return mes;
	}


	public void setMes(Integer mes) {
		this.mes = mes;
	}

}
