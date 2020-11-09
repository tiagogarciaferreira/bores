package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Estado;
import com.tgfcodes.bores.service.EstadoService;

@FacesConverter(value = "estadoConverter", forClass = Estado.class)
public class EstadoConverter implements Converter<Object> {

	@Autowired
	private EstadoService estadoService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long estadoId = Long.parseLong(value);
			return this.estadoService.buscarPorId(estadoId);
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
		Estado estado = (Estado) value;
		return String.valueOf(estado.getId());
	}

}
