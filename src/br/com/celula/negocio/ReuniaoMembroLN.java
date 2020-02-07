package br.com.celula.negocio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import br.com.celula.conexao.EttMnger;
import br.com.celula.dao.GenericDao;
import br.com.celula.dao.ReuniaoDao;
import br.com.celula.dao.ReuniaoMembroDao;
import br.com.celula.entidade.EstadosReuniao;
import br.com.celula.entidade.Membro;
import br.com.celula.entidade.Reuniao;
import br.com.celula.entidade.ReuniaoMembro;
import br.com.celula.integracao.model.AgendamentoPresencaAndroid;


public class ReuniaoMembroLN implements Serializable {

	private static final long serialVersionUID = 1L;
	private GenericDao<ReuniaoMembro> dao;
	private EntityManager manager;
	private String msg;

	public ReuniaoMembroLN(){		
		manager = EttMnger.manager();
	}

	public ReuniaoMembroLN(EntityManager m){		
	}

	public String adiciona(ReuniaoMembro x){
		dao = new GenericDao<ReuniaoMembro>(manager);
		dao.adiciona(x); 
		msg="Adicionado com sucesso.";
		return msg;
	}

	public String atualiza(ReuniaoMembro x){
		dao = new GenericDao<ReuniaoMembro>(manager);
		dao.atualiza(x);
		msg="Atualizado com sucesso.";
		return msg;
	}

	public String removeReuniao(ReuniaoMembro x){
		dao = new GenericDao<ReuniaoMembro>(manager);
		dao.deleta(x);
		msg="Excluido com sucesso.";
		return msg;
	}

	public List<ReuniaoMembro> getListMembrosPresentesNaReuniao(Reuniao reuniao) {
		ReuniaoMembroDao mdao = new ReuniaoMembroDao();				
		return mdao.membrosPresentesNaReuniao(reuniao);
	}

	public Reuniao getReuniao(Integer id){	
		dao = new GenericDao<ReuniaoMembro>(manager);
		Reuniao x = new Reuniao();
		return (Reuniao) dao.buscaPorId(x.getClass(), id);
	}

	private Membro getMembro(Integer i){
		MembroLN mln = new MembroLN();
		return mln.getMembro(i);
	}


	public String gravar(Reuniao reu, List<Membro> ausentes, List<ReuniaoMembro> participantes) {

		//a reunião é definida atraves do metodo carregarCamposParaGravar(); e depois pelo método reuniaoLN.adicionaReuniao(this.reuniao);
		//não esquecer do IDCELULA e do IDESTADO


		ReuniaoMembroDao mdao = new ReuniaoMembroDao();
		Set<ReuniaoMembro> list = new HashSet<ReuniaoMembro>(); //ausentes
		Set<ReuniaoMembro> list2 = new HashSet<ReuniaoMembro>(participantes); //presentes

		try{
			mdao.removeMembroReuniao(reu); //estudar o que isso faz

			for(ReuniaoMembro r : list2){ //RELAÇÃO PRONTA DE MEMBROS PRESENTES
				r.setReuniao(getReuniao(reu.getIdreuniao())); //associa cada membro à reunião passada no metodo gravar();
			}

			mdao.adiciona(new ArrayList<ReuniaoMembro>(list2)); //MÉTODO ONTRANSFER() FAZ A ASSOCIAÇÃO DE PRESENÇA

			for(Membro m : ausentes){ //MEMBROS ausentes SEM RELAÇÃO À REUNIAO
				ReuniaoMembro rm = new ReuniaoMembro();
				rm.setEstevepresente(false);
				rm.setMembro(getMembro(m.getIdmembro()));
				rm.setReuniao(getReuniao(reu.getIdreuniao()));
				list.add(rm);	
			}

			mdao.adiciona(new ArrayList<ReuniaoMembro>(list)); // grava os ausentes

			return "Gravado com sussesso ";

		}catch(Exception e){
			System.out.println(e.getMessage()+e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
	}

	public String gravar(List<AgendamentoPresencaAndroid> list, EntityManager em) {
		ReuniaoMembroDao mdao = new ReuniaoMembroDao(em);	
		try{

			ArrayList<ReuniaoMembro> rmList = new ArrayList<ReuniaoMembro>();
			for(AgendamentoPresencaAndroid m : list){ 
				ReuniaoMembro rm = new ReuniaoMembro();
				Membro membro = new Membro();
				membro.setIdmembro(m.getIdMembro());
				rm.setEstevepresente(m.getEstevePresente());
				rm.setMembro(membro);
				rm.setEstevepresentetadel(m.getEstevePresenteTadel());

				EstadosReuniao er = new EstadosReuniao();
				er.setIdestado(2);

				Reuniao r = new Reuniao();
				r.setIdreuniao(m.getIdReuniao());
				ReuniaoDao dao = new ReuniaoDao(em);
				Reuniao reuniao = dao.reuniao(r);
				reuniao.setEstado(er);

				ReuniaoLN reuniaoLN = new ReuniaoLN(em);
				reuniaoLN.atualizaReuniaoJSON(reuniao);

				rm.setReuniao(reuniao);
				rmList.add(rm);
			}

			mdao.adiciona(rmList);
			
			return "Gravado com sussesso ";
		}catch(Exception e){
			System.out.println(e.getMessage()+e.getLocalizedMessage());
			return e.getLocalizedMessage();
		}
	}


}
