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

import br.com.celula.dao.ReuniaoDao;
import br.com.celula.dao.ReuniaoMembroDao;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Reuniao;
import br.com.celula.negocio.MembroLN;
import br.com.celula.negocio.ReuniaoLN;

@Path("/postReuniaoPorId.maanaimosasco")
public class PostReuniaoPorId {

	private EntityManager manager;

	@POST
	@Consumes("application/json")
	public Response crunchifyREST(String idDaReuniao) {

		String json = getReuniao(idDaReuniao);
		System.out.println(json);
		return Response.status(200).entity(json).build();
	}

	public String getReuniao(String idDaReuniao) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		Reuniao r = new Reuniao();
		r.setIdreuniao(Integer.valueOf(idDaReuniao));
		new ReuniaoDao().reuniao(r);

		manager.close();
		factory.close();

		return new ReuniaoLN().reuniaoToJSON(r);
	}

}