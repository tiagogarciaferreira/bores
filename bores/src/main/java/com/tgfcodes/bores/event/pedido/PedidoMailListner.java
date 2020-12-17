package com.tgfcodes.bores.event.pedido;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.NumberTool;
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

import com.tgfcodes.bores.event.PedidoEvent;
import com.tgfcodes.bores.event.util.VelocityEngineUtil;
import com.tgfcodes.bores.model.Pedido;
import com.tgfcodes.bores.repository.PedidoRepository;

@Component
@EnableAsync
public class PedidoMailListner{

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private PedidoRepository pedidoRepository;

	@Async
	@EventListener
	@Transactional(readOnly = true)
	public void enviarPedido(PedidoEvent pedidoEvent) throws Exception {
		VelocityEngine engine = VelocityEngineUtil.getVelocityEngine();
		Template template = engine.getTemplate("email/ResumoPedido.vm", "UTF-8");
		Pedido pedido = this.pedidoRepository.findById(pedidoEvent.getPedido().getId()).get();
		VelocityContext context = new VelocityContext();
		context.put("pedido", pedido);
		context.put("subtotalPedido", pedido.getValorSubtotal());
		context.put("logo", "logo");
		context.put("produto", "produto");
		context.put("locale", new Locale("pt", "BR"));
		context.put("number", new NumberTool());
		Writer writer = new StringWriter();
		template.merge(context, writer);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		helper.setFrom("tiagogarciaferreira.developer@gmail.com");
		helper.setTo(pedido.getCliente().getEmail());
		helper.setSubject(String.format("Bores - Pedido nº %d", pedido.getId()));
		helper.setText(String.valueOf(writer), true);
		Resource resourceLogo = (Resource) resourceLoader.getResource("classpath:/email/logo.png");
		helper.addInline("logo", resourceLogo);
		Resource resourceProduto = (Resource) resourceLoader.getResource("classpath:/email/produto.png");
		helper.addInline("produto", resourceProduto);
		mailSender.send(mimeMessage);
	}
}
