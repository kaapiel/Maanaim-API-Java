package br.com.celula.entidade;

import java.io.Serializable;

public class Cep implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String uf;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getRua() {
		return rua;
	}
	public void setRua(String rua) {
		this.rua = rua;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	@Override
	public String toString(){
		return rua + " - " + bairro + " - " + cep + " - " + cidade + " - " + uf;
		
	}
}
