package br.com.celula.integracao.get;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.CelulaDao;
import br.com.celula.entidade.Celula;
import br.com.celula.integracao.model.CelulaAndroid;

@Path("/getcelulas.maanaimosasco")
public class GetCelulas {
	
	@GET
	@Produces("application/json; charset=utf-8")
	public List<CelulaAndroid> getCelulas(){		
		return celulas();		
	}
	
	private List<CelulaAndroid> celulas(){
		List<CelulaAndroid> celulas = new ArrayList<CelulaAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		CelulaDao dao = new CelulaDao(manager);
		for(Celula c:dao.celulasAtivas()){
			CelulaAndroid x = new CelulaAndroid();
			x.setIdcelula(c.getIdcelula());
			x.setNome(c.getNome());
			x.setDataCriacao(c.getDatacriacao());
			x.setSubRegiao(c.getSubregiao().getNome());
			x.setRegiao(c.getSubregiao().getRegiao().getCor());
			x.setIdcelulaorigem(c.getCelulaorigem().getIdcelula());
			x.setDescricao(c.getDescricao());
			x.setIdsubregiao(c.getSubregiao().getIdsubregiao());
			x.setAtivo(true);
			x.setDescricao(c.getDescricao());
			x.setIdCelulaOrigem(c.getCelulaorigem().getIdcelula());
			x.setIdsubregiao(c.getSubregiao().getIdsubregiao());
			
			celulas.add(x);
		}
		return celulas;
	}
}
