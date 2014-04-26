package br.com.dsoo.facebook.settings;

import br.com.dsoo.facebook.logic.exceptions.AuthenticationFailedException;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

public class Authenticator{

	private ConfigurationBuilder cb;
	private Configuration conf;
	private FacebookFactory ff;
	private Facebook facebook;
	
	public Authenticator(){
		cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
		.setOAuthAppId(Constants.AUTH_APP_ID)
		.setOAuthAppSecret(Constants.AUTH_APP_SECRET)
		.setOAuthPermissions(Constants.AUTH_APP_PERMISSIONS);

		conf = cb.build();
	}
	
	public Facebook authenticate() throws FacebookException, AuthenticationFailedException{
		ff = new FacebookFactory(conf);
		facebook = ff.getInstance();
		
		AccessToken token = facebook.getOAuthAccessToken(Ui.showAuthenticationDialog(facebook));

		facebook.setOAuthAccessToken(token);
		
		if(!facebook.getAuthorization().isEnabled()){
			throw new AuthenticationFailedException(token);
		}
		
		return facebook;
	}

}
