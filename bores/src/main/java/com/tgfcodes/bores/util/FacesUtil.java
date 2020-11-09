package com.tgfcodes.bores.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

	public static String getParameter(String paramName){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		var paramValue = facesContext.getExternalContext().getRequestParameterMap().get(paramName);
		return (paramValue != null) ? paramValue : null;
	}
}
