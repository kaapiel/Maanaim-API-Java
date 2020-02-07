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
import br.com.celula.entidade.Endereco;
import br.com.celula.entidade.Membro;
import br.com.celula.negocio.MembroLN;

@Path("/postInsertMembro.maanaimosasco")
public class PostInsertMembro {

	private EntityManager manager;
	private Membro m;
	private JSONObject jsonObject;
	private Integer idmembro;

	@POST
	@Consumes("application/json")
	public Response crunchifyREST(String jsonMembro) throws JSONException, ParseException{

		System.out.println("json que sera inserido: "+jsonMembro);
		m = formatMembro(jsonMembro);
		inserirMembro(m);
		System.out.println("id do membro inserido: "+idmembro);
		
		return Response.status(200).entity(String.valueOf(idmembro)).build();
	}

	public void inserirMembro(Membro m) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		MembroLN mln = new MembroLN(manager);
		String mensagem = mln.adicionaMembro(m, manager);
		
		EntityManagerFactory factory2 = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory2.createEntityManager();
		
		idmembro = mln.getMembroPorApelido(m.getApelido()).getIdmembro();
		
		manager.close();
		factory.close();
		
		System.out.println(idmembro);
		System.out.println(mensagem);
	}

	public Membro formatMembro(String json) throws JSONException, ParseException{

		jsonObject = new JSONObject(json);
		Membro m = new Membro();

		m.setApelido(jsonObject.getString("apelido"));
		m.setAtivo(true);

		Celula c = new Celula();
		c.setIdcelula(Integer.valueOf(jsonObject.getString("idcelula")));
		m.setCelula(c);

		m.setComplemento((jsonObject.getString("complemento")));
		
		m.setCpf(tryCatchField("cpf"));
		m.setEmail(tryCatchField("email"));

		Endereco e = new Endereco();
		e.setBairro(jsonObject.getString("bairro"));
		e.setCep(jsonObject.getString("cep"));
		e.setCidade(jsonObject.getString("cidade"));
		e.setLogradouro(jsonObject.getString("logradouro"));
		e.setUf(jsonObject.getString("uf"));
		m.setEndereco(e);

		m.setFone_cel((jsonObject.getString("fone_cel")));
		m.setFone_res((jsonObject.getString("fone_res")));
		m.setFoto(null);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-03:00'");
		m.setNascimento(formatter.parse(jsonObject.getString("nascimento")));
		m.setDatacadastro(formatter.parse(jsonObject.getString("datacadastro")));

		try{
			if(jsonObject.getString("dataBatismo").equals("") || jsonObject.getString("dataBatismo").equals(null)
					|| jsonObject.getString("dataBatismo") == null || jsonObject.getString("dataBatismo") == ""){
				m.setDataBatismo(null);
			} else {
				m.setDataBatismo(formatter.parse(jsonObject.getString("dataBatismo")));
			}
		} catch (Exception ex){
			ex.printStackTrace();
			m.setDataBatismo(null);
		}

		m.setNome((jsonObject.getString("nome")));
		m.setNumero((jsonObject.getString("numero")));
		m.setRg(tryCatchField("rg"));
		m.setSexo((jsonObject.getString("sexo")));

		return m;

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