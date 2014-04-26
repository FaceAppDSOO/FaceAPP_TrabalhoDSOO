package br.com.dsoo.facebook;

import br.com.dsoo.facebook.settings.Authenticator;
import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.FacebookException;

public class FaceApp {

	public static void main(String[] args) {

		Authenticator auth = new Authenticator();

		User user = null;
		try{
			user = new User(auth.authenticate());
		}catch(FacebookException e1){
			e1.printStackTrace();
		}
		
		Ui.show("Conexão efetuada!");

		try {
			Ui.show("Nome: " + user.getName() + "\nNúmero de amigos: " + user.getFriends().length);
		} catch (FacebookException e) {
			e.printStackTrace();
		}

	}

}