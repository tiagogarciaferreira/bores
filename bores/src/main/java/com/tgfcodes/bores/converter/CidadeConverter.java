package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Cidade;
import com.tgfcodes.bores.service.CidadeService;

@FacesConverter(value = "cidadeConverter", forClass = Cidade.class)
public class CidadeConverter implements Converter<Object> {

	@Autowired
	private CidadeService cidadeService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long cidadeId = Long.parseLong(value);
			return this.cidadeService.buscarPorId(cidadeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		Cidade cidade = (Cidade) value;
		return String.valueOf(cidade.getId());
	}

}
