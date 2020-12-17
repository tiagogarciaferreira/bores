package com.tgfcodes.bores.event.password;

import java.io.StringWriter;
import java.io.Writer;
import java.time.format.DateTimeFormatter;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tgfcodes.bores.event.PasswordResetEvent;
import com.tgfcodes.bores.event.util.VelocityEngineUtil;
import com.tgfcodes.bores.model.PasswordReset;
import com.tgfcodes.bores.repository.PasswordResetRepository;

@Component
@EnableAsync
public class PasswordResetMailListner {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Async
	@EventListener
	@Transactional(readOnly = true)
	public void enviarSolicitacao(PasswordResetEvent passwordResetEvent) throws Exception {
		VelocityEngine engine = VelocityEngineUtil.getVelocityEngine();
		Template template = engine.getTemplate("email/PasswordReset.vm", "UTF-8");
		PasswordReset passwordReset = this.passwordResetRepository.findById(passwordResetEvent.getPasswordReset().getId()).get();
		VelocityContext context = new VelocityContext();
		context.put("passwordReset", passwordReset);
		context.put("logo", "logo");
		context.put("formato", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
		String urlSolicitacao = passwordResetEvent.getUrl() + "?token=" + passwordReset.getToken() + "&cancelar=false";
		context.put("urlSolicitacao", urlSolicitacao);
		String urlCancelamento = passwordResetEvent.getUrl() + "?token=" + passwordReset.getToken() + "&cancelar=true";
		context.put("urlCancelamento", urlCancelamento);
		Writer writer = new StringWriter();
		template.merge(context, writer);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setFrom("tiagogarciaferreira.developer@gmail.com");
		helper.setTo(passwordReset.getUsuario().getEmail());
		helper.setSubject("Bores - Redefinir senha");
		helper.setText(String.valueOf(writer), true);
		Resource resourceLogo = (Resource) resourceLoader.getResource("classpath:/email/logo.png");
		helper.addInline("logo", resourceLogo);
		mailSender.send(mimeMessage);
	}
}
