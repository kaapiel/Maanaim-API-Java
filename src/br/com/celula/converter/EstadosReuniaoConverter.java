package br.com.celula.converter;

import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.EstadosReuniao;

@FacesConverter("statusReuniaoConverter")
public class EstadosReuniaoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof EstadosReuniao) {
        	EstadosReuniao entity = (EstadosReuniao) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getEstado());
            return sb.toString();
        }

        return null;
    }
}
