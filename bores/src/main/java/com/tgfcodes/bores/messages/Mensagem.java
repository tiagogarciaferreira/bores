package com.tgfcodes.bores.messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

    public static void info(String mensagen) {
    	facesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, null, mensagen));
    }

    public static void alert(String mensagen) {
    	facesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, null, mensagen));
    }
    
    public static void error(String mensagen) {
    	facesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensagen));
    }
    
    private static FacesContext facesContext() {
    	return FacesContext.getCurrentInstance();
    }
     
}
