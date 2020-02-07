package br.com.celula.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;


/**
*
* @author 
*/
public class GenericDao<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private EntityManager manager;
	
	public GenericDao(EntityManager manager){
		this.manager = manager;
	}
	
	public GenericDao(){
		
	}
	
	public void adiciona(T t){
		manager.persist(t);
	}
	
	//android
	public void adiciona(T t, EntityManager em){
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
	}
	
	public void atualiza(T t){
		manager.merge(t);
	}
	
	//android
	public void atualizaAndroid(T t){
		manager.getTransaction().begin();
		manager.merge(t);	
		manager.getTransaction().commit();
	}
	
	public void deleta(T t){
		this.manager.remove(t);
	}

	@SuppressWarnings("rawtypes")
	public Object buscaPorId(Class clazz, Object id){
		
		Field[] fields = clazz.getDeclaredFields();
		String chave = "";
		for(Field field: fields){
			if(field.getAnnotation(Id.class)!= null){
				chave = field.getName();
				break;
			}			
		}
		Query query = this.manager.createQuery(" From "+clazz.getName() + " Where "+ chave + " = :id");
		query.setParameter("id", id);
		query.setMaxResults(1);
		
		return query.getSingleResult();		
	}
	
	@SuppressWarnings("unchecked")
	public List<T> lista(String classe,Object orderBy){
		String sql = " From "+ classe +" x ORDER BY x."+orderBy;	
		return manager.createQuery(sql).getResultList();	
	}


}
