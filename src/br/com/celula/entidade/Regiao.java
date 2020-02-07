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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity(name = "regiao")
@Table(name = "regiao")
public class Regiao implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@GeneratedValue
	@Id
	private Integer idregiao;
	
	@Column(length=300)
	private String descricao="";
	
	@Column(length=20,unique=true)
	private String cor="";	
	
	@OneToMany(mappedBy = "regiao", targetEntity = SubRegiao.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	@ForeignKey(name="FK_regiao_subregiao")
	private List<SubRegiao> subregioes;
    
	@ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="pastores", joinColumns={@JoinColumn(name="idregiao")}, inverseJoinColumns={@JoinColumn(name="idmembro")})
    @ForeignKey(name="FK_regiao_membro")
    @Size(min = 0, max = 2)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Membro> pastores;
    
    
    public Integer getIdregiao() {
		return idregiao;
	}
	public void setIdregiao(Integer idregiao) {
		this.idregiao = idregiao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCor() {
		return cor;
	}
	public void setCor(String cor) {
		this.cor = cor;
	}
	public List<SubRegiao> getSubregioes() {
		return subregioes;
	}
	public void setSubregioes(List<SubRegiao> subregioes) {
		this.subregioes = subregioes;
	}
	public List<Membro> getPastores() {
		return pastores;
	}
	public void setPastores(List<Membro> pastores) {
		this.pastores = pastores;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cor == null) ? 0 : cor.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result
				+ ((idregiao == null) ? 0 : idregiao.hashCode());
		result = prime * result
				+ ((pastores == null) ? 0 : pastores.hashCode());
		result = prime * result
				+ ((subregioes == null) ? 0 : subregioes.hashCode());
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
		Regiao other = (Regiao) obj;
		if (cor == null) {
			if (other.cor != null)
				return false;
		} else if (!cor.equals(other.cor))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (idregiao == null) {
			if (other.idregiao != null)
				return false;
		} else if (!idregiao.equals(other.idregiao))
			return false;
		if (pastores == null) {
			if (other.pastores != null)
				return false;
		} else if (!pastores.equals(other.pastores))
			return false;
		if (subregioes == null) {
			if (other.subregioes != null)
				return false;
		} else if (!subregioes.equals(other.subregioes))
			return false;
		return true;
	}
	
}
