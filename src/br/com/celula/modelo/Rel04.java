package br.com.celula.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class Rel04 implements Serializable{

	private static final long serialVersionUID = 1L;
	private String celula;
	private BigInteger totalmembros;
	private BigDecimal totalmembrospresente;
	private BigDecimal percentual;
	public String getCelula() {
		return celula;
	}
	public void setCelula(String celula) {
		this.celula = celula;
	}
	public BigInteger getTotalmembros() {
		return totalmembros;
	}
	public void setTotalmembros(BigInteger totalmembros) {
		this.totalmembros = totalmembros;
	}
	public BigDecimal getTotalmembrospresente() {
		return totalmembrospresente;
	}
	public void setTotalmembrospresente(BigDecimal totalmembrospresente) {
		this.totalmembrospresente = totalmembrospresente;
	}
	public BigDecimal getPercentual() {
		return percentual;
	}
	public void setPercentual(BigDecimal percentual) {
		this.percentual = percentual;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
