package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import com.tgfcodes.bores.model.Cliente;
import com.tgfcodes.bores.service.ClienteService;

@FacesConverter(value = "clienteConverter", forClass = Cliente.class)
public class ClienteConverter implements Converter<Object> {

	@Autowired
	private ClienteService clienteService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long clienteId = Long.parseLong(value);
			return this.clienteService.buscarPorId(clienteId);
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
		Cliente cliente = (Cliente) value;
		return String.valueOf(cliente.getId());
	}

}
