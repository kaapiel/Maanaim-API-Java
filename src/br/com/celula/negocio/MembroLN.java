package br.com.celula.negocio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.MembroDao;
import br.com.celula.entidade.Celula;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Regiao;
import br.com.celula.entidade.Reuniao;
import br.com.celula.entidade.SubRegiao;
import br.com.celula.util.SaveFile;

public class MembroLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<Membro> dao;
	private EntityManager manager;
	private String msg;

	public MembroLN(){		
		manager = EttMnger.manager();
	}

	//android
	public MembroLN(EntityManager m){		
		this.manager = m;
	}

	public String adicionaMembro(Membro m){
		dao = new GenericDao<Membro>(manager);
		EnderecoLN eln = new EnderecoLN();
		if(!eln.exiteEnderecoNoBanco(m.getEndereco().getCep()))
			eln.adiciona(m.getEndereco());
		dao.adiciona(m); 
		msg=m.getNome()+" adicionado com sucesso.";
		return msg;
	}
	
	//android
	public String adicionaMembro(Membro m, EntityManager em){
		GenericDao<Membro> dao = new GenericDao<Membro>();
		EnderecoLN eln = new EnderecoLN();
		if(!eln.exiteEnderecoNoBanco(m.getEndereco().getCep(), em))
			eln.adiciona(m.getEndereco(), em);
		dao.adiciona(m, em); 
		msg=m.getNome()+" adicionado com sucesso.";
		return msg;
	}

	public String atualizaMembro(Membro m){
		EnderecoLN eln = new EnderecoLN();
		dao = new GenericDao<Membro>(manager);
		if(!eln.exiteEnderecoNoBanco(m.getEndereco().getCep()))
			eln.adiciona(m.getEndereco());
		dao.atualiza(m);
		msg=m.getNome()+" atualizado com sucesso.";
		return msg;
	}

	//android
	public String atualizaMembroAndroid(Membro m){
		GenericDao<Membro> dao = new GenericDao<Membro>(manager);
		EnderecoLN eln = new EnderecoLN();
		if(!eln.exiteEnderecoNoBanco(m.getEndereco().getCep(), manager))
			eln.adiciona(m.getEndereco(), manager);
		dao.atualizaAndroid(m);
		msg=m.getNome()+" atualizado com sucesso.";
		return msg;
	}
	
	public String removeMembro(Membro m){
		dao = new GenericDao<Membro>(manager);
		dao.deleta(m);
		msg=m.getNome()+ " excluido com sucesso.";
		return msg;
	}

	public List<Membro> getListMembros(){
		dao = new GenericDao<Membro>(manager);
		List<Membro> membros = new ArrayList<Membro>();
		membros = dao.lista("membro","nome");		
		return membros;
	}

	public Membro getMembro(Integer id){	
		dao = new GenericDao<Membro>(manager);
		Membro m = new Membro();
		return (Membro) dao.buscaPorId(m.getClass(), id);
	}

	public boolean validaUsuario(String usuario, String senha) {
		MembroDao daoM = new MembroDao();
		boolean b=false;
		b=daoM.exiteUnico(usuario,senha);
		return b;
	}

	public Membro getMembro(String usuario, String senha){
		MembroDao daoM = new MembroDao();
		return daoM.retornaUsuario(usuario, senha);		
	}
	
	public Membro getMembroPorApelido(String apelido){
		
		manager.getTransaction().begin();
		Membro m = null;
		   try {   
		      Query query = this.manager.createQuery(" FROM membro m WHERE m.apelido = :apelido");   
		      query.setParameter("apelido", apelido);   
		      try{
		    	  m = (Membro) query.getSingleResult(); 
		      }catch(NoResultException e){
		    	  m = null;
		      }
		      
		   } catch (NoResultException e) {  
			   m = null;   
		   } catch (RuntimeException e) {  
		      e.printStackTrace();   
		   }  
		   
		   manager.getTransaction().commit();
		   return m;
	}

	public String exibeFoto(FileUploadEvent event) {
		SaveFile sf = new SaveFile();
		try {
			return	sf.criaArquivoTemp(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String recuperaFoto(Membro m){
		if(m.getFoto()!=null){
			ServletContext sContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			File folder = new File(sContext.getRealPath("/resources/tmp/"));
			String arquivo = sContext.getRealPath("/resources/tmp/") + File.separator + m.getIdmembro() +".jpg";
			if (!folder.exists())
				folder.mkdirs();
			SaveFile.gravaArquivoWeb(m.getFoto(), arquivo);
			return m.getIdmembro() +".jpg";
		}
		return "foto.jpg";
	}

	public List<Membro> getListMembrosAtivos(){
		MembroDao mdao = new MembroDao();				
		return mdao.membrosAtivos();
	}

	public List<Membro> getListMembrosAtivos(Celula celula){
		MembroDao mdao = new MembroDao();				
		return mdao.membrosAtivos(celula);
	}

	public List<Membro> getListMembrosAtivos(Integer idCelula){
		MembroDao mdao = new MembroDao();				
		return mdao.membrosAtivos(idCelula);
	}

	public List<Membro> getListMembrosAtivos(SubRegiao r){
		MembroDao mdao = new MembroDao();				
		return mdao.membrosAtivos(r);
	}

	public List<Membro> getListMembrosAtivos(Regiao r){
		MembroDao mdao = new MembroDao();				
		return mdao.membrosAtivos(r);
	}

	public List<Membro> getListMembrosPresentesNaReuniao(Reuniao reuniao) {
		MembroDao mdao = new MembroDao();				
		return mdao.membrosPresentesNaReuniao(reuniao);
	}

	public List<Membro> getListArniversariantes(Integer mes) {
		MembroDao mdao = new MembroDao();	
		return mdao.aniversariantes(mes);
	}
	public int totalMembrosAtivos(){
		MembroDao mdao = new MembroDao(manager);	
		return mdao.totalMembrosAtivos();
	}

	public String removeMembro(String idMembro, EntityManager em, Membro membro){

		em.getTransaction().begin();

		Query query = em.createQuery(
				"DELETE FROM reuniao_membro rm WHERE rm.membro= :membro");
		int deletedCount = query.setParameter("membro", membro).executeUpdate();
		
		System.out.println("Quantidade de reunioes participadas: "+deletedCount);
		
		Query query2 = em.createQuery(
				"DELETE FROM membro m WHERE m.idmembro= :idmembro");
		int deletedCount2 = query2.setParameter("idmembro", Integer.valueOf(idMembro)).executeUpdate();
		
		System.out.println("Deletados "+deletedCount2+" registros");
		
		em.getTransaction().commit();
		return msg;
	}
	
	public String membroToJSON(Membro m) {

        JSONObject jo = new JSONObject();
        try {

            jo.put("idmembro", m.getIdmembro());
            
            jo.put("idcelula", m.getCelula().getIdcelula());
            jo.put("nome", m.getNome());
            jo.put("apelido", m.getApelido());
            jo.put("foto", m.getFoto());
            jo.put("rg", m.getRg());
            jo.put("cpf", m.getCpf());
            jo.put("dataBatismo", m.getDataBatismo());
            jo.put("email", m.getEmail());
            jo.put("fone_res", m.getFone_res());
            jo.put("fone_cel", m.getFone_cel());
            jo.put("nascimento", m.getNascimento());
            jo.put("datacadastro", m.getDatacadastro());
            jo.put("sexo", m.getSexo());
            
            jo.put("cep", m.getEndereco().getCep());
            jo.put("rua", m.getEndereco().getLogradouro());
            jo.put("bairro", m.getEndereco().getBairro());
            jo.put("cidade", m.getEndereco().getCidade());
            jo.put("uf", m.getEndereco().getUf());
            jo.put("numero", m.getNumero());
            
            jo.put("complemento", m.getComplemento());
            jo.put("ativo", m.getAtivo());

            return jo.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
