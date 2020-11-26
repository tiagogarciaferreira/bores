package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Grupo;
import com.tgfcodes.bores.service.GrupoService;

@FacesConverter(value = "grupoConverter", forClass = Grupo.class)
public class GrupoConverter implements Converter<Object> {

	@Autowired
	private GrupoService grupoService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long grupoId = Long.parseLong(value);
			return this.grupoService.buscarPorId(grupoId);
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
		Grupo grupo = (Grupo) value;
		return String.valueOf(grupo.getId());
	}

}
