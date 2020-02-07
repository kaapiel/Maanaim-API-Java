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

import br.com.celula.entidade.Membro;
import br.com.celula.negocio.MembroLN;

@Path("/postMembroPorId.maanaimosasco")
public class PostMembroPorId {

	private EntityManager manager;
	
	@POST
    @Consumes("application/json")
	public Response crunchifyREST(String idDoMembro) {
        
		String json = getMembro(idDoMembro);
		System.out.println(json);
		return Response.status(200).entity(json).build();
    }
    
	public String getMembro(String idDoMembro) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();
		
			MembroLN mln = new MembroLN(manager);
			Membro m = mln.getMembro(Integer.valueOf(idDoMembro));
			
			manager.close();
		    factory.close();
		    
		    return mln.membroToJSON(m);
	}
	
}