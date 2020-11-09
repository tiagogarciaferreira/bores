package com.tgfcodes.bores.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.beans.factory.annotation.Autowired;

import com.tgfcodes.bores.model.Produto;
import com.tgfcodes.bores.service.ProdutoService;

@FacesConverter(value = "produtoConverter", forClass = Produto.class)
public class ProdutoConverter implements Converter<Object> {

	@Autowired
	private ProdutoService produtoService;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null) {
			return null;
		}
		try {
			Long produtoId = Long.parseLong(value);
			return this.produtoService.buscarPorId(produtoId);
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
		Produto produto = (Produto) value;
		return String.valueOf(produto.getId());
	}

}
