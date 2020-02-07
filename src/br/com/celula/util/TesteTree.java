package br.com.celula.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import br.com.celula.dao.CelulaDao;
import br.com.celula.entidade.Celula;

public class TesteTree {
	static List<Celula>  filhas = new ArrayList<Celula>();
	static MindmapNode root;
	public static void main(String[] args) throws ParseException {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("maanaimPU");
		EntityManager manager = factory.createEntityManager();
		manager.getTransaction().begin();

		CelulaDao dao = new CelulaDao(manager);
		List<Celula>  rs = dao.celulasAtivas();
		Celula pai = new Celula();
		
		for(Celula s:rs)//identifica a root
			if(s.getIdcelula().equals(s.getCelulaorigem().getIdcelula()))
				pai = s;
		root = new DefaultMindmapNode(pai.getNome());
		MindmapNode ndx = new DefaultMindmapNode();
		ordenaLista(rs);
		rs.set(0, pai);
		for(int i=0;i<rs.size();i++){
			ndx = new DefaultMindmapNode(rs.get(i).getNome());
			MindmapNode nd = new DefaultMindmapNode();
			System.out.println(rs.get(i).getNome() + " PAI");
			for(Celula x:rs){
				if(rs.get(i).getIdcelula().equals(x.getCelulaorigem().getIdcelula())){
					nd = new DefaultMindmapNode(x.getNome(),ndx);
					System.out.println(x.getNome() + " filho");
					ndx.addNode(nd);
				}
			}
		}
		//recursive(rs, rs.get(0).getIdcelula(), root);
		System.out.println(ndx.getChildren().size());
		for(MindmapNode node:ndx.getChildren())
			System.out.println(node.getLabel());
		
        manager.getTransaction().commit();
        manager.close();
        factory.close();
	}
	
		public static void recursive(List<Celula> list, int id,MindmapNode pai){
			   List<Celula> subList2=new ArrayList<Celula>();
			   subList2=subPlano(id);
			   for(Celula c:subList2){
				 MindmapNode childNode=new DefaultMindmapNode(c.getNome(),pai);
				 //root.addNode(childNode);
			     recursive(subList2, c.getIdcelula(),childNode);
			   }
		}
			   
	 public static List<Celula> subPlano(long i){
	 	List<Celula> newList=new ArrayList<Celula>();
	     for(Celula c:filhas)
	         if(c.getCelulaorigem().getIdcelula()==i)
	             newList.add(c);
	     return newList;
	 }

	public static List<Celula> ordenaLista(List<Celula> list){
		Collections.sort(list, new Comparator<Celula>() {
	        @Override
	        public int compare(Celula  c1, Celula  c2)
	        {
	            return  c1.getCelulaorigem().getIdcelula().compareTo(c2.getCelulaorigem().getIdcelula());
	        }
	    });
		return list;
	}
}
