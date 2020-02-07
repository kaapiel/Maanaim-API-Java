package br.com.celula.filtro;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class JPAFilter implements Filter {
	
	private EntityManagerFactory factory;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		this.factory = Persistence.createEntityManagerFactory("maanaimPU");
	}
	
	@Override
	public void destroy() {
		this.factory.close();		
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException , ServletException {
		//CHEGADA
		EntityManager manager = this.factory.createEntityManager();
		request.setAttribute("EntityManager", manager);
		manager.getTransaction().begin();
		
		//FILTRO
		chain.doFilter(request, response);
		
		try {
			manager.getTransaction().commit();
		} catch (Exception e) {
			System.out.println( e.getLocalizedMessage() + " "+e.getCause() + " " + e.getStackTrace());
			manager.getTransaction().rollback();
		}finally{
			manager.close();
		}

	}
}
