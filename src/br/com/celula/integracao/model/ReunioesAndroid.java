package br.com.celula.integracao.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReunioesAndroid {
	
	private Integer idReuniao;
	private Integer idCelula;
	private Integer idEstado;
	private String complemento;
	private Date dataReuniao;
	private Date horaReuniao;
	private String numeroLocal;
	private String cep;
	
	public Integer getIdReuniao() {
		return idReuniao;
	}
	public void setIdReuniao(Integer idReuniao) {
		this.idReuniao = idReuniao;
	}
	public Integer getIdCelula() {
		return idCelula;
	}
	public void setIdCelula(Integer idCelula) {
		this.idCelula = idCelula;
	}
	public Integer getIdEstado() {
		return idEstado;
	}
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public Date getDataReuniao() {
		return dataReuniao;
	}
	public void setDataReuniao(Date date) {
		this.dataReuniao = date;
	}
	public Date getHoraReuniao() {
		return horaReuniao;
	}
	public void setHoraReuniao(Date date) {
		this.horaReuniao = date;
	}
	public String getNumeroLocal() {
		return numeroLocal;
	}
	public void setNumeroLocal(String numeroLocal) {
		this.numeroLocal = numeroLocal;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	
	@Override
	public String toString() {
		return dataReuniao+" - "+horaReuniao;
	}
	
}
