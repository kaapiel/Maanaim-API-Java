package br.com.celula.integracao.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.celula.entidade.EstadosReuniao;

@XmlRootElement
public class AgendamentoPresencaAndroid {
	
	private EstadosReuniao er;
	private BigDecimal oferta;
	private Boolean estevePresente, estevePresenteTadel;
	private Integer idReuniao, idMembro;
	
	
	
	public Boolean getEstevePresenteTadel() {
		return estevePresenteTadel;
	}

	public void setEstevePresenteTadel(Boolean estevePresenteTadel) {
		this.estevePresenteTadel = estevePresenteTadel;
	}

	public EstadosReuniao getEr() {
		return er;
	}

	public void setEr(EstadosReuniao er) {
		this.er = er;
	}

	public Boolean getEstevePresente() {
		return estevePresente;
	}

	public void setEstevePresente(Boolean estevePresente) {
		this.estevePresente = estevePresente;
	}

	public BigDecimal getOferta() {
		return oferta;
	}

	public void setOferta(BigDecimal oferta) {
		this.oferta = oferta;
	}

	public Integer getIdReuniao() {
		return idReuniao;
	}

	public void setIdReuniao(Integer idReuniao) {
		this.idReuniao = idReuniao;
	}

	public int getIdMembro() {
		return idMembro;
	}

	public void setIdMembro(int idMembro) {
		this.idMembro = idMembro;
	}
	
}
