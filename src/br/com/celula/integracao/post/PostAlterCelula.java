package br.com.celula.integracao.post;

/**
 * @author Gabriel Aguido Fraga
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.celula.entidade.Celula;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.CelulaLN;
import br.com.celula.negocio.SubRegiaoLN;

@Path("/postAlterCelula.maanaimosasco")
public class PostAlterCelula {

	private EntityManager manager;
	private JSONObject jsonObject;
	private CelulaLN mln;

	@POST
	@Consumes("application/json")
	public Response crunchifyREST(String jsonCelula) throws JSONException, ParseException {

		System.out.println(jsonCelula);
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		Celula c = formatCelula(jsonCelula, manager);
		CelulaLN mln = new CelulaLN(manager);
		String mensagem = mln.atualizaCelulaAndroid(c);
		System.out.println(mensagem);
		
		manager.close();
		factory.close();

		return Response.status(200).entity(mensagem).build();
	}

	public Celula formatCelula(String json, EntityManager em) throws JSONException, ParseException{

		jsonObject = new JSONObject(json);
		Celula c = new Celula();
		CelulaLN cln = new CelulaLN(em);
		Celula co = cln.getCelula(Integer.valueOf(tryCatchField("idcelulaorigem")));
		SubRegiaoLN srln = new SubRegiaoLN(em);
		SubRegiao sr = srln.getSubRegiao(Integer.valueOf(tryCatchField("idsubregiao")), em);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-03:00'");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-02:00'");
		c.setIdcelula(Integer.valueOf(tryCatchField("idcelula")));
		c.setAtivo(Boolean.valueOf(tryCatchField("ativo")));
		
		try{
		c.setDatacriacao(formatter.parse(tryCatchField("datacriacao")));
		}catch (Exception e){
			c.setDatacriacao(formatter2.parse(tryCatchField("datacriacao")));
		}
		
		c.setDescricao(tryCatchField("descricao"));
		c.setNome(tryCatchField("nome"));
		c.setCelulaorigem(co);
		c.setSubregiao(sr);

		return c;
	}

	public void alterarCelula(Celula c) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		mln = new CelulaLN(manager);
		String mensagem = mln.atualizaCelulaAndroid(c);

		manager.close();
		factory.close();

		System.out.println(mensagem);
	}

	public Celula formatCelula(String json) throws JSONException, ParseException{

		jsonObject = new JSONObject(json);
		Celula c = new Celula();
		Celula co = mln.getCelula(jsonObject.getInt("idcelulaorigem"));
		
		c.setIdcelula(jsonObject.getInt("idcelula"));
		c.setCelulaorigem(co);
		c.setAtivo(Boolean.parseBoolean(jsonObject.getString("ativo")));
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-03:00'");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-02:00'");
		try{
			if(jsonObject.getString("datacriacao").equals("") || jsonObject.getString("datacriacao").equals(null)
					|| jsonObject.getString("datacriacao") == null || jsonObject.getString("datacriacao") == ""){
				c.setDatacriacao(null);
			} else {
				c.setDatacriacao(formatter.parse(jsonObject.getString("datacriacao")));
			}
		} catch (Exception e){
			c.setDatacriacao(formatter2.parse(jsonObject.getString("datacriacao")));
		}

		
		c.setDescricao(jsonObject.getString("descricao"));
		c.setNome(jsonObject.getString("nome"));
		c.setSubregiao(new SubRegiaoLN().getSubRegiao(jsonObject.getInt("idsubregiao")));
		
		return c;

	}
	
	public String tryCatchField(String tag){
		
		try{
			if(jsonObject.getString(tag) == "" || jsonObject.getString(tag).equals("")){
				System.out.println("JSON "+tag+" valor:"+jsonObject.getString(tag));
				return null;
			} else {
				return jsonObject.getString(tag);
			}
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


}