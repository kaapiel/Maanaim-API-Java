package br.com.celula.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Rel02 implements Serializable{

	private static final long serialVersionUID = 1L;
	private String membro;
	private String celula;
	private String subregiao;
	private String regiao;
	private BigDecimal frequencia;
	private List<Rel02> list;
	
	public String getMembro() {
		return membro;
	}
	public void setMembro(String membro) {
		this.membro = membro;
	}
	public String getCelula() {
		return celula;
	}
	public void setCelula(String celula) {
		this.celula = celula;
	}
	public String getSubregiao() {
		return subregiao;
	}
	public void setSubregiao(String subregiao) {
		this.subregiao = subregiao;
	}
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public BigDecimal getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(BigDecimal frequencia) {
		this.frequencia = frequencia;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Rel02> getList() {
		return list;
	}
	public void setList(List<Rel02> list) {
		this.list = list;
	}

	
}
