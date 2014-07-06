package br.com.dsoo.facebook.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class FileManager{
	
	public static final String LOGS_PATH = "logs\\";
	public static final String SETTINGS_PATH = "settings\\";
	
	public static final String SYSTEM_LOG = "system_log_";
	public static final String MISC_LOG = "misc_log_";
	public static final String PHOTOS_LOG = "photo_log.log";
	public static final String SETTINGS = "settings_";
	public static final String PHOTO = "photo_";
	
	public static final String LOGS_EXTENSION = ".log";
	public static final String SETTINGS_EXTENSION = ".data";
	public static final String PHOTO_EXTENSION = ".jpg";

	
	public static void writeToFile(String filePath, String fileNameAndExtension, String content, boolean append) throws FileNotFoundException, IOException{
		File path = new File(filePath);
		path.mkdirs();
		
		FileOutputStream out = new FileOutputStream(filePath + fileNameAndExtension, append);
		OutputStreamWriter writer = new OutputStreamWriter(out);
		
		writer.write(content);
		writer.close();
	}
	
	public static String readFromFile(String filePath, String fileNameAndExtension) throws IOException{
		return readFromFile(filePath, fileNameAndExtension, "\n");
	}
	
	public static String readFromFile(String filePath, String fileNameAndExtension, String linesDivider) throws IOException{
		StringBuilder fileText = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(filePath + fileNameAndExtension));
		String line = null;

		while((line = br.readLine()) != null){
			fileText.append(line + linesDivider);
		}
		br.close();

		String s = new String(fileText);
		s = s.substring(0, s.length() - 1);
		
		return s;
	}
	
	public static void storeProperties(String filePath, String fileName, Object[] properties, Object[] values) throws FileNotFoundException, IOException{
		Properties prop = new Properties();
		for(int i = 0; i < properties.length; i++){
			prop.put(properties[i], values[i]);
		}
		
		File file = new File(filePath);
		file.mkdirs();
		
		prop.store(new OutputStreamWriter(new FileOutputStream(filePath + fileName)), "FaceApp");
	}
	
	public static Properties loadProperties(String filePath, String fileName) throws IOException{
		Properties prop = new Properties();
		
		try{
			File file = new File(filePath);
			file.mkdirs();
			prop.load(new InputStreamReader(new FileInputStream(filePath + fileName)));
		}catch(FileNotFoundException | NullPointerException e){
			return null;
		}
		
		return prop;
	}
	
	public static String getSystemLogFileName(String userId){
		return SYSTEM_LOG + userId + LOGS_EXTENSION;
	}
	
	public static String getMiscLogFileName(String userId){
		return MISC_LOG + userId + LOGS_EXTENSION;
	}
	
	public static String getSettingsFileName(String userId){
		return SETTINGS + userId + SETTINGS_EXTENSION;
	}
	
	public static String getPhotoFilePathAndName(String filesPath, String userId){
		return filesPath + PHOTO + userId + PHOTO_EXTENSION;
	}
	
}
