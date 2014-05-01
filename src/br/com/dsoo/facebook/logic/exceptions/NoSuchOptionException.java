package br.com.dsoo.facebook.logic.exceptions;

public class NoSuchOptionException extends RuntimeException{

	private static final long	serialVersionUID	= 1L;
	private char option;
	
	public NoSuchOptionException(char option){
		this.option = option;
	}
	
	@Override
	public String getMessage(){
		return "Opção inválida! (" + option + ")";
	}

}
