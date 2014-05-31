package br.com.dsoo.facebook.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.logic.exceptions.AuthenticationFailedException;
import br.com.dsoo.facebook.view.Alert;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.conf.Configuration;
import facebook4j.conf.ConfigurationBuilder;

@SuppressWarnings("unused")
public class Authenticator{

	private TestUsers[] testUsers = {
			TestUsers.JOAO_DA_SILVA_SAURO,
			TestUsers.JERICO_SANTOS_PADILHA,
			TestUsers.LUIS_PRISCILLO_CAMPOS,
			TestUsers.RODOLFO_MARTINS,
			TestUsers.GERALDO_DE_SOUZA
	};

	private ConfigurationBuilder cb;
	private Configuration conf;
	private FacebookFactory ff;
	private Facebook facebook;
	private AccessToken token;


	public Authenticator(){
		cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
		.setOAuthAppId(AppData.AUTH_APP_ID.getValue())
		.setOAuthAppSecret(AppData.AUTH_APP_SECRET.getValue())
		.setOAuthPermissions(AppData.AUTH_APP_PERMISSIONS.getValue());

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

		// Usuário real com autenticação
		token = facebook.getOAuthAccessToken(Alert.showAuthenticationMessage(facebook));
		// Para usar um Usuário de teste, descomente a linha abaixo e comente a acima
		//token = new AccessToken(testUsers[(int)(((Math.random() * 100) + 1) / 25)].getToken());

		//token = this.getOAuthCredentials(facebook);

		facebook.setOAuthAccessToken(token);

		if(!facebook.getAuthorization().isEnabled()){
			throw new AuthenticationFailedException(token);
		}

		return facebook;
	}

	/**
	 * <b>EM CONSTRUÇÃO!</b><br>
	 * Solicita e lê o código de autenticação
	 * @param facebook
	 * @return Objeto AccessToken com os dados de autenticação
	 */
	private AccessToken getOAuthCredentials(Facebook facebook){
		return null;
	}

	/**
	 * Usuários de teste
	 * @author Raphael
	 *
	 */
	enum TestUsers{
		JOAO_DA_SILVA_SAURO("CAAHLgl4tP3YBAFdiZBKbR9CsZCZBpq1T7LfvXGbXSHRw12tp6nUjE00pfT7CeXWXDtfXTOwMZBPkZCISOeCr0hnX4PkymikMwuhz2ONzkm4HZADP0ZCswgvttNppOqXHD4PZBFDRSsxk2khEz2GamvZC1A9QaWOS7Ud4dZCfL9kWhCUSb5fr8SzNAAvhPkmx1uQKEZD"),
		JERICO_SANTOS_PADILHA("CAAHLgl4tP3YBAEpVcGesBZBwGRhchm018eO7qkVF7et2Scejz8SnKtNfRYI5Mh8RJyIAGZCzLFgnBDvWFPH7dmIyqZBBtjzZCZBhvtN5LkGq8cNoAEuZB7eZAiQ10E2CGBAC2yF0yu8PznmSZC8ZCIExaotU8ivUP0wxdBUtouUiywi3ZA42MDDWMlE5F0TZBYIg97mkH9jY6XjsgZDZD"),
		LUIS_PRISCILLO_CAMPOS("CAAHLgl4tP3YBAJHWVZA9zkppzn7MG2UaebZBZBGgih7717a9ayuiVrxnX8RPlQcDhZBSXMME2OBlBIIVANbDE6AjP7dWukZC3alaPwD9aOsILWcoLeJhqFkZAlMMuHPkLZAtzUjMCFWMZCojgu4GbKYH5g3QF5h5ZC4uZBS6C4hAejjnt3bmjHF5JdSVdISVCDwQkZD"),
		RODOLFO_MARTINS("CAAHLgl4tP3YBAKxC7VMW0ygORygtBzZCjJdgLe7bJHcGCVRG1BWWSCG0ocO6vW8BCKaHW0ObjkwHvuinYom1E2UKOLE8zq7HYcXXCRXJ1psRG0pEe3lLfGKqRh9QLTdiakQzE8GmdTiGUmjBV1JXH9zanijB45PU9Ye7v2fjcEGLvMhkjpopLCTjLc2sZD"),
		GERALDO_DE_SOUZA("CAAHLgl4tP3YBAPwCgNO3ptVgaQBWvhbh3WUABHdIfhw1NkXNZBxCWcFO5uGuOHovRXgIdlE4A1M9EVtZAHwWr4PML5ZBBSJs5r3ueL1WHspOHbw6SX18V44eXBBvRR81OiwOvgilCEt4XEXJJQSFm87yoSTKXRIwDHvUM4xN3VOi0PrQ4y1ZCA1z9GpTD34ZD");

		private String token;
		private TestUsers(String tkn){
			token = tkn;
		}

		public String getToken(){
			return token;
		}
	}

	/*
	 * Classes de comunicação com o servidor para requests
	 */
	class SigningServlet extends HttpServlet{
		private static final long serialVersionUID = 1L;

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Facebook facebook = new FacebookFactory().getInstance();
			request.getSession().setAttribute("facebook", facebook);
			StringBuffer callbackURL = request.getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append("/callback");
			response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
		}
	}
	
	class CallbackServlet extends HttpServlet{

		private static final long serialVersionUID = 1L;
		
		@Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
	        String oauthCode = request.getParameter("code");
	        try {
	            facebook.getOAuthAccessToken(oauthCode);
	        } catch (FacebookException e) {
	            throw new ServletException(e);
	        }
	        response.sendRedirect(request.getContextPath() + "/");
	    }
	}
}
