package br.com.celula.integracao.post;
 
/**
 * @author Gabriel Aguido Fraga
 * 
 */
 
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.com.celula.dao.EnderecoDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Endereco;
import br.com.celula.entidade.EstadosReuniao;
import br.com.celula.entidade.Reuniao;
import br.com.celula.integracao.model.AgendamentoAndroid;
import br.com.celula.util.ValidarData;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
 
@Path("/")
public class PostAgReuniao {
	
	private AgendamentoAndroid ra = new AgendamentoAndroid();
	private EntityManager manager;
    
	@POST
    @Path("/postAgReuniao.maanaimosasco")
    @Consumes("application/json")
    public Response crunchifyREST(String dadosRecebidos) {
        
       System.out.println("Dados recebidos: " + dadosRecebidos);
 
        Gson gs = new Gson();
		JsonParser jp = new JsonParser();
		JsonObject formReuniao = (JsonObject) jp.parse(dadosRecebidos).getAsJsonObject();
		
		ra = gs.fromJson(formReuniao, AgendamentoAndroid.class);
		
		// retorna uma resposta HTTP 200 em caso de sucesso com o IDREUNIAO da reuniao criada
	    return Response.status(200).entity(grava().toString()).build();
			
    }
    
    private Integer grava(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		manager = factory.createEntityManager();
		manager.getTransaction().begin();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		EstadosReuniao status = new EstadosReuniao();
		Celula c = new Celula();
		Reuniao r = new Reuniao();
		Endereco e = new Endereco();
		status.setIdestado(1);
		c.setIdcelula(ra.getIdCelula());
		r.setCelula(c);
		r.setEstado(status);
		r.setComplemento(ra.getDescricao());
		r.setNumero(ra.getNumero());
		r.setOferta(ra.getOferta());
		r.setVisitantes(ra.getVisitantes());
		r.setCriancas(ra.getCriancas());
		
		try {
			e = getEndereco(ra.getCepJson());
			r.setEndereco(e);
			r.setDatareuniao(ValidarData.stringToDate(ra.getData()));
			Date hora = simpleDateFormat.parse(ra.getHora());
			r.setHorareuniao(hora);
		} catch (ParseException | IOException ex) {
			System.out.println(ex.getLocalizedMessage());
			ex.printStackTrace();
		}
		
		manager.persist(r);
        manager.getTransaction().commit();
        manager.close();
        factory.close();
		System.out.println("************************  FIM RECEBIMENTO DA AGENDA DE REUNIAO  *************************");
		
		return r.getIdreuniao();
    }
    
    private Endereco getEndereco(String s) throws IOException {
		EnderecoDao dao = new EnderecoDao(manager);
		Endereco e = new Endereco();
		e.setBairro(ra.getBairro());
		e.setCep(ra.getCepJson());
		e.setCidade(ra.getCidade());
		e.setLogradouro(ra.getLogradouro());
		e.setUf(ra.getEstado());
		
		if(!dao.existeCepNoBanco(s)){
			manager.persist(e);
		}
		
		return e;
	}
    
}