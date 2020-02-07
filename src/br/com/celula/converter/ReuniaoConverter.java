package br.com.celula.converter;

import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.Reuniao;

@FacesConverter("reuniaoConverter")
public class ReuniaoConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {

        if (value instanceof Reuniao) {
        	Reuniao entity = (Reuniao) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getIdreuniao());
            return sb.toString();
        }

        return null;
    }
}
