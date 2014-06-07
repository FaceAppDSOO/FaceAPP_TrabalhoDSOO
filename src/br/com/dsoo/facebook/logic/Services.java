package br.com.dsoo.facebook.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.dsoo.facebook.user.Settings;
import br.com.dsoo.facebook.user.User;
import facebook4j.FacebookException;
import facebook4j.Photo;


public class Services{

	private final Settings settings;
	private Emailer emailer;
	
	public Services(String userId) throws IOException{
		emailer = new Emailer();
		settings = new Settings(userId);
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
		
		for(Photo photo : photos){
			b = new byte[2048];
			url = photo.getPicture();
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
	public void sendActivitiesEmail(User user, String msg, String ... to) throws AddressException, MessagingException, FacebookException, IOException{
		emailer.sendEmail(msg, "Resumo de atividades de " + user.getFirstName(), to);
		Emailer.refreshConfig();
	}

	public Settings getSettings(){
		return settings;
	}
}
