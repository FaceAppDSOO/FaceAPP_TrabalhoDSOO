package br.com.dsoo.facebook.logic.constants;

public enum Months{
	JANUARY("Janeiro"),
	FEBRUARY("Fevereiro"),
	MARCH("Março"),
	APRIL("Abril"),
	MAY("Maio"),
	JUNE("Junho"),
	JULY("Julho"),
	AUGUST("Agosto"),
	SEPTEMBER("Setembro"),
	OCTOBER("Outubro"),
	NOVEMBER("Novembro"),
	DECEMBER("Dezembro");

	private String value;
	Months(String v){
		value = v;
	}

	public String getValue(){
		return value;
	}
}
