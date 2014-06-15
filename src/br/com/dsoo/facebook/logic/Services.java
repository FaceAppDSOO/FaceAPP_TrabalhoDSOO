package br.com.dsoo.facebook.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.dsoo.facebook.user.Settings;
import br.com.dsoo.facebook.user.User;

import com.thoughtworks.xstream.XStream;

import facebook4j.FacebookException;
import facebook4j.Photo;


public class Services{

	private final Logger logger = new Logger("Services");
	
	private final Settings settings;
	private Emailer emailer;
	
	private XStream xstream;
	
	public Services(String userId) throws IOException{
		initializeSerializer();
		emailer = new Emailer();
		settings = loadSettings(userId);
	}
	
	public void downloadPhotos(Photo ...photos) throws IOException{
		String folder = settings.getDownloadPhotoFilePath();
		File path = new File(folder);
		path.mkdirs();
		
		URL url = null;
		InputStream in = null;
		OutputStream out = null;
		
		byte[] b = null;
		int length = 0;
		
		Photo photo = null;
		for(int i = 0; i < photos.length; i++){
			photo = photos[i];
			b = new byte[2048];
			url = photo.getSource();
			in = url.openStream();
			out = new FileOutputStream(folder + "photo_" + photo.getId() + ".jpg");
			
			while((length = in.read(b)) != -1){
				out.write(b, 0, length);
			}
			
			in.close();
			out.close();
		}
	}

	/**
	 * Envia um email
	 * @param user Usuário ativo
	 * @param msg Mensage,
	 * @param to Destinatário
	 * @throws MessagingException 
	 * @throws AddressException
	 * @throws FacebookException 
	 * @throws IOException 
	 */
	public void sendActivitiesEmail(User user, String msg, String ...to) throws AddressException, MessagingException, FacebookException, IOException{
		emailer.sendEmail(msg, "Resumo de atividades de " + user.getFirstName(), to);
		Emailer.refreshConfig();
	}

	public Settings getSettings(){
		return settings;
	}
	
	private Settings loadSettings(String userId) throws IOException{
		try{
			String xml = Utils.readFromFile("settings\\", "settings_" + userId + ".xml");
			return (Settings)xstream.fromXML(xml);
		}catch(FileNotFoundException e){
			return new Settings(userId);			
		}
	}
	
	public String parseSettings(){
		return xstream.toXML(settings);
	}
	
	private void initializeSerializer(){
		xstream = new XStream();
		xstream.alias("settings", Settings.class);
	}
}
