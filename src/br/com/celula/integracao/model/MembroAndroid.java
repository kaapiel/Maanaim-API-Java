package br.com.celula.integracao.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MembroAndroid {
	
	private Integer idmembro;
	private String nome="";
	private String foto;
	private String apelido;
	private String rg;  	
	private String cpf;
	private Date dataBatismo;
	private String email;
	private String fone_res;
	private String fone_cel;
	private Date nascimento;
	private Date datacadastro;	
	private String sexo;

	private String cep;
	private String logradouro;	
	private String bairro;	
	private String cidade;	
	private String uf;
	
	private Integer idcelula;
	private String complemento;
	private String numero;
	private Boolean ativo=true;
	
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
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
	
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getApelido() {
		return apelido;
	}
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Date getDataBatismo() {
		return dataBatismo;
	}
	public void setDataBatismo(Date dataBatismo) {
		this.dataBatismo = dataBatismo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFone_res() {
		return fone_res;
	}
	public void setFone_res(String fone_res) {
		this.fone_res = fone_res;
	}
	public String getFone_cel() {
		return fone_cel;
	}
	public void setFone_cel(String fone_cel) {
		this.fone_cel = fone_cel;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	public Date getDatacadastro() {
		return datacadastro;
	}
	public void setDatacadastro(Date datacadastro) {
		this.datacadastro = datacadastro;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	
	public Integer getIdcelula() {
		return idcelula;
	}
	public void setIdcelula(Integer idcelula) {
		this.idcelula = idcelula;
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
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	

}
