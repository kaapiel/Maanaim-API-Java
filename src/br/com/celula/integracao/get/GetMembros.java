package br.com.celula.integracao.get;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.axis.encoding.Base64;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.MembroDao;
import br.com.celula.entidade.Membro;
import br.com.celula.integracao.model.MembroAndroid;

@Path("/getmembros.maanaimosasco")
public class GetMembros {
	
	@GET
	@Produces("application/json; charset=utf-8")
	public List<MembroAndroid> getMembros(){	
		return membros();		
	}
	
	private List<MembroAndroid> membros(){
		List<MembroAndroid> membros = new ArrayList<MembroAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		MembroDao dao = new MembroDao(manager);
		for(Membro m:dao.membrosAtivos()){
			MembroAndroid x = new MembroAndroid();
			
			x.setIdcelula(m.getCelula().getIdcelula());
			x.setIdmembro(m.getIdmembro());
			x.setNome(m.getNome());
			
			if(m.getFoto() != null){
				String encode = Base64.encode(m.getFoto());
				x.setFoto(encode);
			}
			
			x.setFone_res(m.getFone_res());
			x.setFone_cel(m.getFone_cel());
			x.setCep(m.getEndereco().getCep());
			x.setEmail(m.getEmail());
			x.setNumero(m.getNumero());
			x.setBairro(m.getEndereco().getBairro());
			x.setLogradouro(m.getEndereco().getLogradouro());
			x.setCidade(m.getEndereco().getCidade());
			x.setNascimento(m.getNascimento());
			
			x.setApelido(m.getApelido());
			x.setRg(m.getRg());
			x.setCpf(m.getCpf());
			x.setDataBatismo(m.getDataBatismo());
			x.setDatacadastro(m.getDatacadastro());
			x.setSexo(m.getSexo());
			x.setUf(m.getEndereco().getUf());
			x.setComplemento(m.getComplemento());
			x.setAtivo(m.getAtivo());
			
			membros.add(x);
			
		}
		
		return membros;
	}
}
