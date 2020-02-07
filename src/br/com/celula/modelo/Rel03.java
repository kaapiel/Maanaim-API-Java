package br.com.celula.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class Rel03 implements Serializable{

	private static final long serialVersionUID = 1L;
	private String bairro;
	private BigInteger totalmembros;
	private BigDecimal totaloferta;
	private List<Rel03> list;

	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public BigInteger getTotalmembros() {
		return totalmembros;
	}
	public void setTotalmembros(BigInteger totalmembros) {
		this.totalmembros = totalmembros;
	}
	public BigDecimal getTotaloferta() {
		return totaloferta;
	}
	public void setTotaloferta(BigDecimal totaloferta) {
		this.totaloferta = totaloferta;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<Rel03> getList() {
		return list;
	}
	public void setList(List<Rel03> list) {
		this.list = list;
	}

}
