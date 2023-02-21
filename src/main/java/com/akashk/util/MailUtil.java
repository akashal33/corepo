package com.akashk.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sun.istack.ByteArrayDataSource;

@Component
public class MailUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;

	public boolean sendMail(String to,String subject,String body,byte[] file) throws Exception {
		
		System.out.println(body);
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = null;
		boolean flag = false;
		try {
		mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
		mimeMessageHelper.setFrom("akashk33@hotmail.com");
		mimeMessageHelper.setTo(to);
		// body as html
		mimeMessageHelper.setText(body,true);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.addAttachment("notice.pdf", new ByteArrayDataSource(file, MediaType.APPLICATION_PDF_VALUE));
		javaMailSender.send(mimeMessage);
		flag = true;
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("error mail");
			flag = false;
			throw new Exception(e);
		}
		System.out.println("mail flag "+flag);
		return flag;
		
	}
	
}
