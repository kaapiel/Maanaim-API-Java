package br.com.celula.negocio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import br.com.celula.dao.RelatorioDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.modelo.Rel02;
import br.com.celula.modelo.Rel03;
import br.com.celula.modelo.Rel04;
import br.com.celula.util.ValidarData;


public class RelatorioLN implements Serializable{

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void geraRelatorioTodosMembro() throws IOException, JRException{
		MembroLN ln = new MembroLN();
		List<Membro> ms = ln.getListMembrosAtivos();
		for(Membro m:ms){
			System.out.println("--------------------------------------------------------------");
			System.out.println(m.getCelula().getSubregiao().getRegiao().getCor());
			for(SubRegiao sr:m.getCelula().getSubregiao().getRegiao().getSubregioes()){
				System.out.println("SubRegiao #### "+sr.getNome());
				for(Celula c:m.getCelula().getSubregiao().getCelulas()){
					System.out.println("Celula ==> "+c.getNome());
					
				}
			}
		}
			
		String nomeRelatorio = "todosmembros.jasper";	
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(ms,false);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));


	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.png"));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=membros.pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

	@SuppressWarnings("unchecked")
	public void geraFichaMembro(Membro m) throws IOException, JRException{

		String nomeRelatorio = "fichademembro.jasper";	
		List<Membro> ms = new ArrayList<Membro>();
		ms.add(m);
		MembroLN ln = new MembroLN();
		ln.recuperaFoto(m);
		ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String arquivo = sContext.getRealPath("/resources/tmp/") + File.separator + m.getIdmembro() +".jpg";
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(ms,false);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));

	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.png"));
	    param.put("foto", arquivo);
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=membros.pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

	@SuppressWarnings("unchecked")
	public void gerafrequenciaPorCelula(Date datainicio, Date datafim) throws IOException, JRException, ParseException{
		RelatorioDao dao = new RelatorioDao();
		String nomeRelatorio = "frequenciaporcelula.jasper";	
		List<Rel04> rs = new ArrayList<Rel04>();
		rs = dao.frequenciaPorCelula(datainicio, datafim);

	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(rs,false);
	    @SuppressWarnings("rawtypes")
	    HashMap param = new HashMap();
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
		   
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("logo", ec.getRealPath("/sistema/img/logo.png"));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=frequenciaporcelula.pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}
	
	@SuppressWarnings("unchecked")
	public void gerafrequenciaDoMembro(Date datainicio, Date datafim,int idadeinicio, 
			int idadefim,double ipercentual,double fpercentual,List<String> regioes) throws IOException, JRException, ParseException{
		RelatorioDao dao = new RelatorioDao();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		MembroLN mln = new MembroLN();
		int totaldememb = mln.totalMembrosAtivos();
		//String nomeRelatorio = "frequenciadomembro.jasper";	
		String nomeRelatorio = "rel02.jasper";
		List<Rel02> rs = new ArrayList<Rel02>();
		Rel02 r = new Rel02();

		rs = dao.frequenciaMembroPorMes(datainicio,datafim,idadeinicio,idadefim,ipercentual,fpercentual,regioes);
		r.setList(rs);
		List<Rel02> rsA = new ArrayList<Rel02>();
		rsA.add(r);
		String nomeGrafico=geraGraficoEvento(ec,rs.size(),totaldememb);
		if(rs.size()==0){
			Rel02 r2 = new Rel02();
			r2.setList(new ArrayList<Rel02>());
			rsA.add(r2);
		}
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(rsA,false);
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	    @SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
	    String periodo="";
	    String faixa="Faixa Et�ria selecionada: de " +idadeinicio + " a " + idadefim + " anos.";
	    String percfreq="% de frequencia selecionado: de " + ipercentual*100 + " a " + fpercentual*100;
	    String totaldemembros = "Quantidade total de membros: " + totaldememb;
	    if(datainicio!=null&datafim!=null){
	    	periodo = "Per�odo selecionado das reuni�es: " 
	    			+ ValidarData.dataParaString(datainicio) + 
	    			" � " + ValidarData.dataParaString(datafim);
	    }else{
	    	periodo ="Per�odo selecionado das reuni�es: Todas Reuni�es" ;
	    }
	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("totaldemembros", totaldemembros);
	    param.put("periodo",periodo);
	    param.put("faixa",faixa);
	    param.put("percfreq",percfreq);
	    param.put("logo", ec.getRealPath("/sistema/img/logo.png"));
	    param.put("grafico", ec.getRealPath("/resources/tmp/"+nomeGrafico));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=frequenciadomembro.pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}
	
   private String geraGraficoEvento(ExternalContext ec,int selecionados,int total) throws IOException{
		String nomearquivo = new Long(System.currentTimeMillis()).toString() + ".png";
		DefaultPieDataset ds = new DefaultPieDataset( );
	    ds.setValue( "Selecionados" , new Double( selecionados ) );  
	    ds.setValue( "N�o Selecionados" , new Double( total-selecionados ) ); 
		JFreeChart grafico = ChartFactory.createPieChart("% Membros Selecionados", ds, true, true, false);
		OutputStream arquivo = new FileOutputStream(ec.getRealPath("/resources/tmp/"+nomearquivo));
		ChartUtils.writeChartAsPNG(arquivo, grafico, 650, 500);
		arquivo.close(); 
		return nomearquivo;
   }

   private String geraGraficoOferta(ExternalContext ec,List<Rel03> rs) throws IOException{
		String nomearquivo = new Long(System.currentTimeMillis()).toString() + ".png";
		DefaultPieDataset ds = new DefaultPieDataset( );
		for(Rel03 r:rs)
			ds.setValue( r.getBairro() , r.getTotaloferta());  

		JFreeChart grafico = ChartFactory.createPieChart("Oferta X Bairro", ds, true, true, false);
		OutputStream arquivo = new FileOutputStream(ec.getRealPath("/resources/tmp/"+nomearquivo));
		ChartUtils.writeChartAsPNG(arquivo, grafico, 650, 500);
		arquivo.close(); 
		return nomearquivo;
   }
	@SuppressWarnings("unchecked")
	public void geraofertaPorBairro(Date datainicio, Date datafim) throws IOException, JRException, ParseException{
		RelatorioDao dao = new RelatorioDao();
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		String nomeRelatorio = "rel03.jasper";
		List<Rel03> rs = new ArrayList<Rel03>();
		Rel03 r = new Rel03();

		rs = dao.ofertaPorBairro(datainicio, datafim);
		r.setList(rs);
		List<Rel03> rsA = new ArrayList<Rel03>();
		rsA.add(r);
		String nomeGrafico=geraGraficoOferta(ec,rs);
		if(rs.size()==0){
			Rel03 r2 = new Rel03();
			r2.setList(new ArrayList<Rel03>());
			rsA.add(r2);
		}
	    JRBeanCollectionDataSource jrds = new JRBeanCollectionDataSource(rsA,false);
	    File reportFile = new File(ec.getRealPath("/sistema/relatorio/" +nomeRelatorio));
	    @SuppressWarnings("rawtypes")
		HashMap param = new HashMap();
	    String periodo="";
	    if(datainicio!=null&datafim!=null){
	    	periodo = "Per�odo selecionado das reuni�es: " 
	    			+ ValidarData.dataParaString(datainicio) + 
	    			" � " + ValidarData.dataParaString(datafim);
	    }else{
	    	periodo ="Per�odo selecionado das reuni�es: Todas Reuni�es" ;
	    }
	    
	    // para resolver o problema da moeda
	    param.put("REPORT_LOCALE", new Locale("pt", "BR"));
	    param.put("periodo",periodo);
	    param.put("logo", ec.getRealPath("/sistema/img/logo.png"));
	    param.put("grafico", ec.getRealPath("/resources/tmp/"+nomeGrafico));
	    
	    HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  	    
	    httpServletResponse.addHeader("Content-disposition", "inline; filename=frequenciadomembro.pdf");  
	    ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
	    JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(),param, jrds); 
	    JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
	    FacesContext.getCurrentInstance().responseComplete();  
	}

}
