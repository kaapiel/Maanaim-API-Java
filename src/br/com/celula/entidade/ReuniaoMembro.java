package br.com.celula.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="reuniao_membro")
@IdClass(ReuniaoMembro.class)
public class ReuniaoMembro implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idmembro")
	private Membro membro;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idreuniao")
	private Reuniao reuniao;
	
	private Boolean estevepresente=false;
	
	private Boolean estevepresentetadel=false;
	
	
	public Boolean getEstevepresentetadel() {
		return estevepresentetadel;
	}
	public void setEstevepresentetadel(Boolean estevepresentetadel) {
		this.estevepresentetadel = estevepresentetadel;
	}
	public Membro getMembro() {
		return membro;
	}
	public void setMembro(Membro membro) {
		this.membro = membro;
	}
	public Reuniao getReuniao() {
		return reuniao;
	}
	public void setReuniao(Reuniao reuniao) {
		this.reuniao = reuniao;
	}
	public Boolean getEstevepresente() {
		return estevepresente;
	}
	public void setEstevepresente(Boolean estevepresente) {
		this.estevepresente = estevepresente;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((estevepresente == null) ? 0 : estevepresente.hashCode());
		result = prime * result + ((membro == null) ? 0 : membro.hashCode());
		result = prime * result + ((reuniao == null) ? 0 : reuniao.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReuniaoMembro other = (ReuniaoMembro) obj;
		if (estevepresente == null) {
			if (other.estevepresente != null)
				return false;
		} else if (!estevepresente.equals(other.estevepresente))
			return false;
		if (membro == null) {
			if (other.membro != null)
				return false;
		} else if (!membro.equals(other.membro))
			return false;
		if (reuniao == null) {
			if (other.reuniao != null)
				return false;
		} else if (!reuniao.equals(other.reuniao))
			return false;
		return true;
	}
}
