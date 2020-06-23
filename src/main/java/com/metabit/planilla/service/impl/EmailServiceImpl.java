package com.metabit.planilla.service.impl;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.metabit.planilla.entity.Email;
import com.metabit.planilla.service.EmailService;

@Service("emailServiceImpl")
public class EmailServiceImpl implements EmailService{

	@Autowired
    private JavaMailSender emailSender;
	@Autowired
    private SpringTemplateEngine templateEngine;
	
	@Override
	public void sendEmail(Email email) {
		// TODO Auto-generated method stub
		try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(email.getModel());
            String html = templateEngine.process("email-template", context);

            helper.setTo(email.getTo());
            helper.setText(html, true);
            helper.setSubject(email.getSubject());
            helper.setFrom(email.getFrom());

            emailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
		
	}
	
}
