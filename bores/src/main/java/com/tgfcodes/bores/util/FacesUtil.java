package com.tgfcodes.bores.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

public class FacesUtil {

	public static String getParameter(String paramName){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		var paramValue = facesContext.getExternalContext().getRequestParameterMap().get(paramName);
		return (paramValue != null) ? paramValue : null;
	}
	
	public static String getUrlResetPassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return request.getRequestURL().toString();
	}
}
