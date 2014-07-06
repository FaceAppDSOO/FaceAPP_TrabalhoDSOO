package br.com.dsoo.facebook.logic;

import java.io.IOException;
import java.util.Date;

import br.com.dsoo.facebook.logic.utils.Utils;
import br.com.dsoo.facebook.user.FileManager;

public class Logger{

	private final String userId, file;
	
	public Logger(String userId, String file){
		this.userId = userId;
		this.file = file;
	}
	
	public void log(String text){
		try{
			FileManager.writeToFile(
					FileManager.LOGS_PATH,
					FileManager.SYSTEM_LOG + userId + FileManager.LOGS_EXTENSION,
					Utils.dateToString(new Date(), false) + "\n" + file + " - " + text + "\n\n", true);
		}catch(IOException e){}
	}
	
	public void log(String text, String itemId){
		log(text + "(" + itemId + ")");
	}
	
}
