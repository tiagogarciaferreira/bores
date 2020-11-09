package com.tgfcodes.bores.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory wrapped;

	@SuppressWarnings("deprecation")
	public JsfExceptionHandlerFactory(final ExceptionHandlerFactory wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfExceptionHandler(wrapped.getExceptionHandler());
	}

	@Override
	public ExceptionHandlerFactory getWrapped() {
		return wrapped;
	}
}
