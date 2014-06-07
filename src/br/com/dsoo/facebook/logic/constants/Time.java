package br.com.dsoo.facebook.logic.constants;

public enum Time{
	SECONDS(1000),
	MINUTES(60 * 1000),
	HOURS(1000 * 60 * 60),
	DAYS(1000 * 60 * 60 * 24),
	WEEKS(1000 * 60 * 60 * 24 * 7),
	MONTHS(1000 * 60 * 60 * 24 * 30),
	YEARS(32605672207L);

	public final long value;
	Time(long v){
		value = v;
	}
	
	public static enum WeekDays{
		SUNDAY("Domingo"),
		MONDAY("Segunda"),
		TUESDAY("Terça"),
		WEDNESDAY("Quarta"),
		THURSDAY("Quinta"),
		FRIDAY("Sexta"),
		SATURDAY("Sábado");

		public final String value;
		private WeekDays(String v){
			value = v;
		}
	}
	
	public static enum Months{
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

		public final String value;
		Months(String v){
			value = v;
		}
	}
}
