package br.com.celula.integracao.post;

/**
 * @author Gabriel Aguido Fraga
 * 
 */

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.celula.integracao.model.AgendamentoPresencaAndroid;
import br.com.celula.negocio.ReuniaoMembroLN;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Path("/presenca/")
public class PostAgPresenca {

	private EntityManager manager;
	private List<AgendamentoPresencaAndroid> apaList = new ArrayList<AgendamentoPresencaAndroid>();
	private String mensagem;

	@POST
	@Path("/postAgPresenca.maanaimosasco")
	@Consumes("application/json")
	public Response crunchifyREST(String dadosRecebidos) {

		System.out.println(dadosRecebidos);

		Gson gs = new Gson();
		JsonParser jp = new JsonParser();
		JsonArray formPresenca = (JsonArray) jp.parse(dadosRecebidos).getAsJsonObject().get("presencaMembros");

		for (JsonElement je : formPresenca) {
			AgendamentoPresencaAndroid apa  = gs.fromJson(je, AgendamentoPresencaAndroid.class);
			apaList.add(apa);
		}

		grava();
		return Response.status(200).entity(dadosRecebidos).build();
	}

	public void grava() {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		ReuniaoMembroLN rmln = new ReuniaoMembroLN(manager);
		System.out.println(rmln.gravar(apaList, manager));

		manager.close();
		factory.close();

		System.out.println("************************  FIM RECEBIMENTO DA PRESENCA DE REUNIAO  *************************");	    
	}
}