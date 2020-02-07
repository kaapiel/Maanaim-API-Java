package br.com.celula.integracao.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CelulaAndroid {
	
	private Integer idcelula;
	private String nome;
	private String descricao;
	private Date dataCriacao;
	private Integer idcelulaorigem;
	private Integer idsubregiao;
	private String regiao;
	private String subRegiao;
	private Integer idCelulaOrigem;
	private boolean ativo;


	public Integer getIdsubregiao() {
		return idsubregiao;
	}
	public void setIdsubregiao(Integer idsubregiao) {
		this.idsubregiao = idsubregiao;
	}
	
	public Integer getIdCelulaOrigem() {
		return idCelulaOrigem;
	}
	public void setIdCelulaOrigem(Integer idCelulaOrigem) {
		this.idCelulaOrigem = idCelulaOrigem;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getIdcelulaorigem() {
		return idcelulaorigem;
	}
	public void setIdcelulaorigem(Integer idcelulaorigem) {
		this.idcelulaorigem = idcelulaorigem;
	}
	
	public String getRegiao() {
		return regiao;
	}
	public void setRegiao(String regiao) {
		this.regiao = regiao;
	}
	public String getSubRegiao() {
		return subRegiao;
	}
	public void setSubRegiao(String subRegiao) {
		this.subRegiao = subRegiao;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
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
	
}
