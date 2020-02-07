package br.com.celula.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;



@Entity(name = "reuniao")
@Table(name = "reuniao")
public class Reuniao implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idreuniao;
	
	@Temporal(TemporalType.DATE)	
	private Date datareuniao;
	
	@Temporal(TemporalType.TIME)	
	private Date horareuniao;	
	
	@ManyToOne
	@JoinColumn(name="idcelula")
	@ForeignKey(name="FK_reuniao_celula")
	private Celula celula;  
	
	@OneToMany(mappedBy = "reuniao")
	private List<ReuniaoMembro> membros;
	
	@Column(length=100,nullable=true)
	private String complemento;
	
	@Column(length=10,nullable=true)
	private String numero;
	
	@OneToOne
	@JoinColumn(name="cep",nullable=true)
	private Endereco endereco;
	
	@OneToOne
	@JoinColumn(name="idestado")
	@ForeignKey(name="FK_estadoreuniao_reuniao")
	private EstadosReuniao estado;
	
	@Column(length=10,nullable=true)
	private BigDecimal oferta;
	
	@Column(length=4,nullable=true)
	private int visitantes;
	
	@Column(length=4,nullable=true)
	private int criancas;
	
	@Column(length=200,nullable=true)
	private String observacao;
	
	
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getVisitantes() {
		return visitantes;
	}

	public void setVisitantes(int visitantes) {
		this.visitantes = visitantes;
	}

	public int getCriancas() {
		return criancas;
	}

	public void setCriancas(int criancas) {
		this.criancas = criancas;
	}

	public BigDecimal getOferta() {
		return oferta;
	}

	public void setOferta(BigDecimal oferta) {
		this.oferta = oferta;
	}

	public Reuniao(){
	}
	
	public Reuniao(Integer idreuniao){
		this.idreuniao = idreuniao;
	}
	
	public Integer getIdreuniao() {
		return idreuniao;
	}
	public void setIdreuniao(Integer idreuniao) {
		this.idreuniao = idreuniao;
	}
	public Date getDatareuniao() {
		return datareuniao;
	}
	public void setDatareuniao(Date datareuniao) {
		this.datareuniao = datareuniao;
	}
	public Date getHorareuniao() {
		return horareuniao;
	}
	public void setHorareuniao(Date horareuniao) {
		this.horareuniao = horareuniao;
	}
	public Celula getCelula() {
		return celula;
	}
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	public List<ReuniaoMembro> getMembros() {
		return membros;
	}
	public void setMembros(List<ReuniaoMembro> membros) {
		this.membros = membros;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public EstadosReuniao getEstado() {
		return estado;
	}
	public void setEstado(EstadosReuniao estado) {
		this.estado = estado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celula == null) ? 0 : celula.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result
				+ ((datareuniao == null) ? 0 : datareuniao.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result
				+ ((horareuniao == null) ? 0 : horareuniao.hashCode());
		result = prime * result
				+ ((idreuniao == null) ? 0 : idreuniao.hashCode());
		result = prime * result + ((membros == null) ? 0 : membros.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
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
		Reuniao other = (Reuniao) obj;
		if (celula == null) {
			if (other.celula != null)
				return false;
		} else if (!celula.equals(other.celula))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (datareuniao == null) {
			if (other.datareuniao != null)
				return false;
		} else if (!datareuniao.equals(other.datareuniao))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (horareuniao == null) {
			if (other.horareuniao != null)
				return false;
		} else if (!horareuniao.equals(other.horareuniao))
			return false;
		if (idreuniao == null) {
			if (other.idreuniao != null)
				return false;
		} else if (!idreuniao.equals(other.idreuniao))
			return false;
		if (membros == null) {
			if (other.membros != null)
				return false;
		} else if (!membros.equals(other.membros))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		return true;
	}	
	
}