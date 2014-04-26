package br.com.dsoo.facebook.user;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;

public class User{

	Facebook fb;
	Object[] friends;
	
	public User(Facebook f) throws FacebookException{
		this.fb = f;
		friends = fb.friends().getFriends().toArray();
	}

	public String getName() throws IllegalStateException, FacebookException{
		return fb.getName();
	}
	
	public Object[] getFriends(){
		return friends;
	}
	
	public AccessToken getAuthToken(){
		return fb.getOAuthAccessToken();
	}
}
