package br.com.celula.modelo;

import br.com.celula.entidade.Celula;

public class Rel01 {
	private Celula celula;
	private Integer total=0;
	private Integer batizado=0;
	
	public void somaTotal(Integer i){
		total =total+ i;
	}

	public void somaBatizado(Integer i){
		batizado =batizado+ i;
	}
	
	public Celula getCelula() {
		return celula;
	}
	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getBatizado() {
		return batizado;
	}

	public void setBatizado(Integer batizado) {
		this.batizado = batizado;
	}

}
