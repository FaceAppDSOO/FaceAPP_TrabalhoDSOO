package br.com.dsoo.facebook.user;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.dsoo.facebook.logic.Logger;
import br.com.dsoo.facebook.logic.Services;
import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.logic.constants.Family;
import br.com.dsoo.facebook.logic.constants.Time;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.Like;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.PictureSize;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class User{

	public static final String LAST_LIKED = "LAST_LIKED", LAST_DOWNLOADED = "LAST_DOWNLOADED", LAST_AUTO_LIKED_POST = "LAST_AUTO_LIKED_POST";
	
	private final Logger logger;
	
	private String name;
	private final Services s;
	
	private Facebook fb;
	private ResponseList<Friend> friends;
	private ResponseList<facebook4j.Family> family;

	public User(Facebook f) throws FacebookException, IOException{
		fb = f;
		logger = new Logger(getId(), "User");
		friends = fb.getFriends();
		family = fb.getFamily();
		name = fb.getName();
		s = new Services(getId());
	}

	public Settings getSettings(){
		return s.getSettings();
	}
	
	public void storeSettings() throws FileNotFoundException, IOException{
		s.storeSettings();
	}
	
	/**
	 * 
	 * @return
	 * @throws FacebookException
	 * @throws IOException 
	 */
	public void likeAndDownloadTaggedPhotos() throws FacebookException, IOException{
		ResponseList<Photo> photos = fb.photos().getPhotos();
		logger.log("Fotos pesquisadas");
		Paging<Photo> paging = null;
		
		ArrayList<Photo> imgsToDownload = new ArrayList<>();
		
		boolean like = getSettings().isLikePhotosWhenTagged(),
				download = getSettings().isDownloadPhotosWhenTagged();
		
		Properties prop = FileManager.loadProperties(getSettings().getDownloadPhotoFilePath(), FileManager.PHOTOS_LOG);
		
		String 	lastLikeId = "null", lastDownloadId = "null", lastLiked = "null", lastDownloaded = "null";
		
		if(prop != null){
			lastLikeId = prop.getProperty(LAST_LIKED);
			lastDownloadId = prop.getProperty(LAST_DOWNLOADED);
		}
		
		String id = null;
		while(photos != null){
			if(like){
				likesLoop: for(Photo photo : photos){
					id = photo.getId();
					if(id.equalsIgnoreCase(lastLikeId)){
						like = false;
						break likesLoop;
					}
					
					fb.likePhoto(lastLiked = id);
					logger.log("Foto curtida", id);
				}
			}
			
			if(download){
				downloadsLoop: for(Photo photo : photos){
					id = photo.getId();
					if(id.equalsIgnoreCase(lastDownloadId)){
						download = false;
						break downloadsLoop;
					}
					
					imgsToDownload.add(photo);
					lastDownloaded = id;
				}
			}
			
			if((download || like) && (paging = photos.getPaging()) != null){
				photos = fb.fetchNext(paging);
				logger.log("Paginação das fotos", paging.getNext().toString());
				continue;
			}
			
			break;
		}
		
		if(getSettings().getDownloadPhotoFilePath() != null){
			FileManager.storeProperties(
					getSettings().getDownloadPhotoFilePath(), FileManager.PHOTOS_LOG,
					new String[]{LAST_LIKED, LAST_DOWNLOADED},
					new String[]{lastLiked, lastDownloaded}
					);
			if(imgsToDownload.size() > 0){
				s.downloadPhotos(imgsToDownload.toArray(new Photo[imgsToDownload.size()]));
			}
		}
	}

	/**
	 * 
	 * @return Família do usuário
	 */
	public HashMap<Family, ArrayList<facebook4j.Family>> getFamilyTree(){
		HashMap<Family, ArrayList<facebook4j.Family>> map = new HashMap<>();
		
		ArrayList<facebook4j.Family> gParents = new ArrayList<>();
		ArrayList<facebook4j.Family> parents = new ArrayList<>();
		ArrayList<facebook4j.Family> uncles = new ArrayList<>();
		ArrayList<facebook4j.Family> brothers = new ArrayList<>();
		ArrayList<facebook4j.Family> cousins = new ArrayList<>();
		ArrayList<facebook4j.Family> sons = new ArrayList<>();
		ArrayList<facebook4j.Family> grandsons = new ArrayList<>();
		ArrayList<facebook4j.Family> undefined = new ArrayList<>();
		
		for(facebook4j.Family p : family){
			if(p.getRelationship().matches(Family.GRANDPARENT.value)){
				gParents.add(p);
			}else if(p.getRelationship().matches(Family.PARENTS.value)){
				parents.add(p);
			}else if(p.getRelationship().matches(Family.UNCLES.value)){
				uncles.add(p);
			}else if(p.getRelationship().matches(Family.BROTHERS_AND_SISTERS.value)){
				brothers.add(p);
			}else if(p.getRelationship().matches(Family.COUSINS.value)){
				cousins.add(p);
			}else if(p.getRelationship().matches(Family.SONS.value)){
				sons.add(p);
			}else if(p.getRelationship().matches(Family.GRANDSONS.value)){
				grandsons.add(p);
			}else{
				undefined.add(p);
			}
		}
		
		map.put(Family.GRANDPARENT, gParents);
		map.put(Family.PARENTS, parents);
		map.put(Family.UNCLES, uncles);
		map.put(Family.BROTHERS_AND_SISTERS, brothers);
		map.put(Family.COUSINS, cousins);
		map.put(Family.SONS, sons);
		map.put(Family.GRANDSONS, grandsons);
                map.put(Family.UNDEFINED, undefined);
		
		return map;
	}
	
	/**
	 * 
	 * @return URL com a imagem do perfil do usuário
	 * @throws FacebookException
	 */
	public URL getUserPic() throws FacebookException{
		logger.log("Foto do perfil do usuário carregada");
		return fb.getPictureURL(PictureSize.normal);
	}

	/**
	 * Resumo das atividades (páginas curtidas, comentários feitos, etc.
	 * @return Resumo
	 * @throws FacebookException
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	public String getActivitiesReport() throws FacebookException, ParseException, AddressException, MessagingException, IOException{
		Date since = Utils.getDateFromDifference(getSettings().getActivitiesReportSince(), Time.DAYS);

		ResponseList<Post> allStatuses = fb.getStatuses((new Reading()).since(since));
		logger.log("Pesquisados status do usuário");
		ResponseList<Like> allPageLikes = fb.likes().getUserLikes((new Reading()).since(since));
		logger.log("Pesquisados likes do usuário");

		int statuses = 0;
		int likes = 0;

		Paging<Post> statusPaging = null;
		Paging<Like> likePaging = null;

		statusesLoop: while(allStatuses != null){
			for(Post status : allStatuses){
				if(status.getUpdatedTime().before(since)){
					break statusesLoop;
				}

				statuses++;
			}

			if((statusPaging = allStatuses.getPaging()) != null){
				allStatuses = fb.fetchNext(statusPaging);
				logger.log("Paginação dos status", statusPaging.getNext().toString());
				continue;
			}

			break;
		}

		likesLoop: while(allPageLikes != null){
			for(Like like : allPageLikes){
				if(like.getCreatedTime().before(since)){
					break likesLoop;
				}
				
				likes++;
			}

			if((likePaging = allPageLikes.getPaging()) != null){
				allPageLikes = fb.fetchNext(likePaging);
				logger.log("Paginação dos likes", likePaging.getNext().toString());
				continue;
			}

			break;
		}

		long diff = Utils.differenceBetweenDates(since, new Date(), Time.DAYS);

		String str = "Atividade de " + name.split(" ")[0] + " dos últimos " + diff + " dias:\n\n"
				+ statuses + " novas atualizações de Status\n"
				+ likes + " novas páginas curtidas\n";

		String[] emails = getSettings().getActivitiesReportEmail().split(" *,+ *");
		if(emails != null && emails.length > 0){
			s.sendActivitiesEmail(this, str, emails);
		}
		
		return str;
	}
	
	/**
	 * @return Feed de notícias
	 * @throws FacebookException
	 * @throws IOException 
	 */
	public Post[] getNewsFeed() throws FacebookException, IOException{
		ArrayList<Post> posts = new ArrayList<>();

		boolean likeStatuses = getSettings().isLikeUsersListStatuses();
		String[] idsToLike = getSettings().getUsersIdsToLikeStatuses();
		Properties prop = FileManager.loadProperties(FileManager.LOGS_PATH, FileManager.getMiscLogFileName(getId()));
		String lastAutoLikedPost = prop.getProperty(LAST_AUTO_LIKED_POST);
		
		int quantity = getSettings().getNewsFeedSize();
		
		ResponseList<Post> home = fb.getHome();
		logger.log("Feed de notícias carregado");
		Paging<Post> paging = null;

		while(home != null){
			for(Post post : home){
				if(posts.size() == quantity){
					return posts.toArray(new Post[quantity]);
				}
				
				if(likeStatuses && idsToLike != null){
					String 	postId = post.getId(),
							fromId = post.getFrom().getId();
					idsLoop: for(String id : idsToLike){
						if(id.equalsIgnoreCase(fromId)){
							if(postId.equalsIgnoreCase(lastAutoLikedPost)){
								likeStatuses = false;
								break idsLoop;
							}
							
							try{
								fb.posts().likePost(postId);
								logger.log("Post curtido", postId);
							}catch(FacebookException e){}
							break idsLoop;
						}
					}
				}

				if(post.getType().equals("status") && post.getMessage() != null){
					posts.add(post);
				}
			}
			
			if((paging = home.getPaging()) != null){
				home = fb.fetchNext(paging);
				continue;
			}
			
			break;
		}
		
		FileManager.storeProperties(
				FileManager.LOGS_PATH,
				FileManager.getMiscLogFileName(getId()),
				new String[]{LAST_AUTO_LIKED_POST}, 
				new String[]{lastAutoLikedPost});
		
		return posts.toArray(new Post[posts.size()]);
	}
	
	public ArrayList<Event> getUserAgenda() throws FacebookException, ParseException{
		Date since = Utils.getDateFromDifference(getSettings().getAgendaSince(), Time.DAYS);
		
		ResponseList<Event> events = fb.getEvents((new Reading()).since(since));
		logger.log("Eventos carregados");
		Paging<Event> paging = null;
		
		ArrayList<Event> ev = new ArrayList<>();
		
		eventsLoop: while(events != null){
			for(Event e : events){
				if(e.getEndTime() != null && e.getEndTime().before(new Date()))
					break eventsLoop;

				if(e.getStartTime().after(new Date()))
					ev.add(e);
			}

			paging = events.getPaging();

			if(paging != null){
				events = fb.fetchNext(paging);
				continue;
			}

			break;
		}

		return ev;
	}

	/**
	 * Realiza um post no Facebook
	 * @return ID da mensagem postada
	 * @param msg
	 * @throws FacebookException
	 */
	public String postStatusMessage(String msg) throws FacebookException{
		String id = fb.postStatusMessage(msg);
		logger.log("Atualização de status", id);
		return id;
	}

	/**
	 * 
	 * @return Nome do usuário
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return Primeiro nome do usuário
	 * @throws FacebookException
	 */
	public String getFirstName() throws FacebookException{
		return fb.getMe().getFirstName();
	}

	/**
	 * 
	 * @return Amigos do usuário
	 */
	public ResponseList<Friend> getFriends(){
		return friends;
	}

	/**
	 * 
	 * @return AccessToken configurado para este usuário
	 */
	public AccessToken getAuthToken(){
		return fb.getOAuthAccessToken();
	}

	/**
	 * 
	 * @return A situação de autenticação do usuário
	 */
	public boolean isAuthenticated(){
		return fb.getAuthorization().isEnabled();
	}

	/**
	 * 
	 * @return A idade do usuário
	 * @throws FacebookException
	 * @throws ParseException
	 */
	public int getAge() throws FacebookException, ParseException{
		return (int)Utils.differenceBetweenDates(Utils.toDate(fb.getMe().getBirthday()), new Date(), Time.YEARS);
	}

	/**
	 * 
	 * @return O ID do usuário
	 * @throws FacebookException
	 */
	public String getId() throws FacebookException{
		return fb.getMe().getId();
	}

	/**
	 * @return Dados do usuário
	 */
	@Override
	public String toString(){
		String data = "";
		try{
			data = "ID: " + getId()
				+ "\nNome: " + getName();
			
			int age = getAge();
			if(age != -1 && age != 0)
				data += "\nIdade: " + age + " anos";

			data += "\n\nNúmero de amigos: " + friends.size();

		}catch(IllegalStateException | FacebookException | ParseException e){
			e.printStackTrace();
		}

		return data;
	}
	
	/**
	 * 
	 * @return Facebook
	 */
	public Facebook getAccount(){
		return fb;
	}

	/**
	 * Encerra a sessão
	 */
	public void logout(){
		fb.shutdown();
	}
}
