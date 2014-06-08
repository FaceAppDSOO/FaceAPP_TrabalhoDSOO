package br.com.dsoo.facebook;

import java.io.IOException;

import br.com.dsoo.facebook.logic.exceptions.AuthenticationFailedException;
import br.com.dsoo.facebook.user.Authenticator;
import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import br.com.dsoo.facebook.view.frames.FaceAppFrame;
import facebook4j.FacebookException;

public class FaceApp {

	public static void main(String[] args){

		User user = null;
		
		Authenticator auth = new Authenticator();
				
		do{
			try{
				new FaceAppFrame(user = new User(auth.authenticate()));
			}catch(FacebookException e){
				Alert.showError(e);
			}catch(AuthenticationFailedException | IOException e){
				Alert.showError(e);
				System.exit(0);
			}
		}while(user == null || !user.isAuthenticated());
		
//		Ui ui = new Ui();
//
//		do{
//			try{
//				user = new User(auth.authenticate());
//			}catch(FacebookException e1){
//				e1.printStackTrace();
//			}catch(AuthenticationFailedException e){
//				ui.show(e.getMessage());
//			}catch(IOException e){
//				ui.show(e.getMessage());
//			}
//		}while(!user.isAuthenticated());
//		
//		try{
//			ui.showOptions(user);
//		}catch(IllegalStateException | FacebookException | ParseException | MessagingException | IOException e){
//			ui.show(e.getMessage());
//		}finally{
//			user.logout();
//			System.exit(0);
//		}
		
	}

}