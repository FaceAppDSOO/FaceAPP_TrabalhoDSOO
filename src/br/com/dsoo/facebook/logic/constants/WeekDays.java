package br.com.dsoo.facebook.logic.constants;

public enum WeekDays{
	SUNDAY("Domingo"), MONDAY("Segunda"), TUESDAY("Terça"), WEDNESDAY("Quarta"), THURSDAY("Quinta"), FRIDAY("Sexta"), SATURDAY("Sábado");

	private String value;
	private WeekDays(String v){
		value = v;
	}

	public String getValue(){
		return value;
	}
}
