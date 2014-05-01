package br.com.dsoo.facebook;

import java.text.ParseException;

import br.com.dsoo.facebook.logic.exceptions.AuthenticationFailedException;
import br.com.dsoo.facebook.user.Authenticator;
import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.FacebookException;

public class FaceApp {

	public static void main(String[] args) {

		Ui ui = new Ui();
		Authenticator auth = new Authenticator();

		User user = null;
		do{
			try{
				user = new User(auth.authenticate());
			}catch(FacebookException e1){
				e1.printStackTrace();
			}catch(AuthenticationFailedException e){
				ui.show(e.getMessage());
			}
		}while(!user.isAuthenticated());
		
		try{
			ui.showOptions(user);
		}catch(IllegalStateException | FacebookException | ParseException e){
			ui.show(e.getMessage());
		}finally{
			user.logout();
			System.exit(0);
		}
		
	}

}