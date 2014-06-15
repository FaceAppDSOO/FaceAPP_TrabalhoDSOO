package br.com.dsoo.facebook.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.dsoo.facebook.logic.constants.Time;

public class Utils{

	public Utils(){}
	
	/**
	 * M�todo similar ao <code>Date.toString()</code>, mas traduzido para o portugu�s.
	 * @param date
	 * @return Data formatada
	 */
	public static String dateToString(Date date, boolean translate){
		String[] timeStamp = date.toString().split(" ");
		String[] time = timeStamp[3].split(":");
		
		String hour = time[0];
		String minute = time[1];
		
		if(translate){
			String weekDay = translate(timeStamp[0]);
			String month = translate(timeStamp[1]);

			return weekDay + ", " + timeStamp[2] + " de " + month + " de " + timeStamp[5] + ", �s " + hour + ":" + minute;
		}
		
		return timeStamp[2] + "/" + timeStamp[1] + "/" + timeStamp[5] + " - " + hour + ":" + minute;
	}
	
	/**
	 * Traduz dias da semana e meses
	 * @param word
	 * @return Tradu��o
	 */
	public static String translate(String word){
		switch(word){
			// DIAS DA SEMANA
			case "Sun":
				return Time.WeekDays.SUNDAY.value;
				
			case "Mon":
				return Time.WeekDays.MONDAY.value;
				
			case "Tue":
				return Time.WeekDays.TUESDAY.value;
				
			case "Wed":
				return Time.WeekDays.WEDNESDAY.value;
				
			case "Thu":
				return Time.WeekDays.THURSDAY.value;
				
			case "Fri":
				return Time.WeekDays.FRIDAY.value;
				
			case "Sat":
				return Time.WeekDays.SATURDAY.value;

			
			// MESES
			case "Jan":
				return Time.Months.JANUARY.value;
				
			case "Feb":
				return Time.Months.FEBRUARY.value;
				
			case "Mar":
				return Time.Months.FEBRUARY.value;
				
			case "Apr":
				return Time.Months.APRIL.value;
				
			case "May":
				return Time.Months.MAY.value;
				
			case "Jun":
				return Time.Months.JUNE.value;
				
			case "Jul":
				return Time.Months.JULY.value;
				
			case "Aug":
				return Time.Months.AUGUST.value;
				
			case "Sep":
				return Time.Months.SEPTEMBER.value;
				
			case "Oct":
				return Time.Months.OCTOBER.value;
				
			case "Nov":
				return Time.Months.NOVEMBER.value;
				
			case "Dec":
				return Time.Months.DECEMBER.value;
				
			default:
				return "_" + word;
		}
	}
	
	/**
	 * Converte uma <code>String</code> timeStamp em data
	 * @param date - Data no formato <code>MM/dd/yyyy</code>
	 * @return Objeto <code>Date</code> com a data indicada na <code>String</code>
	 * @throws ParseException
	 */
	public static Date toDate(String date) throws ParseException{
		if(date == null)
			return null;
		
		String divider = "";
		
		for(int i = 0; i < date.length(); i++){
			if(!Character.isDigit(date.charAt(i))){
				divider += date.charAt(i);
				break;
			}
		}
		
		String[] timeStamp = date.split("T");
		timeStamp = timeStamp[0].split(divider);
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		
		return formatter.parse(timeStamp[0] + "/" + timeStamp[1] + "/" + timeStamp[2]);
	}
	
	/**
	 * Retorna a diferen�a de tempo entre as duas datas fornecidas.<br><br>
	 * 
	 * A fun��o aceita os seguintes par�metros:<br>
	 * <ul>
	 * <li><code>seconds</code>: Diferen�a em <b>segundos</b></li><br>
	 * <li><code>minutes</code>: Diferen�a em <b>minutos</b></li><br>
	 * <li><code>hours</code>: Diferen�a em <b>horas</b></li><br>
	 * <li><code>days</code>: Diferen�a em <b>dias (Padr�o)</b></li><br>
	 * <li><code>weeks</code>: Diferen�a em <b>semanas</b></li><br>
	 * <li><code>months</code>: Diferen�a em <b>meses</b></li><br>
	 * <li><code>years</code>: Diferen�a em <b>anos</b></li>
	 * </ul>
	 * @param beforeDay
	 * @param afterDate
	 * @return Diferen�a de tempo entre as datas fornecidas (<b>0</b> se n�o houver diferen�a)
	 */
	public static long differenceBetweenDates(Date beforeDate, Date afterDate, Time param){
		if(beforeDate == null || afterDate == null)
			return -1;
		
		if(beforeDate.getTime() == afterDate.getTime())
			return 0;
		
		long diff = afterDate.getTime() - beforeDate.getTime();
		
		return diff / param.value;
	}
	
	public static Date getDateFromDifference(long diff, Time param){
		Date date = new Date();
		date.setTime(date.getTime() - (diff * param.value));
		
		return date;
	}
}
