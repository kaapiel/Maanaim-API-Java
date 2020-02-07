package br.com.celula.entidade;

import java.util.Date;

public class ConsA {
	
	private String celula;
	private Date data;
	private long total;
	private long presente;
	private long ausente;
	
	@SuppressWarnings("unused")
	private Double percPresente;
	
	@SuppressWarnings("unused")
	private Double percAusente;
	
	public String getCelula() {
		return celula;
	}
	public void setCelula(String celula) {
		this.celula = celula;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPresente() {
		return presente;
	}
	public void setPresente(long presente) {
		this.presente = presente;
	}
	public long getAusente() {
		return ausente;
	}
	public void setAusente(long ausente) {
		this.ausente = ausente;
	}
	public Double getPercPresente() {
		return (double) (getPresente() * 100 / getTotal());
	}
	public void setPercPresente(Double percPresente) {
		this.percPresente = percPresente;
	}
	public Double getPercAusente() {		
		return (double) (getAusente() * 100 / getTotal());
	}
	public void setPercAusente(Double percAusente) {
		this.percAusente = percAusente;
	}

}
