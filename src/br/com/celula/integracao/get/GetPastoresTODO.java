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
import br.com.celula.entidade.Membro;
import br.com.celula.integracao.model.LiderAndroid;

@Path("/getpastores.maanaimosasco")
public class GetPastoresTODO {
	
	@GET
	@Produces("application/json")
	public List<LiderAndroid> getLideres(){	
		return list();		
	}
	
	private List<LiderAndroid> list(){
		List<LiderAndroid> list = new ArrayList<LiderAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		CelulaDao dao = new CelulaDao(manager);
		for(Celula c:dao.celulasAtivas()){
			for(Membro m:c.getLideres()){
				LiderAndroid x = new LiderAndroid();
				x.setIdcelula(c.getIdcelula());
				x.setIdmembro(m.getIdmembro());
				list.add(x);
			}
		}
		return list;
	}
}
