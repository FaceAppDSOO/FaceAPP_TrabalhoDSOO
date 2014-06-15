package br.com.dsoo.facebook.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.dsoo.facebook.logic.constants.Time;

public class Utils{

	public Utils(){}
	
	/**
	 * Método similar ao <code>Date.toString()</code>, mas traduzido para o português.
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

			return weekDay + ", " + timeStamp[2] + " de " + month + " de " + timeStamp[5] + ", às " + hour + ":" + minute;
		}
		
		return timeStamp[2] + "/" + timeStamp[1] + "/" + timeStamp[5] + " - " + hour + ":" + minute;
	}
	
	/**
	 * Traduz dias da semana e meses
	 * @param word
	 * @return Tradução
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
	 * Retorna a diferença de tempo entre as duas datas fornecidas.<br><br>
	 * 
	 * A função aceita os seguintes parâmetros:<br>
	 * <ul>
	 * <li><code>seconds</code>: Diferença em <b>segundos</b></li><br>
	 * <li><code>minutes</code>: Diferença em <b>minutos</b></li><br>
	 * <li><code>hours</code>: Diferença em <b>horas</b></li><br>
	 * <li><code>days</code>: Diferença em <b>dias (Padrão)</b></li><br>
	 * <li><code>weeks</code>: Diferença em <b>semanas</b></li><br>
	 * <li><code>months</code>: Diferença em <b>meses</b></li><br>
	 * <li><code>years</code>: Diferença em <b>anos</b></li>
	 * </ul>
	 * @param beforeDay
	 * @param afterDate
	 * @return Diferença de tempo entre as datas fornecidas (<b>0</b> se não houver diferença)
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
