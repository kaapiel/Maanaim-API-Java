package br.com.celula.converter;

import javax.faces.convert.FacesConverter;

import br.com.celula.entidade.Membro;

@FacesConverter("membroConverter")
public class MembroConverter extends AbstractConverter{
	
    @Override
    public String getConversionId(Object value) {
        if (value instanceof Membro) {
        	Membro entity = (Membro) value;
            StringBuilder sb = new StringBuilder();
            sb.append(entity.getClass().getSimpleName());
            sb.append("@");
            sb.append(entity.getNome());
            return sb.toString();
        }

        return null;
    }
}
