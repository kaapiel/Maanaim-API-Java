package br.com.celula.negocio;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/sair")
public class SairLN {

	@GET
	public String sair(){

		System.out.println("saiu");
		return "login";
	}
}
