package org.serratec.java2backend.alterdatapac6.config;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class MailConfig {

	@Autowired
	JavaMailSender javaMail;

	public String sendEmail(String para, String assunto, String texto) throws MessagingException {

		MimeMessage mail = javaMail.createMimeMessage();

		mail.setSubject(assunto);

		MimeMessageHelper helper = new MimeMessageHelper(mail, true);

		helper.setFrom("alterdata.pac6@gmail.com");

		helper.setTo(para);

		helper.setText(texto, true);

		javaMail.send(mail);

		return "Verifique a caixa de entrada do e-mail cadastrado!";

	}
	
	
}
