package com.ivanmoreno.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ivanmoreno.entities.Email;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendEmail(Email email)  {
		StringBuilder subjectMod = new StringBuilder();
		subjectMod.append("Confirmation email | ").append(email.getSubject());
		
		email.setMessage(createBody(email));
		
		return sendEmailTool(email.getEmail(), subjectMod.toString(), email.getMessage());
	}

	private String createBody(Email email) {
		StringBuilder bodyMod = new StringBuilder();
		bodyMod.append("Hi ")
		.append(email.getName())
		.append(". \nThis is just an email confirmation.")
		.append("\nThanks for contacting me, I'll answer you as soon as possible.\nHave a nice day!")
		.append("\n\n\n\nIvan Moreno");
		return bodyMod.toString();
	}
	
	private boolean sendEmailTool(String to,String subject, String body) {
		
		boolean send = false;
		 SimpleMailMessage mail = new SimpleMailMessage();
	        try {
				mail.setTo(to);
				mail.setSubject(subject);
				mail.setText(body);
				javaMailSender.send(mail);
				send = true;
			} catch (MailException e) {
				send = false;
				e.printStackTrace();
			}
	        return send;
	}
	
	public boolean sendEmailWithAttachment(String to,String subject, String body) {

		boolean send = false;
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

//			ClassPathResource classPathResource = new ClassPathResource("SOFTCORP.pdf");
//			helper.addAttachment(classPathResource.getFilename(), classPathResource);
			javaMailSender.send(mimeMessage);
			send = true;
		} catch (MailException | MessagingException e) {
			send = false;
			e.printStackTrace();
		}
		
		return send;
	}
}
