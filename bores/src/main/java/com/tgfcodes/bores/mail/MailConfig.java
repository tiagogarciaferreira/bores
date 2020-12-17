package com.tgfcodes.bores.mail;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

	@Autowired
	private Environment environment;
	
	@Bean
	public JavaMailSender mailSender() throws IOException {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(environment.getProperty("mailjet.mail.host"));
		mailSender.setPort(Integer.parseInt(environment.getProperty("mailjet.mail.port")));
		mailSender.setUsername(environment.getProperty("mailjet.mail.username"));
		mailSender.setPassword(environment.getProperty("mailjet.mail.password"));
		Properties properties = new Properties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", true);
		properties.put("mail.smtp.starttls.enable", true);
		properties.put("mail.debug", false);
		properties.put("mail.smtp.socketFactory.port", "587");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.connectiontimeout", 10000);
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
	
}
