package br.com.dsoo.facebook.logic.constants;

public enum WeekDays{
	SUNDAY("Domingo"), MONDAY("Segunda"), TUESDAY("Ter�a"), WEDNESDAY("Quarta"), THURSDAY("Quinta"), FRIDAY("Sexta"), SATURDAY("S�bado");

	private String value;
	private WeekDays(String v){
		value = v;
	}

	public String getValue(){
		return value;
	}
}
