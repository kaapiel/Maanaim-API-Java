package br.com.celula.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;


@Entity(name="avisos")
@Table(name = "avisos")
@XmlRootElement
public class Avisos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idaviso;
	
	@Column(length=200)
	private String titulo="";
	
	@Lob
	private byte[] imagemEvento;
	
	@Temporal(TemporalType.DATE)
	private Date dataPublicacao;
	
	@Temporal(TemporalType.DATE)
	private Date dataEvento;	
	

	public Avisos(Integer idaviso){
		this.idaviso = idaviso;
	}
	
	public Avisos(){
	}
	
	public Integer getIdaviso() {
		return idaviso;
	}

	public void setIdaviso(Integer idaviso) {
		this.idaviso = idaviso;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public byte[] getImagemEvento() {
		return imagemEvento;
	}

	public void setImagemEvento(byte[] imagemEvento) {
		this.imagemEvento = imagemEvento;
	}

	public Date getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(Date dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public Date getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(Date dataEvento) {
		this.dataEvento = dataEvento;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idaviso == null) ? 0 : idaviso.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((imagemEvento == null) ? 0 : imagemEvento.hashCode());
		result = prime * result + ((dataPublicacao == null) ? 0 : dataPublicacao.hashCode());
		result = prime * result	+ ((dataEvento == null) ? 0 : dataEvento.hashCode());
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
		Avisos other = (Avisos) obj;
		if (idaviso == null) {
			if (other.idaviso != null)
				return false;
		} else if (!idaviso.equals(other.idaviso))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (imagemEvento == null) {
			if (other.imagemEvento != null)
				return false;
		} else if (!imagemEvento.equals(other.imagemEvento))
			return false;
		if (dataPublicacao == null) {
			if (other.dataPublicacao != null)
				return false;
		} else if (!dataPublicacao.equals(other.dataPublicacao))
			return false;
		if (dataEvento == null) {
			if (other.dataEvento != null)
				return false;
		} else if (!dataEvento.equals(other.dataEvento))
			return false;
		return true;
	}	
   
 }