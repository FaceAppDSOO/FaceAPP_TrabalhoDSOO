package br.com.dsoo.facebook.logic;

import java.io.IOException;
import java.util.Date;

public class Logger{

	private final String file;
	
	public Logger(String file){
		this.file = file;
	}
	
	public void log(String text){
		try{
			Utils.writeToFile("\\logs", "system_log",
					Utils.dateToString(new Date(), false) + " - " + file + ":\n" + text,
					true);
		}catch(IOException e){}
	}
	
}
