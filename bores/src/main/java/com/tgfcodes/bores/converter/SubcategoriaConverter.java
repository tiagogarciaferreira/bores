package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.springframework.beans.factory.annotation.Autowired;
import com.tgfcodes.bores.model.Subcategoria;
import com.tgfcodes.bores.service.SubcategoriaService;

@FacesConverter(value = "subcategoriaConverter", forClass = Subcategoria.class)
public class SubcategoriaConverter implements Converter<Object> {

	@Autowired
	private SubcategoriaService subcategoriaService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long categoriaId = Long.parseLong(value);
			return this.subcategoriaService.buscarPorId(categoriaId);
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
		Subcategoria subcategoria = (Subcategoria) value;
		return String.valueOf(subcategoria.getId());
	}

}
