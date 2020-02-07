package br.com.celula.integracao.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.celula.entidade.EstadosReuniao;
@XmlRootElement
public class AgendamentoAndroid {
	
	private Integer idCelula;
	private String data, hora, logradouro, cidade, estado, cep, bairro, numero, descricao;
	private BigDecimal oferta;
	private EstadosReuniao er;
	private int criancas, visitantes;
	
	public int getCriancas() {
		return criancas;
	}
	public void setCriancas(int criancas) {
		this.criancas = criancas;
	}
	public int getVisitantes() {
		return visitantes;
	}
	public void setVisitantes(int visitantes) {
		this.visitantes = visitantes;
	}
	public BigDecimal getOferta() {
		return oferta;
	}
	public void setOferta(BigDecimal oferta) {
		this.oferta = oferta;
	}
	public EstadosReuniao getEr() {
		return er;
	}
	public void setEr(EstadosReuniao er) {
		this.er = er;
	}
	public Integer getIdCelula() {
		return idCelula;
	}
	public void setIdCelula(Integer idCelula) {
		this.idCelula = idCelula;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCepJson() {
		return cep;
	}
	public void setCepJson(String cepJson) {
		this.cep = cepJson;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return idCelula+" "+data+" "+hora+" "+logradouro+" "+cidade+" "+estado+" "+cep+" "+bairro+" "+numero+" "+descricao;
	}
	
}
