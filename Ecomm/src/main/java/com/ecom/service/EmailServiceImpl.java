package com.ecom.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ecom.beans.EmailToCustomer;

@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
    JavaMailSender mailSender;
	@Override
	public void sendEmail(EmailToCustomer email) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(email.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(email.getMailFrom(), "santhoshguggilla125@gmail.com"));
            mimeMessageHelper.setTo(email.getMailTo());
            mimeMessageHelper.setText(email.getMailContent());
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

		
	}

}
