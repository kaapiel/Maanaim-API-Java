package br.com.celula.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.SubRegiao;
import br.com.celula.negocio.SubRegiaoLN;


@FacesConverter(forClass=SubRegiao.class,value="subregiaoConverter")
public class SubRegiaoConverter implements Converter {

	private SubRegiaoLN ln;
	private List<SubRegiao> subregioes = new ArrayList<SubRegiao>();
	private SubRegiao subregiao;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		ln=new SubRegiaoLN();
		try {
			if (value == null || value.equalsIgnoreCase("0"))
				return null;
			Integer id = new Integer(value);
			return ln.getSubRegiao(id);
		} catch (NumberFormatException ne) {
			throw new ConverterException("Nao foi possivel converter (" + value
					+ ") para subregiao: nao e um numero inteiro.", ne);
		} catch (IllegalArgumentException e) {
			throw new ConverterException("Nao foi possivel converter (" + value
					+ ") para subregiao: identificador desconhecido.", e);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null)
			return null;
		if (value instanceof SubRegiao) {
			SubRegiao aux = (SubRegiao) value;
			return String.valueOf(aux.getIdsubregiao());
		} else if (value instanceof String
				&& ((String) value).equalsIgnoreCase("-1")) {
			return "-1";
		} else
			throw new ConverterException("(" + value.getClass()
					+ ") nao e uma instancia.");
	}

	public SubRegiaoLN getLn() {
		return ln;
	}

	public void setLn(SubRegiaoLN ln) {
		this.ln = ln;
	}

	public List<SubRegiao> getSubRegioes() {
		return subregioes;
	}

	public void setSubRegioes(List<SubRegiao> subregioes) {
		this.subregioes = subregioes;
	}

	public SubRegiao getSubRegiao() {
		return subregiao;
	}

	public void setSubRegiao(SubRegiao subregiao) {
		this.subregiao = subregiao;
	}
	
}
