package br.com.celula.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="estadosreuniao")
@Table(name="estadosreuniao")
public class EstadosReuniao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idestado;
	
	@Column(length=20,nullable=false)
	private String estado;
	
	public Integer getIdestado() {
		return idestado;
	}
	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((idestado == null) ? 0 : idestado.hashCode());
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
		EstadosReuniao other = (EstadosReuniao) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (idestado == null) {
			if (other.idestado != null)
				return false;
		} else if (!idestado.equals(other.idestado))
			return false;
		return true;
	}
	

}
