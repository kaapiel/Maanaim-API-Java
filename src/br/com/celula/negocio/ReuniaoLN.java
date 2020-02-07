package br.com.celula.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.LocalDate;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.ReuniaoDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.EstadosReuniao;
import br.com.celula.entidade.Reuniao;


public class ReuniaoLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Reuniao> dao;
	private EntityManager manager;
	private String msg;

	public ReuniaoLN(){		
		manager = EttMnger.manager();
	}

	public ReuniaoLN(EntityManager m){		
		manager = m;
	}

	public ReuniaoLN(String m){		
	}

	public String adicionaReuniao(Reuniao x){
		dao = new GenericDao<Reuniao>(manager);
		EstadosReuniao s = new EstadosReuniao();
		s.setIdestado(1); //agendado
		x.setEstado(s);
		EnderecoLN eln = new EnderecoLN();
		if(!eln.exiteEnderecoNoBanco(x.getEndereco().getCep()))
			eln.adiciona(x.getEndereco());
		dao.adiciona(x); 
		msg="Adicionado com sucesso.";
		return msg;
	}

	public String atualizaReuniao(Reuniao x){
		dao = new GenericDao<Reuniao>(manager);
		EnderecoLN eln = new EnderecoLN();
		if(!eln.exiteEnderecoNoBanco(x.getEndereco().getCep()))
			eln.adiciona(x.getEndereco());
		dao.atualiza(x);
		msg="Atualizado com sucesso.";
		return msg;
	}

	public String atualizaReuniaoJSON(Reuniao x){
		dao = new GenericDao<Reuniao>(manager);
		dao.atualiza(x);
		return msg;
	}

	public String removeReuniao(Reuniao x){
		dao = new GenericDao<Reuniao>(manager);
		dao.deleta(x);
		msg="Excluido com sucesso.";
		return msg;
	}

	public List<Reuniao> getListReunioes(){
		dao = new GenericDao<Reuniao>(manager);	
		return dao.lista("reuniao","datareuniao,horareuniao");
	}

	public Reuniao getReuniao(Integer id){	
		dao = new GenericDao<Reuniao>(manager);
		Reuniao x = new Reuniao();
		return (Reuniao) dao.buscaPorId(x.getClass(), id);
	}

	public List<Reuniao> getListReunioesPorCelula(Celula celula,EstadosReuniao status) {
		ReuniaoDao rdao = new ReuniaoDao();		
		return rdao.reunioes(status,celula);
	}

	public List<Reuniao> getListReunioesPorCelula() {
		ReuniaoDao rdao = new ReuniaoDao();		
		return rdao.reunioes();
	}

	public boolean existeReuniaoComMenosDeUmaSemana(Reuniao r) {
		ReuniaoDao rdao = new ReuniaoDao();	
		LocalDate d = new LocalDate(r.getDatareuniao());
		return rdao.existeReuniaoComMenosDeUmaSemana(r.getCelula(),d.minusDays(6).toDate());
	}

	public String reuniaoToJSON(Reuniao r) {
		JSONObject jo = new JSONObject();
		try {

			jo.put("idReuniao", r.getIdreuniao());
			jo.put("idCelula", r.getCelula().getIdcelula());
			jo.put("idEstado", r.getEstado().getIdestado());
			jo.put("complemento", r.getComplemento());
			jo.put("dataReuniao", r.getDatareuniao());
			jo.put("horaReuniao", r.getHorareuniao());
			jo.put("numeroLocal", r.getNumero());
			jo.put("cep", r.getEndereco().getCep());
			
			return jo.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}
}
