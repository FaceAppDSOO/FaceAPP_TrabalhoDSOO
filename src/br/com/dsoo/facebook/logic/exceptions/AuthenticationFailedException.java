package br.com.dsoo.facebook.logic.exceptions;

import facebook4j.auth.AccessToken;

public class AuthenticationFailedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private AccessToken token;

	public AuthenticationFailedException(){}
	
	public AuthenticationFailedException(AccessToken token){
		this.token = token;
	}

	public String getMessage(){
		if(token != null)
			return "Falha ao obter autoriza��o (" + token.toString() + ")";
		else
			return "Login cancelado pelo usu�rio";
	}
}
