package br.com.celula.negocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;

public class RelMembroCelulaLN implements Serializable{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private List<Membro> membros = new ArrayList<Membro>();
	@SuppressWarnings("unused")
	private List<Celula> celulas = new ArrayList<Celula>();
	
	public void getMembros(){
		MembroLN ln = new MembroLN();
		this.membros =ln.getListMembros();
	}
	public void getCelulas(){
		CelulaLN ln = new CelulaLN();
		this.celulas = ln.getList();//só as ativas
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}
	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}
}
