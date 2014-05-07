package br.com.dsoo.facebook.logic;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import facebook4j.FacebookException;
import br.com.dsoo.facebook.user.User;


public class Services{

	private Emailer emailer;
	
	public Services() throws IOException{
		emailer = new Emailer();
		//CARREGA AS CONFIGURAÇÕES
	}

	/**
	 * Envia um email
	 * @param user Usuário ativo
	 * @param msg Mensage,
	 * @param to Destinatário
	 * @throws MessagingException 
	 * @throws AddressException
	 * @throws FacebookException 
	 * @throws IOException 
	 */
	public void sendActivitiesEmail(User user, String msg, String ... to) throws AddressException, MessagingException, FacebookException, IOException{
		emailer.sendEmail(msg, "Resumo de atividades de " + user.getFirstName(), to);
		Emailer.refreshConfig();
	}
}
