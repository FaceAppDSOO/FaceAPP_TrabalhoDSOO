package br.com.dsoo.facebook.logic;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.dsoo.facebook.logic.constants.AppData;

public class Emailer{

	private static Properties mailServerConfig = new Properties();
	
	public static void refreshConfig() throws IOException {
	    mailServerConfig.clear();
	    fetchConfig();
	  }
	
	private static void fetchConfig() throws IOException{
		mailServerConfig.put("mail.smtp.auth", "true");
		mailServerConfig.put("mail.smtp.starttls.enable", "true");
		mailServerConfig.put("mail.smtp.host", "smtp.gmail.com");
		mailServerConfig.put("mail.smtp.port", "587");
	}
	
	public Emailer() throws IOException{
		fetchConfig();
	}
	
	public void sendEmail(String msg, String subject, String ... to) throws AddressException, MessagingException{
		Session session = Session.getInstance(mailServerConfig, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AppData.APP_EMAIL.value, AppData.APP_EMAIL_PASS.value);
			}
		});
		
		Message message = new MimeMessage(session);
		
		message.setSubject(subject);
		message.setText(msg);
		
		for(String rec : to){
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(rec));
		}
		
		Transport.send(message);
	}

}
