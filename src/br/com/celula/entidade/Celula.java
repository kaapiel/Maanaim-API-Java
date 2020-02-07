package br.com.celula.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity(name = "celula")
@Table(name = "celula")
@XmlRootElement
public class Celula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idcelula;
	
	@Column(length=80,unique=true)
	private String nome;
	
	@Column(length=300)
	private String descricao;
	
	@Temporal(TemporalType.DATE)
	private Date datacriacao;
	
	@OneToOne
	@JoinColumn(name="idcelulaorigem")
	@ForeignKey(name="FK_celula_celulaorigem")
	private Celula celulaorigem;
	
	@ManyToOne(targetEntity=SubRegiao.class)
	@JoinColumn(name="idsubregiao")
	private SubRegiao subregiao;		
	
	@OneToMany(mappedBy = "celula", targetEntity = Reuniao.class)
	@ForeignKey(name="FK_celula_reuniao")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Reuniao> reunioes;
	
	@OneToMany(mappedBy = "celula", targetEntity = Membro.class)
	@ForeignKey(name="FK_celula_membro")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Membro> membros;
	
	private Boolean ativo=true;
	
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="lideres", joinColumns={@JoinColumn(name="idcelula")}, inverseJoinColumns={@JoinColumn(name="idmembro")})
	@ForeignKey(name="FK_celula_lideres")
	@Size(min = 0, max = 2)
    @LazyCollection(LazyCollectionOption.FALSE)
	private List<Membro> lideres;
	
	public Integer getIdcelula() {
		return idcelula;
	}
	public void setIdcelula(Integer idcelula) {
		this.idcelula = idcelula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDatacriacao() {
		return datacriacao;
	}
	public void setDatacriacao(Date datacriacao) {
		this.datacriacao = datacriacao;
	}
	public Celula getCelulaorigem() {
		return celulaorigem;
	}
	public void setCelulaorigem(Celula celulaorigem) {
		this.celulaorigem = celulaorigem;
	}
	public SubRegiao getSubregiao() {
		return subregiao;
	}
	public void setSubregiao(SubRegiao subregiao) {
		this.subregiao = subregiao;
	}
	public List<Reuniao> getReunioes() {
		return reunioes;
	}
	public void setReunioes(List<Reuniao> reunioes) {
		this.reunioes = reunioes;
	}
	public List<Membro> getMembros() {
		return membros;
	}
	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public List<Membro> getLideres() {
		return lideres;
	}
	public void setLideres(List<Membro> lideres) {
		this.lideres = lideres;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result
				+ ((datacriacao == null) ? 0 : datacriacao.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((idcelula == null) ? 0 : idcelula.hashCode());
		result = prime * result + ((lideres == null) ? 0 : lideres.hashCode());
		result = prime * result + ((membros == null) ? 0 : membros.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result
				+ ((reunioes == null) ? 0 : reunioes.hashCode());
		result = prime * result
				+ ((subregiao == null) ? 0 : subregiao.hashCode());
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
		Celula other = (Celula) obj;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (celulaorigem == null) {
			if (other.celulaorigem != null)
				return false;
		}
		if (datacriacao == null) {
			if (other.datacriacao != null)
				return false;
		} else if (!datacriacao.equals(other.datacriacao))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idcelula == null) {
			if (other.idcelula != null)
				return false;
		} else if (!idcelula.equals(other.idcelula))
			return false;
		if (lideres == null) {
			if (other.lideres != null)
				return false;
		} else if (!lideres.equals(other.lideres))
			return false;
		if (membros == null) {
			if (other.membros != null)
				return false;
		} else if (!membros.equals(other.membros))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (reunioes == null) {
			if (other.reunioes != null)
				return false;
		} else if (!reunioes.equals(other.reunioes))
			return false;
		if (subregiao == null) {
			if (other.subregiao != null)
				return false;
		} else if (!subregiao.equals(other.subregiao))
			return false;
		return true;
	}
	
	
}