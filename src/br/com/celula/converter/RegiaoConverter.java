package br.com.celula.converter;

import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.Regiao;

@FacesConverter("regiaoConverter")
public class RegiaoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof Regiao) {
        	Regiao entity = (Regiao) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getCor());
            return sb.toString();
        }

        return null;
    }
}
