package br.com.celula.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.joda.time.LocalDate;

import br.com.celula.conexao.EttMnger;
import br.com.celula.entidade.ConsA;
import br.com.celula.modelo.Rel02;
import br.com.celula.modelo.Rel03;
import br.com.celula.modelo.Rel04;

/**
*
* @author MAURICIO CRUZ
*/
public class RelatorioDao {
	
	private EntityManager manager;
	
	public RelatorioDao(){
		this.manager = EttMnger.manager();
	}

	public RelatorioDao(EntityManager m){
		this.manager = m;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<ConsA>  frequenciaPorMes(int mes, int ano) throws ParseException {	
		List<ConsA> cons = new ArrayList<ConsA>();
		LocalDate dt = new LocalDate(ano, mes, 01);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" c.nome,r.datareuniao,");
		sb.append("count(m.idmembro),");
		sb.append(" SUM(case when rm.estevepresente=1 Then 1 Else 0 End),");
		sb.append(" SUM(case when rm.estevepresente=0 Then 1 Else 0 End)");
		sb.append(" FROM reuniao_membro rm");
		sb.append(" join rm.reuniao r");
		sb.append(" join rm.membro m");
		sb.append(" join m.celula c");
		sb.append(" WHERE");
		sb.append(" r.datareuniao between :inicio and :fim");
		sb.append(" group by c.idcelula,r.idreuniao");
		List<Object[]>  rs=  this.manager.createQuery(sb.toString()) 
	    				.setParameter("inicio", dt.toDate(),TemporalType.DATE)
	    				.setParameter("fim", dt.dayOfMonth().withMaximumValue().toDate(),TemporalType.DATE)
	    				.getResultList();
		for(Object[] s:rs){
			ConsA c = new ConsA();
			c.setCelula((String)s[0]); //nome celula
			c.setData((Date)s[1]);  //data
			c.setTotal((long)s[2]); //total
			c.setPresente((long)s[3]); //prese
			c.setAusente((long)s[4]); //ausente
			cons.add(c);
		}
		return cons;
	}  

	
	@SuppressWarnings({ "unchecked" })
	public List<Rel02>  frequenciaMembroPorMes(Date datainicio, Date datafim,int idadeinicio, 
			int idadefim,double ipercentual,double fpercentual,List<String> regioes) throws ParseException {	
		List<Rel02> cons = new ArrayList<Rel02>();
 
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m.nome as membro,c.nome as celula,sr.nome as subregiao,rg.cor as regiao, ");
		sb.append("(SELECT Sum(frm.estevepresente = 1) / Count(frm.idmembro) FROM  reuniao_membro frm ");
		sb.append("WHERE frm.idmembro = rm.idmembro GROUP BY frm.idmembro) AS frequencia ");
		sb.append("FROM membro m INNER JOIN reuniao_membro rm ON ( m.idmembro = rm.idmembro ) ");
		sb.append("INNER JOIN reuniao r ON ( r.idreuniao = rm.idreuniao ) INNER JOIN celula c ON ( c.idcelula = r.idcelula ) ");
		sb.append("INNER JOIN subregiao sr ON ( sr.idsubregiao = c.idsubregiao ) INNER JOIN regiao rg ON ( rg.idregiao = sr.idregiao ) ");
		sb.append("WHERE ");
		if(datainicio!=null&&datafim!=null)
			sb.append("r.datareuniao BETWEEN :datainicio and :datafim and ");
		sb.append("( Year(Now()) - Year(m.nascimento) ) BETWEEN :idadeinicio and :idadefim and ");
		sb.append("(SELECT Sum(frm.estevepresente = 1) / Count(frm.idmembro) FROM reuniao_membro frm WHERE ");
		sb.append("frm.idmembro = rm.idmembro GROUP BY frm.idmembro) BETWEEN :ipercentual and :fpercentual ");
		if(regioes.size()!=0)
			sb.append("and rg.cor in ( :regioes ) ");
		sb.append("GROUP BY m.idmembro ORDER BY c.nome,m.nome");
		Query q = manager.createNativeQuery(sb.toString());		
		if(datainicio!=null&&datafim!=null)
			q.setParameter("datainicio", datainicio, TemporalType.DATE).setParameter("datafim", datafim, TemporalType.DATE);
		q.setParameter("idadeinicio", idadeinicio).setParameter("idadefim", idadefim).setParameter("ipercentual", ipercentual);
		if(regioes.size()!=0)
			q.setParameter("regioes", regioes);
		List<Object[]> rs=q.setParameter("fpercentual", fpercentual).getResultList();
		for(Object[] s:rs){
			Rel02 r = new Rel02();
			r.setMembro((String)s[0]); 
			r.setCelula((String)s[1]); 
			r.setSubregiao((String)s[2]); 
			r.setRegiao((String)s[3]); 
			r.setFrequencia(((BigDecimal) s[4]).multiply(new BigDecimal(100)));
			cons.add(r);
		}		
		return cons;		
	}  

