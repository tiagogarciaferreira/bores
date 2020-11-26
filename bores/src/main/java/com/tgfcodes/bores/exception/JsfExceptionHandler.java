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

import org.apache.commons.lang3.exception.ExceptionUtils;

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
			NegocioException negocioException = (NegocioException) ExceptionUtils.getRootCause(exception);
			boolean handled = false;
			try {
				if (exception instanceof ViewExpiredException) {
					handled = true;
					this.redirect("/admin/Dashboard.xhtml");
				} else if (negocioException != null) {
					handled = true;
					String message[] = negocioException.getMessage().split(":");
					Mensagem.error(message[0]+":", message[1]);
				} else {
					handled = true;
					this.redirect("/error/500.xhtml");
				}
			} finally {
				if (handled) {
					events.remove();
				}
			}
		}
		this.getWrapped().handle();
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