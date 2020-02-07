package br.com.celula.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;


@ManagedBean(name="dialogMB")
@ViewScoped
public class DialogMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1235761208815478916L;

    public void selecionarCelula() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 480);
        RequestContext.getCurrentInstance().openDialog("celulas",options, null);
    }

    public void selecionarSubRegiao() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 480);
        RequestContext.getCurrentInstance().openDialog("subregioes",options, null);
    }
    
    public void selecionarRegiao() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 480);
        RequestContext.getCurrentInstance().openDialog("regioes",options, null);
    }
    
    public void selecionarMembro() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 480);
        RequestContext.getCurrentInstance().openDialog("membros",options, null);
    }
    
    public void selecionarAviso() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("resizable", false);
        options.put("contentHeight", 480);
        RequestContext.getCurrentInstance().openDialog("eventos",options, null);
    }
}