	@SuppressWarnings({ "unchecked" })
	public List<Rel03>  ofertaPorBairro(Date datainicio, Date datafim) throws ParseException {	
		
		List<Rel03> cons = new ArrayList<Rel03>();
 		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT e.bairro,sum(rm.oferta) as ofertatotal,count(rm.idmembro) as totaldemembros ");
		sb.append("FROM endereco e inner join reuniao r on (r.cep=e.cep) ");
		sb.append("inner join reuniao_membro rm on (r.idreuniao=rm.idreuniao) ");
		if(datainicio!=null&&datafim!=null){
			sb.append("WHERE ");
			sb.append("r.datareuniao BETWEEN :datainicio and :datafim ");
		}
		sb.append("GROUP BY e.bairro");
		Query q = manager.createNativeQuery(sb.toString());		
		if(datainicio!=null&&datafim!=null)
			q.setParameter("datainicio", datainicio, TemporalType.DATE).setParameter("datafim", datafim, TemporalType.DATE);
		List<Object[]> rs=q.getResultList();
		for(Object[] s:rs){
			Rel03 r = new Rel03();
			r.setBairro((String)s[0]); 
			r.setTotaloferta((BigDecimal) s[1]);
			r.setTotalmembros((BigInteger)s[2]);  
			cons.add(r);
		}		
		return cons;		
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Rel04>  frequenciaPorCelula(Date datainicio, Date datafim) throws ParseException {	
		
		List<Rel04> cons = new ArrayList<Rel04>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT count(m.idmembro) as totalmembros, SUM(case when rm.estevepresente=1 Then 1 Else 0 End), ");
		sb.append("SUM(case when rm.estevepresente=1 Then 1 Else 0 End)*100 / count(m.idmembro),c.nome FROM membro m ");
		sb.append("inner join reuniao_membro rm on (rm.idmembro=m.idmembro) inner join reuniao r on (r.idreuniao=rm.idreuniao) inner join celula c on (m.idcelula=c.idcelula)");
		if(datainicio!=null&&datafim!=null){
			sb.append("WHERE ");
			sb.append("r.datareuniao BETWEEN :datainicio and :datafim ");
		}
		sb.append("GROUP BY c.idcelula");
		Query q = manager.createNativeQuery(sb.toString());		
		if(datainicio!=null&&datafim!=null)
			q.setParameter("datainicio", datainicio, TemporalType.DATE).setParameter("datafim", datafim, TemporalType.DATE);
		List<Object[]> rs=q.getResultList();
		for(Object[] s:rs){
			Rel04 r = new Rel04();
			r.setTotalmembros((BigInteger)s[0]);  
			r.setTotalmembrospresente((BigDecimal)s[1]);  
			r.setPercentual((BigDecimal) s[2]);
			r.setCelula((String)s[3]); 
			cons.add(r);
		}		
		return cons;		
	}
	
}
