package br.com.celula.integracao.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.axis.encoding.Base64;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.AvisosDao;
import br.com.celula.entidade.Avisos;
import br.com.celula.integracao.model.AvisosAndroid;

@Path("/getavisos.maanaimosasco")
public class GetAvisos {
	
	@GET
	@Produces("application/json; charset=utf-8")
	public List<AvisosAndroid> getAvisos(){		
		return avisos();		
	}
	
	private List<AvisosAndroid> avisos(){
		List<AvisosAndroid> avisos = new ArrayList<AvisosAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		AvisosDao dao = new AvisosDao(manager);
		Date dataHoje = new Date();
		for(Avisos a:dao.getAvisos()){
			AvisosAndroid x = new AvisosAndroid();
			x.setId(a.getIdaviso());
			x.setDataPublicacao(dataHoje);
			x.setDataEvento(a.getDataEvento());
			
			if(a.getImagemEvento() != null){
				String encode = Base64.encode(a.getImagemEvento());
				x.setImagemEvento(encode);
			}
			
			x.setTitulo(a.getTitulo());
			
			avisos.add(x);
		}
		return avisos;
	}
}
