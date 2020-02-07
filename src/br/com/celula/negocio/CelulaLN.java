package br.com.celula.negocio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.CelulaDao;
import br.com.celula.dao.GenericDao;
import br.com.celula.entidade.Celula;


public class CelulaLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Celula> dao;
	private EntityManager manager;
	private String msg;
	
	public CelulaLN(){		
		manager = EttMnger.manager();
	}
	
	public CelulaLN(EntityManager m){		
		manager = m;
	}
	
	public String adicionaCelula(Celula x){
		dao = new GenericDao<Celula>(manager);
		dao.adiciona(x); 
		msg="Adicionado com sucesso.";
		return msg;
	}
	
	public String atualizaCelula(Celula x){
		dao = new GenericDao<Celula>(manager);
		dao.atualiza(x);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	//android
	public String atualizaCelulaAndroid(Celula x){
		dao = new GenericDao<Celula>(manager);
		dao.atualizaAndroid(x);
		msg="Atualizado com sucesso.";
		return msg;
	}
	
	public String removeCelula(Celula x){
		dao = new GenericDao<Celula>(manager);
		CelulaDao cdao = new CelulaDao();
		if(x.getMembros().size()!=0){
			msg="Não excluido, pois existem membros vinculadas a esta célala.";
			return msg;	
		}
		if(cdao.estaUsadaNaCelulaOrigem(x)){
			msg="Não excluido, pois existem referencia a esta célula em outras células originadas desta.";
			return msg;				
		}
			
		
		dao.deleta(getCelula(x.getIdcelula()));
		msg="Excluido com sucesso.";
		return msg;
	}
	
	public List<Celula> getListAll(){
		dao = new GenericDao<Celula>(manager);	
		return dao.lista("celula","id");
	}
	
	public List<Celula> getList(){
		CelulaDao cdao = new CelulaDao();	
		return cdao.celulasAtivas();
	}
	
	public boolean existeCelulaCadastrada(){
		CelulaDao cdao = new CelulaDao();
		return cdao.existeCelulaCadastrada();
	}
	
	public Celula getCelula(Integer id){	
		dao = new GenericDao<Celula>(manager);
		Celula x = new Celula();
		return (Celula) dao.buscaPorId(x.getClass(), id);
	}

	public boolean existeCelulaOrigemInicial(Celula c){
		CelulaDao cdao = new CelulaDao();
		return cdao.existeCelulaOrigemInicial(c);
	}
	
	public String celulaToJSON(Celula c) {

        JSONObject jo = new JSONObject();
        try {

            jo.put("idcelula", c.getIdcelula());
            jo.put("idcelulaorigem", c.getCelulaorigem().getIdcelula());
            jo.put("nome", c.getNome());
            jo.put("supervisores", c.getSubregiao().getNome());
            jo.put("regiao", c.getSubregiao().getRegiao().getCor());
            jo.put("descricao", c.getDescricao());
            jo.put("ativo", c.getAtivo());
            jo.put("dataCriacao", c.getDatacriacao());
            
            return jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
