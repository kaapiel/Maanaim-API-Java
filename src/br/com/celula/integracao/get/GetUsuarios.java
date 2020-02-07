package br.com.celula.integracao.get;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.UsuarioDao;
import br.com.celula.entidade.Usuario;
import br.com.celula.integracao.model.UsuarioAndroid;


@Path("/getusuarios.maanaimosasco")
public class GetUsuarios {
	
	@GET
	@Produces("application/json")
	public List<UsuarioAndroid> getUsuarios(){		
		return usuarios();		
	}
	
	private List<UsuarioAndroid> usuarios(){
		List<UsuarioAndroid> usuarios = new ArrayList<UsuarioAndroid>();
		EntityManager manager = EttMnger.getManager();
		manager.getTransaction().begin();
		UsuarioDao dao = new UsuarioDao(manager);
		for(Usuario c:dao.usuarios()){
			UsuarioAndroid x = new UsuarioAndroid();
			x.setIdusuario(c.getIdusuario());
			if(c.getMembro()!=null)
				x.setIdmembro(c.getMembro().getIdmembro());
			else
				x.setIdmembro(null);
			x.setNome(c.getNome());
			x.setUsuario(c.getUsuario());
			x.setSenha(c.getSenha());
			usuarios.add(x);
		}
		return usuarios;
	}
}
