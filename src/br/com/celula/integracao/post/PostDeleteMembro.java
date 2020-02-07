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

@Path("/postDeleteMembro.maanaimosasco")
public class PostDeleteMembro {

	private EntityManager manager;
	
	@POST
    @Consumes("application/json")
	public Response crunchifyREST(String idDoMembro) {
        
		System.out.println("id do cara que sera deletado: "+ idDoMembro);
		deletarMembro(idDoMembro);
		
		return Response.status(200).entity(idDoMembro).build();
    }
    
	public void deletarMembro(String idDoMembro) {
		
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();
		
			MembroLN mln = new MembroLN(manager);
			Membro membro = new Membro();
			membro.setIdmembro(Integer.valueOf(idDoMembro));
			mln.removeMembro(idDoMembro, manager, membro);
			
			manager.close();
		    factory.close();
	}
	
}