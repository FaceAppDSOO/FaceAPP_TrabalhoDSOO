package br.com.dsoo.facebook.logic.exceptions;

public class TypeMismatchException extends Exception{

	private static final long serialVersionUID = 1L;
	private Object obj;
	
	public TypeMismatchException(Object obj){
		this.obj = obj;
	}

	@Override
	public String getMessage(){
		return "Tipo de objeto inválido (" + this.obj.toString() + ")";
	}
}
