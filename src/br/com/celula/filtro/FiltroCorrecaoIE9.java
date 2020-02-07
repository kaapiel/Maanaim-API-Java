package br.com.celula.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
 
public class FiltroCorrecaoIE9 implements Filter {
 
   public void init(FilterConfig filterConfig) throws ServletException {
   }
 
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      ((HttpServletResponse) response).setHeader("X-UA-Compatible", "IE=EmulateIE8");
      chain.doFilter(request, response);
   }
 
   public void destroy() {
   }
}