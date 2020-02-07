package br.com.celula.integracao.get;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.ReuniaoDao;
import br.com.celula.entidade.Reuniao;
import br.com.celula.integracao.model.ReunioesAndroid;

@Path("/getreunioes.maanaimosasco")
public class GetReunioes {
	
	@GET
	@Produces("application/json")
	public List<ReunioesAndroid> getReunioes(){	
		return list();		
	}
	
	private List<ReunioesAndroid> list(){
		List<ReunioesAndroid> list = new ArrayList<ReunioesAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		ReuniaoDao dao = new ReuniaoDao(manager);
		for(Reuniao r: dao.reunioes()){
			ReunioesAndroid x = new ReunioesAndroid();
			x.setIdReuniao(r.getIdreuniao());
			x.setIdCelula(r.getCelula().getIdcelula());
			x.setIdEstado(r.getEstado().getIdestado());
			x.setComplemento(r.getComplemento());
			x.setDataReuniao(r.getDatareuniao());
			x.setHoraReuniao(r.getHorareuniao());
			x.setNumeroLocal(r.getNumero());
			x.setCep(r.getEndereco().getCep());
			list.add(x);
			
		}
		return list;
	}
}
