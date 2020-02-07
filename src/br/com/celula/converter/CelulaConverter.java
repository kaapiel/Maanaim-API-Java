package br.com.celula.converter;

import java.util.ArrayList;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.Celula;
import br.com.celula.negocio.CelulaLN;


@FacesConverter(forClass=Celula.class,value="celulaConverter")
public class CelulaConverter implements Converter {

	private CelulaLN ln;
	private List<Celula> celulas = new ArrayList<Celula>();
	private Celula celula;
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		ln=new CelulaLN();
		try {
			if (value == null || value.equalsIgnoreCase("0"))
				return null;
			Integer id = new Integer(value);
			return ln.getCelula(id);
		} catch (NumberFormatException ne) {
			throw new ConverterException("Nao foi possivel converter (" + value
					+ ") para celula: nao e um numero inteiro.", ne);
		} catch (IllegalArgumentException e) {
			throw new ConverterException("Nao foi possivel converter (" + value
					+ ") para celula: identificador desconhecido.", e);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object value) {
		if (value == null)
			return null;
		if (value instanceof Celula) {
			Celula aux = (Celula) value;
			return String.valueOf(aux.getIdcelula());
		} else if (value instanceof String
				&& ((String) value).equalsIgnoreCase("-1")) {
			return "-1";
		} else
			throw new ConverterException("(" + value.getClass()
					+ ") nao e uma instancia.");
	}

	public CelulaLN getLn() {
		return ln;
	}

	public void setLn(CelulaLN ln) {
		this.ln = ln;
	}

	public List<Celula> getCelulas() {
		return celulas;
	}

	public void setCelulas(List<Celula> celulas) {
		this.celulas = celulas;
	}

	public Celula getCelula() {
		return celula;
	}

	public void setCelula(Celula celula) {
		this.celula = celula;
	}
	
}
