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
		if(beforeDate.getTime() == afterDate.getTime())
			return 0;
		
		long diff = afterDate.getTime() - beforeDate.getTime();
		
		return diff / param.getValue();
	}
}
