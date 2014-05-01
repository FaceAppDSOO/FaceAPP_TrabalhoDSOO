package br.com.dsoo.facebook.logic.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.dsoo.facebook.logic.constants.Time;

public class Utils{

	public Utils(){}

	/**
	 * Converte uma <code>String</code> timeStamp em data
	 * @param date
	 * @return Objeto <code>Date</code> com a data da <code>String</code>
	 * @throws ParseException
	 */
	public static Date toDate(String date) throws ParseException{
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
		
		return formatter.parse(timeStamp[1] + "/" + timeStamp[2] + "/" + timeStamp[0]);
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
		if(beforeDate.getTime() == afterDate.getTime())
			return 0;
		
		long diff = afterDate.getTime() - beforeDate.getTime();
		
		return diff / param.getValue();
	}
}
