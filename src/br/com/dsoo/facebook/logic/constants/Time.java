package br.com.dsoo.facebook.logic.constants;

public enum Time{
	SECONDS(1000),
	MINUTES(60 * 1000),
	HOURS(1000 * 60 * 60),
	DAYS(1000 * 60 * 60 * 24),
	WEEKS(1000 * 60 * 60 * 24 * 7),
	MONTHS(1000 * 60 * 60 * 24 * 30),
	YEARS(32605672207L);

	private long value;
	Time(long v){
		value = v;
	}
	
	public long getValue(){
		return value;
	}
}
