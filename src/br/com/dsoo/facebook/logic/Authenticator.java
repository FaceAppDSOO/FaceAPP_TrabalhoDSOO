package br.com.dsoo.facebook.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.logic.exceptions.AuthenticationFailedException;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

@SuppressWarnings("unused")
public class Authenticator{

	private ConfigurationBuilder cb;
	private Configuration conf;
	private FacebookFactory ff;
	private Facebook facebook;
	private AccessToken token;
	
	private Ui ui;

	
	public Authenticator(){
		ui = new Ui();
		
		cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
		.setOAuthAppId(AppData.AUTH_APP_ID.getProperty())
		.setOAuthAppSecret(AppData.AUTH_APP_SECRET.getProperty())
		.setOAuthPermissions(AppData.AUTH_APP_PERMISSIONS.getProperty());

		conf = cb.build();
	}
	
	/**
	 * Autentica o usuário.
	 * @return Objeto Facebook
	 * @throws FacebookException
	 * @throws AuthenticationFailedException
	 */
	public Facebook authenticate() throws FacebookException, AuthenticationFailedException{
		ff = new FacebookFactory(conf);
		facebook = ff.getInstance();
		
		token = facebook.getOAuthAccessToken(ui.showAuthenticationMessage(facebook));
		//token = this.getOAuthCredentials(facebook);
		
		facebook.setOAuthAccessToken(token);
		
		if(!facebook.getAuthorization().isEnabled()){
			throw new AuthenticationFailedException(token);
		}
		
		return facebook;
	}
	
	/**
	 * <b>EM CONSTRUÇÃO!</b>
	 * Solicita e lê o código de autenticação
	 * @param facebook
	 * @return Objeto AccessToken com os dados de autenticação
	 */
	private AccessToken getOAuthCredentials(Facebook facebook){
		String authUrl = facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.getProperty());

		URL url = null;
		BufferedReader reader = null;
		StringBuilder stringBuilder;

		try{
			url = new URL(authUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setReadTimeout(15000);
			connection.connect();

			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			stringBuilder = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null){
				stringBuilder.append(line + "\n");
			}

			return facebook.getOAuthAccessToken(stringBuilder.toString());
		}catch (Exception e){
			ui.show(e.getMessage());
			return null;
		}finally{
			if (reader != null){
				try{
					reader.close();
				}catch (IOException ioe){
					ui.show(ioe.getMessage());
				}
			}
		}
	}


}
