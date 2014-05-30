package br.com.dsoo.facebook.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import facebook4j.Event;
import br.com.dsoo.facebook.logic.constants.Months;
import br.com.dsoo.facebook.logic.constants.Time;
import br.com.dsoo.facebook.logic.constants.WeekDays;

public class Utils{

	public Utils(){}
	
	/**
	 * Método similar ao <code>Date.toString()</code>, mas traduzido para o português.
	 * @param date
	 * @return Data formatada
	 */
	public static String dateToString(Date date){
		String[] timeStamp = date.toString().split(" ");
		String[] time = timeStamp[3].split(":");
		
		String hour = time[0];
		String minute = time[1];
		String weekDay = translate(timeStamp[0]);
		String month = translate(timeStamp[1]);
		
		return weekDay + ", " + timeStamp[2] + " de " + month + " de " + timeStamp[5] + ", às " + hour + ":" + minute;
	}
	
	/**
	 * 
	 * @param events
	 * @return String formatada com os eventos
	 */
	public static String formatEvents(ArrayList<Event> events){
		String str = "";
		
		for(int i = 0; i < events.size(); i++){
			str += events.get(i).getName() + ": " + Utils.dateToString(events.get(i).getStartTime()) + "\n";
		}
		
		return str.substring(0, str.length() - 1);
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
				return WeekDays.SUNDAY.getValue();
				
			case "Mon":
				return WeekDays.MONDAY.getValue();
				
			case "Tue":
				return WeekDays.TUESDAY.getValue();
				
			case "Wed":
				return WeekDays.WEDNESDAY.getValue();
				
			case "Thu":
				return WeekDays.THURSDAY.getValue();
				
			case "Fri":
				return WeekDays.FRIDAY.getValue();
				
			case "Sat":
				return WeekDays.SATURDAY.getValue();

			
			// MESES
			case "Jan":
				return Months.JANUARY.getValue();
				
			case "Feb":
				return Months.FEBRUARY.getValue();
				
			case "Mar":
				return Months.FEBRUARY.getValue();
				
			case "Apr":
				return Months.APRIL.getValue();
				
			case "May":
				return Months.MAY.getValue();
				
			case "Jun":
				return Months.JUNE.getValue();
				
			case "Jul":
				return Months.JULY.getValue();
				
			case "Aug":
				return Months.AUGUST.getValue();
				
			case "Sep":
				return Months.SEPTEMBER.getValue();
				
			case "Oct":
				return Months.OCTOBER.getValue();
				
			case "Nov":
				return Months.NOVEMBER.getValue();
				
			case "Dec":
				return Months.DECEMBER.getValue();
				
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
		
		return diff / param.getValue();
	}
}
