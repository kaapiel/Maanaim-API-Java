package br.com.celula.integracao.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioAndroid {
	private Integer idusuario;
	private Integer idmembro;
	private String nome;
	private String usuario;
	private String senha;

	public Integer getIdmembro() {
		return idmembro;
	}
	public void setIdmembro(Integer idmembro) {
		this.idmembro = idmembro;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Integer getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
