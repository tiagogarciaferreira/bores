package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Usuario;
import com.tgfcodes.bores.service.UsuarioService;

@FacesConverter(value = "usuarioConverter", forClass = Usuario.class)
public class UsuarioConverter implements Converter<Object> {

	@Autowired
	private UsuarioService usuarioService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long usuarioId = Long.parseLong(value);
			return this.usuarioService.buscarPorId(usuarioId);
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
		Usuario usuario = (Usuario) value;
		return String.valueOf(usuario.getId());
	}

}
