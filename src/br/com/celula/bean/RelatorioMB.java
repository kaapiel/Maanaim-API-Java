package br.com.celula.bean;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.sf.jasperreports.engine.JRException;
import br.com.celula.entidade.Regiao;
import br.com.celula.negocio.RegiaoLN;
import br.com.celula.negocio.RelatorioLN;

@ManagedBean(name="relatorioMB")
@ViewScoped
public class RelatorioMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private Date dataInicial;
	private Date dataFinal;
	private int idadeinicio=0; 
	private int idadefim=0;
	private double ipercentual;
	private double fpercentual;
	private List<String> regioes;
	private List<String> regioesSelecionadas;
	
	public void listarRegioes(){
		regioes = new ArrayList<String>();
		List<Regiao> ls = new ArrayList<Regiao>();
		RegiaoLN ln = new RegiaoLN();
		ls = ln.getListRegioes();
		for(Regiao r:ls)
			regioes.add(r.getCor());
	}
	
	public void imprimirTodosMembrosAtivos(){
		RelatorioLN ln = new RelatorioLN();
		try {
			ln.geraRelatorioTodosMembro();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}


	public void frequenciaPorCelula(){
		RelatorioLN ln = new RelatorioLN();
		try {
			ln.gerafrequenciaPorCelula(dataInicial, dataFinal);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage());
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	
	public void frequenciaDoMembro(){
		RelatorioLN ln = new RelatorioLN();			
		try {
			ln.gerafrequenciaDoMembro(dataInicial, dataFinal, idadeinicio, idadefim, ipercentual/100, fpercentual/100, regioes);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage() + " IOException");
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage() + " JRException");
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage() + " ParseException");
		}
	}

	public void ofertaPorBairro(){
		RelatorioLN ln = new RelatorioLN();			
		try {
			ln.geraofertaPorBairro(dataInicial, dataFinal);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage() + " IOException");
		} catch (JRException e) {
			System.out.println(e.getLocalizedMessage() + " JRException");
		} catch (ParseException e) {
			System.out.println(e.getLocalizedMessage() + " ParseException");
		}
	}

	
	public Date getDataInicial() {
		return dataInicial;
	}


	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}


	public Date getDataFinal() {
		return dataFinal;
	}


	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public int getIdadeinicio() {
		return idadeinicio;
	}


	public void setIdadeinicio(int idadeinicio) {
		this.idadeinicio = idadeinicio;
	}


	public int getIdadefim() {
		return idadefim;
	}


	public void setIdadefim(int idadefim) {
		this.idadefim = idadefim;
	}


	public double getIpercentual() {
		return ipercentual;
	}


	public void setIpercentual(double ipercentual) {
		this.ipercentual = ipercentual;
	}


	public double getFpercentual() {
		return fpercentual;
	}


	public void setFpercentual(double fpercentual) {
		this.fpercentual = fpercentual;
	}


	public List<String> getRegioes() {
		return regioes;
	}


	public void setRegioes(List<String> regioes) {
		this.regioes = regioes;
	}

	public List<String> getRegioesSelecionadas() {
		return regioesSelecionadas;
	}

	public void setRegioesSelecionadas(List<String> regioesSelecionadas) {
		this.regioesSelecionadas = regioesSelecionadas;
	}
	


}
