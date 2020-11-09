package com.tgfcodes.bores.exception;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class FacesHandlerException {

	@Bean
	public FilterRegistrationBean<FacesExceptionFilter> filterRegisBean() {
		final FilterRegistrationBean<FacesExceptionFilter> filterRegBean = new FilterRegistrationBean<FacesExceptionFilter>();
		filterRegBean.setFilter(new FacesExceptionFilter());
		filterRegBean.addUrlPatterns("/*");
		filterRegBean.setEnabled(Boolean.TRUE);
		return filterRegBean;
	}
	
	@Bean
	public ErrorPageRegistrar errorPageRegistrar() {
		return new ErrorPageRegistrar() {
			@Override
			public void registerErrorPages(ErrorPageRegistry registry) {
				registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.xhtml"));
				registry.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error/403.xhtml"));
				registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500.xhtml"));
			}
		};
	}

}