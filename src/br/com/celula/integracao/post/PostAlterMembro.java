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

@Path("/postAlterMembro.maanaimosasco")
public class PostAlterMembro {

	private EntityManager manager;
	private JSONObject jsonObject;

	@POST
	@Consumes("application/json")
	public Response crunchifyREST(String jsonMembro) throws JSONException, ParseException {

		System.out.println(jsonMembro);
		alterarMembro(formatMembro(jsonMembro));
		return Response.status(200).entity(jsonMembro).build();
	}

	public void alterarMembro(Membro m) {

		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();

		MembroLN mln = new MembroLN(manager);
		String mensagem = mln.atualizaMembroAndroid(m);

		manager.close();
		factory.close();

		System.out.println(mensagem);
	}

	public Membro formatMembro(String json) throws JSONException, ParseException{

		jsonObject = new JSONObject(json);
		Membro m = new Membro();

		m.setIdmembro(Integer.valueOf(tryCatchField("idmembro")));
		m.setApelido(tryCatchField("apelido"));
		m.setAtivo(true);

		Celula c = new Celula();
		c.setIdcelula(Integer.valueOf(tryCatchField("idcelula")));
		m.setCelula(c);

		m.setComplemento(tryCatchField("complemento"));
		m.setCpf(tryCatchField("cpf"));
		m.setEmail(tryCatchField("email"));

		Endereco e = new Endereco();
		e.setBairro(tryCatchField("bairro"));
		e.setCep(tryCatchField("cep"));
		e.setCidade(tryCatchField("cidade"));
		e.setLogradouro(tryCatchField("logradouro"));
		e.setUf(tryCatchField("uf"));
		m.setEndereco(e);

		m.setFone_cel(tryCatchField("fone_cel"));
		m.setFone_res(tryCatchField("fone_res"));
		m.setFoto(null);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-02:00'");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-03:00'");
		
		try{
			m.setNascimento(formatter.parse(tryCatchField("nascimento")));
		}catch (Exception ex){
			m.setNascimento(formatter2.parse(tryCatchField("nascimento")));
		}

		try{
			if(jsonObject.getString("dataBatismo").equals("") || jsonObject.getString("dataBatismo").equals(null)
					|| jsonObject.getString("dataBatismo") == null || jsonObject.getString("dataBatismo") == ""){
				m.setDataBatismo(null);
			} else {
				try{
					m.setDataBatismo(formatter.parse(jsonObject.getString("dataBatismo")));
				}catch(Exception ex){
					m.setDataBatismo(formatter2.parse(jsonObject.getString("dataBatismo")));
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
			m.setDataBatismo(null);
		}

		m.setNome(tryCatchField("nome"));
		m.setNumero(tryCatchField("numero"));
		m.setRg(tryCatchField("rg"));
		m.setSexo(tryCatchField("sexo"));

		return m;
	}

	public String tryCatchField(String tag){

		try{
			if(jsonObject.getString(tag) == "" || jsonObject.getString(tag).equals("")){
				return null;
			} else {
				System.out.println("JSON "+tag+" valor:"+jsonObject.getString(tag));
				return jsonObject.getString(tag);
			}
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}


}