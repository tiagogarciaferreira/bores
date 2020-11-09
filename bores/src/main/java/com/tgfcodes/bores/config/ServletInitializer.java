package com.tgfcodes.bores.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class ServletInitializer implements ServletContextInitializer {
	

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
        servletContext.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
        servletContext.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
        servletContext.setInitParameter("facelets.DEVELOPMENT", "true");
        servletContext.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
        servletContext.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", "true");
        servletContext.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
        servletContext.setInitParameter("primefaces.FONT_AWESOME", "true");
        servletContext.setInitParameter("primefaces.THEME", "nova-light");
        servletContext.setInitParameter("primefaces.UPLOADER", "auto");
		servletContext.setInitParameter("primefaces.SUBMIT", "partial");
	}

}
