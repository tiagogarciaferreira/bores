package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import com.tgfcodes.bores.model.Categoria;
import com.tgfcodes.bores.service.CategoriaService;

@FacesConverter(value = "categoriaConverter", forClass = Categoria.class)
public class CategoriaConverter implements Converter<Object> {

	@Autowired
	private CategoriaService categoriaService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long categoriaId = Long.parseLong(value);
			return this.categoriaService.buscarPorId(categoriaId);
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
		Categoria categoria = (Categoria) value;
		return String.valueOf(categoria.getId());
	}

}
