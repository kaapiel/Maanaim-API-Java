package br.com.celula.integracao.post;
 
/**
 * @author Gabriel Aguido Fraga
 * 
 */
 
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.celula.entidade.Celula;
import br.com.celula.negocio.CelulaLN;

@Path("/postCelulaPorId.maanaimosasco")
public class PostCelulaPorId {

	private EntityManager manager;
	
	@POST
    @Consumes("application/json")
	public Response crunchifyREST(String idDaCelula) {
        
		String json = getCelula(idDaCelula);
		System.out.println(json);
		return Response.status(200).entity(json).build();
    }
    
	public String getCelula(String idDaCelula) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();
		
			CelulaLN cln = new CelulaLN(manager);
			Celula c = cln.getCelula(Integer.valueOf(idDaCelula));
			
			manager.close();
		    factory.close();
		    
		    return cln.celulaToJSON(c);
	}
	
}