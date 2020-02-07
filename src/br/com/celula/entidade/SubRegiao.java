package br.com.celula.entidade;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity(name = "subregiao")
@Table(name = "subregiao")
public class SubRegiao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idsubregiao;	
	
	@Column(length=100,unique=true)
	private String nome;
	
	@Column(length=300)
	private String descricao;	
	
	@ManyToOne(targetEntity=Regiao.class)
	@JoinColumn(name="idregiao")
	private Regiao regiao;	
	
	@OneToMany(mappedBy = "subregiao", targetEntity = Celula.class,cascade=CascadeType.MERGE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ForeignKey(name="FK_subregiao_celula")
	private List<Celula> celulas;
    
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="supervisores", joinColumns={@JoinColumn(name="idsubregiao")}, inverseJoinColumns={@JoinColumn(name="idmembro")})
    @ForeignKey(name="FK_subregiao_membro")
    @Size(min = 0, max = 2)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Membro> supervisores;

	
	public Integer getIdsubregiao() {
		return idsubregiao;
	}
	public void setIdsubregiao(Integer idsubregiao) {
		this.idsubregiao = idsubregiao;
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
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public List<Celula> getCelulas() {
		return celulas;
	}
	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}
	public List<Membro> getSupervisores() {
		return supervisores;
	}
	public void setSupervisores(List<Membro> supervisores) {
		this.supervisores = supervisores;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((celulas == null) ? 0 : celulas.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((idsubregiao == null) ? 0 : idsubregiao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((regiao == null) ? 0 : regiao.hashCode());
		result = prime * result
				+ ((supervisores == null) ? 0 : supervisores.hashCode());
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
		SubRegiao other = (SubRegiao) obj;
		if (celulas == null) {
			if (other.celulas != null)
				return false;
		} else if (!celulas.equals(other.celulas))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idsubregiao == null) {
			if (other.idsubregiao != null)
				return false;
		} else if (!idsubregiao.equals(other.idsubregiao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (regiao == null) {
			if (other.regiao != null)
				return false;
		} else if (!regiao.equals(other.regiao))
			return false;
		if (supervisores == null) {
			if (other.supervisores != null)
				return false;
		} else if (!supervisores.equals(other.supervisores))
			return false;
		return true;
	}	
    
}
