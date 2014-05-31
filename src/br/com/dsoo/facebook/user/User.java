package br.com.dsoo.facebook.user;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.dsoo.facebook.logic.Services;
import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.logic.constants.Family;
import br.com.dsoo.facebook.logic.constants.Time;
import br.com.dsoo.facebook.logic.exceptions.TypeMismatchException;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Friend;
import facebook4j.Like;
import facebook4j.Paging;
import facebook4j.PictureSize;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class User{

	private String name;
	private Services s;
	
	private Facebook fb;
	private ResponseList<Friend> friends;
	private ResponseList<facebook4j.Family> family;

	public User(Facebook f) throws FacebookException, IOException{
		fb = f;
		friends = fb.getFriends();
		family = fb.getFamily();
		name = fb.getName();
		s = new Services();
	}

	/**
	 * 
	 * @return Fam�lia do usu�rio
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
			if(p.getRelationship().matches(Family.GRANDPARENT.getValue())){
				gParents.add(p);
			}else if(p.getRelationship().matches(Family.PARENTS.getValue())){
				parents.add(p);
			}else if(p.getRelationship().matches(Family.UNCLES.getValue())){
				uncles.add(p);
			}else if(p.getRelationship().matches(Family.BROTHERS_AND_SISTERS.getValue())){
				brothers.add(p);
			}else if(p.getRelationship().matches(Family.COUSINS.getValue())){
				cousins.add(p);
			}else if(p.getRelationship().matches(Family.SONS.getValue())){
				sons.add(p);
			}else if(p.getRelationship().matches(Family.GRANDSONS.getValue())){
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
	 * @return URL com a imagem do perfil do usu�rio
	 * @throws FacebookException
	 */
	public URL getUserPic() throws FacebookException{
		return fb.getPictureURL(PictureSize.normal);
	}

	/**
	 * Resumo das atividades (p�ginas curtidas, coment�rios feitos, etc.
	 * @return Resumo
	 * @throws FacebookException
	 * @throws ParseException 
	 * @throws EmailException 
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws AddressException 
	 * @throws TypeMismatchException 
	 */
	public String getActivitiesReport() throws FacebookException, ParseException, AddressException, MessagingException, IOException{
		Date since = Utils.toDate("04-01-2014");

		ResponseList<Post> allStatuses = fb.getStatuses((new Reading()).since(since));
		ResponseList<Like> allPageLikes = fb.likes().getUserLikes((new Reading()).since(since));

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

			statusPaging = allStatuses.getPaging();

			if(statusPaging != null){
				allStatuses = fb.fetchNext(statusPaging);
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

			likePaging = allPageLikes.getPaging();

			if(likePaging != null){
				allPageLikes = fb.fetchNext(likePaging);
				continue;
			}

			break;
		}

		long diff = Utils.differenceBetweenDates(since, new Date(), Time.DAYS);

		String str = "Atividade de " + name.split(" ")[0] + " dos �ltimos " + diff + " dias:\n\n"
				+ statuses + " novas atualiza��es de Status\n"
				+ likes + " novas p�ginas curtidas\n";

		//ENVIAR EMAIL
		
		return str;
	}
	
	public ArrayList<Event> getUserAgenda() throws FacebookException, ParseException{
		Date since = Utils.toDate("04/01/2014");
		
		ResponseList<Event> events = fb.getEvents((new Reading()).since(since));
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
		return fb.postStatusMessage(msg);
	}

	/**
	 * 
	 * @return Nome do usu�rio
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * 
	 * @return Primeiro nome do usu�rio
	 * @throws FacebookException
	 */
	public String getFirstName() throws FacebookException{
		return fb.getMe().getFirstName();
	}

	/**
	 * 
	 * @return Amigos do usu�rio
	 */
	public ResponseList<Friend> getFriends(){
		return friends;
	}

	/**
	 * 
	 * @return AccessToken configurado para este usu�rio
	 */
	public AccessToken getAuthToken(){
		return fb.getOAuthAccessToken();
	}

	/**
	 * 
	 * @return A situa��o de autentica��o do usu�rio
	 */
	public boolean isAuthenticated(){
		return fb.getAuthorization().isEnabled();
	}

	/**
	 * 
	 * @return A idade do usu�rio
	 * @throws FacebookException
	 * @throws ParseException
	 */
	public int getAge() throws FacebookException, ParseException{
		return (int)Utils.differenceBetweenDates(Utils.toDate(fb.getMe().getBirthday()), new Date(), Time.YEARS);
	}

	/**
	 * 
	 * @return O ID do usu�rio
	 * @throws FacebookException
	 */
	public String getId() throws FacebookException{
		return fb.getMe().getId();
	}

	/**
	 * @return Dados do usu�rio
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

			data += "\n\nN�mero de amigos: " + friends.size();

//			if(fb.getMe().getRelationshipStatus() != null){
//				data += "\n\nEm um relacionamento s�rio";
//			}
		}catch(IllegalStateException | FacebookException | ParseException e){
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Encerra a sess�o
	 */
	public void logout(){
		fb.shutdown();
	}
}
