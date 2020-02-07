package br.com.celula.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.chart.PieChartModel;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.modelo.Rel01;
import br.com.celula.negocio.CelulaLN;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.RegiaoLN;
import br.com.celula.negocio.SubRegiaoLN;


@ManagedBean(name="relcelulaMB")
@ViewScoped
public class RelCelulaMB implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Celula>  filhas = new ArrayList<Celula>();
	private TreeNode root;
	private List<Celula> celulas = new ArrayList<Celula>();
	private List<Celula> celulastmp = new ArrayList<Celula>();
	private CelulaLN celulaLN;
	private Celula pai = new Celula();
	private Map<Celula,List<Membro>> membroscelula = new HashMap<>();
	private List<Membro> membros = new ArrayList<Membro>();
	private List<Membro> membrosSelecionados = new ArrayList<Membro>();
	private int faixaInicial=0;
	private int faixaFinal=0;
	private Date dataInicial;
	private Date dataFinal;
	private List<Rel01> ts = new ArrayList<Rel01>();
	private PieChartModel pieModel;
	private Integer universo=0;
	private Integer totalfaixa=0;
	private Integer totalbatizados=0;
	private List<Regiao> regioes = new ArrayList<Regiao>();
	private List<SubRegiao> subregioes = new ArrayList<SubRegiao>();
	private List<Regiao> regioesSelecionadas = new ArrayList<Regiao>();
	private List<SubRegiao> subregioesSelecionadas = new ArrayList<SubRegiao>();	
	private List<Regiao> regioestmp = new ArrayList<Regiao>();
	private List<SubRegiao> subregioestmp = new ArrayList<SubRegiao>();
	private boolean todasCelulas=true;
	private boolean batizados=false;
	private Regiao regiao = new Regiao();
	private SubRegiao subregiao = new SubRegiao();	
	
	@PostConstruct
	public void vai(){
		listar();	
		createPieModel();
	}
	
    private void createPieModel() {
        pieModel = new PieChartModel();
         
        if(batizados){
        	pieModel.set("Selecionados ", totalbatizados);
	        pieModel.set("Demais Membro", universo-totalbatizados);
        }else{
        	pieModel.set("Selecionados ", totalfaixa);
	        pieModel.set("Demais Membro", universo-totalfaixa);
        }
        
        pieModel.setTitle("Percentual de Membros");
        pieModel.setLegendPosition("e");
        pieModel.setFill(false);
        pieModel.setShowDataLabels(true);
        pieModel.setDiameter(100);
    }
    
	public void relFaixaEtaria(){
		ts=new ArrayList<Rel01>();
		totalbatizados=0;
		totalfaixa=0;
		for(Celula c:celulas){
			Rel01 r = new Rel01();
			r.setCelula(c);
			for(Membro m: c.getMembros()){				
				if(estaNaFaixaEtaria(faixaInicial, faixaFinal, m)){
					r.somaTotal(1);	
					totalfaixa = totalfaixa+1;
					if(m.getDataBatismo()!=null){
						r.setBatizado(1);	
						totalbatizados = totalbatizados+1;
					}else{
						r.setBatizado(0);
					}
				}else{
					r.somaTotal(0);	
				}
			}
			somaTotalFaixa(r.getTotal());
			ts.add(r);
			createPieModel();			
		}
		System.out.println(totalbatizados);
		System.out.println(totalfaixa);
		for(Rel01 r:ts){
			System.out.println(r.getBatizado());
			System.out.println(r.getTotal());			
		}
			
	}



	public void filtrar(){		
		totalfaixa =0;
		totalbatizados=0;
		celulas=new ArrayList<Celula>();
		if(todasCelulas){
			celulas=celulastmp;
		}else{
			if(regioesSelecionadas.size()>0){
				if(subregioesSelecionadas.size()>0){
					for(SubRegiao sr:subregioesSelecionadas)
						for(Celula c:sr.getCelulas())
							celulas.add(c);
				}else{
					for(Regiao r:regioesSelecionadas)
						for(SubRegiao sr:r.getSubregioes())
							for(Celula c:sr.getCelulas())
								celulas.add(c);
				}
			}else{
				celulas=celulastmp;
			}
		}
		celulas=celulastmp;
		relFaixaEtaria();
	}
	

	
	public boolean estaNaFaixaEtaria(Integer inicio, Integer fim, Membro m){
		LocalDate d = null;
		LocalDate hoje = new LocalDate();
		int i=0;
		if(m.getNascimento()!=null){
			d = new LocalDate(m.getNascimento());
			i = Years.yearsBetween(d, hoje).getYears();
			if(i>=inicio && i<=fim)
				return true;
		}
		return false;
	}
 
	public void listar(){
		celulaLN = new CelulaLN();
		RegiaoLN rln = new RegiaoLN();
		SubRegiaoLN srln = new SubRegiaoLN();
		MembroLN mln = new MembroLN();
		celulastmp =celulaLN.getList();
		regioestmp = rln.getListRegioes();
		subregioestmp = srln.getListSubRegioes();
		universo=mln.totalMembrosAtivos();
	}

	public void listarRegiaoESubRegiao(){

	}

	public void atualizaFiltro(){
		if(todasCelulas){
			regioes = new ArrayList<Regiao>();
			subregioes = new ArrayList<SubRegiao>();
			regiao = new Regiao();
			subregiao = new SubRegiao();
		}else{
			regioes = regioestmp;
		}
	}
	
	public void carregaSubRegiores(){
		subregioes = new ArrayList<SubRegiao>();
		for(Regiao r: regioesSelecionadas)
			subregioes.addAll(r.getSubregioes());
	}
	
	public void limparFiltro(){
		regioes = new ArrayList<Regiao>();
		regioesSelecionadas = new ArrayList<Regiao>();
		subregioes = new ArrayList<SubRegiao>();
		subregioesSelecionadas = new ArrayList<SubRegiao>();
		regiao = new Regiao();
		subregiao = new SubRegiao();
		dataFinal = null;
		dataInicial = null;
		faixaFinal=0;
		faixaInicial=0;
	}
	
	public void hierarquia(){		
		for(Celula s:celulas)//identifica a root
			if(s.getIdcelula().equals(s.getCelulaorigem().getIdcelula()))
				pai = s;
			else
				filhas.add(s);
		root = new DefaultTreeNode("ORIGEM DAS CELULAS", null);
		List<Celula>  rs = celulas;
		ordenaLista(rs);
		
		rs.set(0, pai);
		
		
		TreeNode x = new DefaultTreeNode(rs.get(0).getNome(), root);
		recursive(rs, rs.get(0).getIdcelula(), x);
		//ordenaLista(celulas);
		for(Celula c:celulas){
			List<Membro> mbs = new ArrayList<Membro>();
			for(Membro m:membros)
				if(m.getCelula().getIdcelula().equals(c.getIdcelula()))
					mbs.add(m);
			membroscelula.put(c, mbs);			
		}
	}
	
	public void recursive(List<Celula> list, int id,TreeNode node){
		   List<Celula> subList2=new ArrayList<Celula>();
		   subList2=subPlano(id);
		   for(Celula c:subList2){
		     TreeNode childNode=new DefaultTreeNode(c.getNome(), node);
		     childNode.setExpanded(true);
		     recursive(subList2, c.getIdcelula(),childNode);
		   }
	}
		   
	 public List<Celula> subPlano(long i){
	 	List<Celula> newList=new ArrayList<Celula>();
	     for(Celula c:filhas)
	         if(c.getCelulaorigem().getIdcelula()==i)
	             newList.add(c);
	     return newList;
	 }

	public List<Celula> ordenaLista(List<Celula> list){
		Collections.sort(list, new Comparator<Celula>() {
	        @Override
	        public int compare(Celula  c1, Celula  c2)
	        {
	            return  c1.getIdcelula().compareTo(c2.getIdcelula());
	        }
	    });
		return list;
	}

	public void somaBatizados(Integer i){
		totalbatizados =totalbatizados+ i;
	}
	
	public void somaUniverso(Integer i){
		universo =universo+ i;
	}

	public void somaTotalFaixa(Integer i){
		totalfaixa =totalfaixa+ i;
	}	

	
	
	public List<Celula> getCelulas() {
		return celulas;
	}

	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Celula getPai() {
		return pai;
	}

	public void setPai(Celula pai) {
		this.pai = pai;
	}

	public List<Celula> getFilhas() {
		return filhas;
	}

	public void setFilhas(List<Celula> filhas) {
		this.filhas = filhas;
	}

	public CelulaLN getCelulaLN() {
		return celulaLN;
	}

	public void setCelulaLN(CelulaLN celulaLN) {
		this.celulaLN = celulaLN;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<Celula,List<Membro>> getMembroscelula() {
		return membroscelula;
	}

	public void setMembroscelula(Map<Celula,List<Membro>> membroscelula) {
		this.membroscelula = membroscelula;
	}

	public List<Membro> getMembros() {
		return membros;
	}

	public void setMembros(List<Membro> membros) {
		this.membros = membros;
	}

	public int getFaixaInicial() {
		return faixaInicial;
	}

	public void setFaixaInicial(int faixaInicial) {
		this.faixaInicial = faixaInicial;
	}

	public int getFaixaFinal() {
		return faixaFinal;
	}

	public void setFaixaFinal(int faixaFinal) {
		this.faixaFinal = faixaFinal;
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
	public List<Rel01> getTs() {
		return ts;
	}

	public void setTs(List<Rel01> ts) {
		this.ts = ts;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public void setPieModel(PieChartModel pieModel) {
		this.pieModel = pieModel;
	}
	public Integer getUniverso() {
		return universo;
	}
	public void setUniverso(Integer universo) {
		this.universo = universo;
	}
	public Integer getTotalfaixa() {
		return totalfaixa;
	}
	public void setTotalfaixa(Integer totalfaixa) {
		this.totalfaixa = totalfaixa;
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
	public boolean isTodasCelulas() {
		return todasCelulas;
	}
	public void setTodasCelulas(boolean todasCelulas) {
		this.todasCelulas = todasCelulas;
	}
	public Regiao getRegiao() {
		return regiao;
	}
	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}
	public SubRegiao getSubregiao() {
		return subregiao;
	}
	public void setSubregiao(SubRegiao subregiao) {
		this.subregiao = subregiao;
	}
	public List<Regiao> getRegioestmp() {
		return regioestmp;
	}
	public void setRegioestmp(List<Regiao> regioestmp) {
		this.regioestmp = regioestmp;
	}
	public List<SubRegiao> getSubregioestmp() {
		return subregioestmp;
	}
	public void setSubregioestmp(List<SubRegiao> subregioestmp) {
		this.subregioestmp = subregioestmp;
	}
	public List<Regiao> getRegioesSelecionadas() {
		return regioesSelecionadas;
	}
	public void setRegioesSelecionadas(List<Regiao> regioesSelecionadas) {
		this.regioesSelecionadas = regioesSelecionadas;
	}
	public List<SubRegiao> getSubregioesSelecionadas() {
		return subregioesSelecionadas;
	}
	public void setSubregioesSelecionadas(List<SubRegiao> subregioesSelecionadas) {
		this.subregioesSelecionadas = subregioesSelecionadas;
	}
	public List<Celula> getCelulastmp() {
		return celulastmp;
	}
	public void setCelulastmp(List<Celula> celulastmp) {
		this.celulastmp = celulastmp;
	}

	public boolean isBatizados() {
		return batizados;
	}

	public void setBatizados(boolean batizados) {
		this.batizados = batizados;
	}

	public Integer getTotalbatizados() {
		return totalbatizados;
	}

	public void setTotalbatizados(Integer totalbatizados) {
		this.totalbatizados = totalbatizados;
	}

	public List<Membro> getMembrosSelecionados() {
		return membrosSelecionados;
	}

	public void setMembrosSelecionados(List<Membro> membrosSelecionados) {
		this.membrosSelecionados = membrosSelecionados;
	}

}
