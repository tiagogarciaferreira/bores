package com.tgfcodes.bores.exception;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import com.tgfcodes.bores.messages.Mensagem;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	@SuppressWarnings("deprecation")
	public JsfExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable exception = context.getException();
			NegocioException negocioException = this.getNegocioException(exception);
			boolean handled = false;
			try {
				if (exception instanceof ViewExpiredException) {
					handled = true;
					this.redirect("/admin/Dashboard.xhtml");
				} else if (negocioException != null) {
					handled = true;
					Mensagem.error("Aviso: ", negocioException.getMessage());
				} else {
					handled = true;
					//this.redirect("/error/500.xhtml");
				}
			} finally {
				if (handled) {
					events.remove();
				}
			}
		}
		this.getWrapped().handle();
	}

	private NegocioException getNegocioException(Throwable exception) {
		if (exception instanceof NegocioException) {
			return (NegocioException) exception;
		} else if (exception.getCause() != null) {
			return this.getNegocioException(exception.getCause());
		}
		return null;
	}

	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();
			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}