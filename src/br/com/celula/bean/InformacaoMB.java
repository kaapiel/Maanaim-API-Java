package br.com.celula.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.RegiaoLN;

@ManagedBean(name="infoMB")
@ViewScoped
public class InformacaoMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Regiao> regioes = new ArrayList<Regiao>();
	private List<SubRegiao> subregioes = new ArrayList<SubRegiao>();
	private List<Celula> celulas = new ArrayList<Celula>();

	@PostConstruct
	public void listarRegioes(){
		regioes = new ArrayList<Regiao>();
		RegiaoLN ln = new RegiaoLN();
		regioes = ln.getListRegioes();
		for(Regiao r:regioes){
			subregioes.addAll(r.getSubregioes());
			for(SubRegiao sr:r.getSubregioes()){
				celulas.addAll(sr.getCelulas());
			}
		}
			
	}

	public int buscaIdade(Membro m){
		LocalDate hoje = new LocalDate();
		LocalDate dataNascimento = new LocalDate(m.getNascimento());
		return Years.yearsBetween(dataNascimento, hoje).getYears();
	}
	
	public List<Regiao> getRegioes() {
		return regioes;
	}

	public void setRegioes(List<Regiao> regioes) {
		this.regioes = regioes;
	}

	public List<SubRegiao> getSubregioes() {
		return subregioes;
	}

	public void setSubregioes(List<SubRegiao> subregioes) {
		this.subregioes = subregioes;
	}

	public List<Celula> getCelulas() {
		return celulas;
	}

	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
