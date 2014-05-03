package br.com.dsoo.facebook.logic;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import facebook4j.FacebookException;
import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.user.User;

public class Services{

	public Services(){
		//CARREGA AS CONFIGURAÇÕES
	}

	/**
	 * Envia um email
	 * @param user Usuário ativo
	 * @param msg Mensage,
	 * @param to Destinatário
	 * @throws EmailException
	 * @throws FacebookException
	 */
	public void sendEmail(User user, String msg, String ... to) throws EmailException, FacebookException{
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setFrom(AppData.APP_EMAIL.getValue());
		email.addTo(to);
		email.setSubject("Resumo de atividades de " + user.getFirstName());
		email.setMsg(msg);
		email.setAuthentication(AppData.APP_EMAIL_LOGIN.getValue(), AppData.APP_EMAIL_PASS.getValue());
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.send();
	}
}
