package br.com.celula.integracao.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LiderAndroid {
	private Integer idmembro;
	private Integer idcelula;

	public Integer getIdmembro() {
		return idmembro;
	}
	public void setIdmembro(Integer idmembro) {
		this.idmembro = idmembro;
	}
	public Integer getIdcelula() {
		return idcelula;
	}
	public void setIdcelula(Integer idcelula) {
		this.idcelula = idcelula;
	}
	

}
