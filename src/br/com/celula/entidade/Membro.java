package br.com.celula.entidade;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.ForeignKey;

@Entity(name="membro")
@Table(name = "membro")
@XmlRootElement
public class Membro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Id
	private Integer idmembro;
	
	@Column(length=200)
	private String nome="";
	
	@Lob
	private byte[] foto;
	
	@Column(length=30,unique=true,nullable=true)
	private String apelido;
	
	@Column(length=15,unique=true,nullable=true)
	private String rg;  	
	
	@Column(length=15,unique=true,nullable=true)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	private Date dataBatismo;
	
	@Column(length=100,unique=true,nullable=true)
	private String email;
	
	@Column(length=15)
	private String fone_res;
	
	@Column(length=200)
	private String fone_cel;
	
	@Temporal(TemporalType.DATE)
	private Date nascimento;
	
	@Temporal(TemporalType.DATE)
	private Date datacadastro;	
	
	@Column(length=1)
	private String sexo;
	
	@OneToOne
	@JoinColumn(name="cep")
	@ForeignKey(name="FK_endereco_membro")
	private Endereco endereco;
	
	@ManyToOne
	@JoinColumn(name="idcelula")
	@ForeignKey(name="FK_membro_celula")
	private Celula celula = new Celula();
	
	@OneToMany(mappedBy = "membro")
	private List<ReuniaoMembro> celulas;
	
	@Column(length=100,nullable=true)
	private String complemento;
	
	@Column(length=10,nullable=true)
	private String numero;
	
	private Boolean ativo=true;

	public Membro(Integer idmembro){
		this.idmembro = idmembro;
	}
	
	public Membro(){
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
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
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
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Celula getCelula() {
		return celula;
	}
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	public List<ReuniaoMembro> getCelulas() {
		return celulas;
	}
	public void setCelulas(List<ReuniaoMembro> celulas) {
		this.celulas = celulas;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apelido == null) ? 0 : apelido.hashCode());
		result = prime * result + ((ativo == null) ? 0 : ativo.hashCode());
		result = prime * result + ((celula == null) ? 0 : celula.hashCode());
		result = prime * result + ((celulas == null) ? 0 : celulas.hashCode());
		result = prime * result
				+ ((complemento == null) ? 0 : complemento.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result
				+ ((dataBatismo == null) ? 0 : dataBatismo.hashCode());
		result = prime * result
				+ ((datacadastro == null) ? 0 : datacadastro.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result
				+ ((fone_cel == null) ? 0 : fone_cel.hashCode());
		result = prime * result
				+ ((fone_res == null) ? 0 : fone_res.hashCode());
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result
				+ ((idmembro == null) ? 0 : idmembro.hashCode());
		result = prime * result
				+ ((nascimento == null) ? 0 : nascimento.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((rg == null) ? 0 : rg.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
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
		Membro other = (Membro) obj;
		if (apelido == null) {
			if (other.apelido != null)
				return false;
		} else if (!apelido.equals(other.apelido))
			return false;
		if (ativo == null) {
			if (other.ativo != null)
				return false;
		} else if (!ativo.equals(other.ativo))
			return false;
		if (celula == null) {
			if (other.celula != null)
				return false;
		} else if (!celula.equals(other.celula))
			return false;
		if (celulas == null) {
			if (other.celulas != null)
				return false;
		} else if (!celulas.equals(other.celulas))
			return false;
		if (complemento == null) {
			if (other.complemento != null)
				return false;
		} else if (!complemento.equals(other.complemento))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataBatismo == null) {
			if (other.dataBatismo != null)
				return false;
		} else if (!dataBatismo.equals(other.dataBatismo))
			return false;
		if (datacadastro == null) {
			if (other.datacadastro != null)
				return false;
		} else if (!datacadastro.equals(other.datacadastro))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (fone_cel == null) {
			if (other.fone_cel != null)
				return false;
		} else if (!fone_cel.equals(other.fone_cel))
			return false;
		if (fone_res == null) {
			if (other.fone_res != null)
				return false;
		} else if (!fone_res.equals(other.fone_res))
			return false;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (idmembro == null) {
			if (other.idmembro != null)
				return false;
		} else if (!idmembro.equals(other.idmembro))
			return false;
		if (nascimento == null) {
			if (other.nascimento != null)
				return false;
		} else if (!nascimento.equals(other.nascimento))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (rg == null) {
			if (other.rg != null)
				return false;
		} else if (!rg.equals(other.rg))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		return true;
	}	
   
 }