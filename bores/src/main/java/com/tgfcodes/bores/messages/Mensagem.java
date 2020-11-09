package com.tgfcodes.bores.messages;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {

    public static void info(String entidade, String mensagen) {
    	facesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, entidade, mensagen));
    }

    public static void error(String entidade, String mensagen) {
    	facesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, entidade, mensagen));
    }
    
    private static FacesContext facesContext() {
    	return FacesContext.getCurrentInstance();
    }
     
}
