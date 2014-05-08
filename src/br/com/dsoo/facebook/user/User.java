package br.com.dsoo.facebook.user;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import br.com.dsoo.facebook.logic.Services;
import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.logic.constants.Time;
import br.com.dsoo.facebook.logic.exceptions.TypeMismatchException;
import br.com.dsoo.facebook.view.Ui;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Family;
import facebook4j.Friend;
import facebook4j.Like;
import facebook4j.Paging;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;

public class User{

	private String name;
	private Services s;
	
	private Ui ui;
	
	private Facebook fb;
	private ResponseList<Friend> friends;
	private ResponseList<Family> family;

	public User(Facebook f, Ui ui) throws FacebookException, IOException{
		this.ui = ui;
		fb = f;
		friends = fb.getFriends();
		family = fb.getFamily();
		name = fb.getName();
		s = new Services();
	}

	/**
	 * 
	 * @return Família do usuário
	 */
	public HashMap<br.com.dsoo.facebook.logic.constants.Family, ArrayList<Family>> getFamilyTree(){
		HashMap<br.com.dsoo.facebook.logic.constants.Family, ArrayList<Family>> map = new HashMap<>();
		
		ArrayList<Family> gParents = new ArrayList<>();
		ArrayList<Family> parents = new ArrayList<>();
		ArrayList<Family> uncles = new ArrayList<>();
		ArrayList<Family> brothers = new ArrayList<>();
		ArrayList<Family> cousins = new ArrayList<>();
		ArrayList<Family> sons = new ArrayList<>();
		ArrayList<Family> grandsons = new ArrayList<>();
		ArrayList<Family> undefined = new ArrayList<>();
		
		for(Family p : family){
			if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.GRANDPARENT.getValue())){
				gParents.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.PARENTS.getValue())){
				parents.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.UNCLES.getValue())){
				uncles.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS.getValue())){
				brothers.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.COUSINS.getValue())){
				cousins.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.SONS.getValue())){
				sons.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.GRANDSONS.getValue())){
				grandsons.add(p);
			}else{
				undefined.add(p);
			}
		}
		
		map.put(br.com.dsoo.facebook.logic.constants.Family.GRANDPARENT, gParents);
		map.put(br.com.dsoo.facebook.logic.constants.Family.PARENTS, parents);
		map.put(br.com.dsoo.facebook.logic.constants.Family.UNCLES, uncles);
		map.put(br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS, brothers);
		map.put(br.com.dsoo.facebook.logic.constants.Family.COUSINS, cousins);
		map.put(br.com.dsoo.facebook.logic.constants.Family.SONS, sons);
		map.put(br.com.dsoo.facebook.logic.constants.Family.GRANDSONS, grandsons);
		
		return map;
	}

	/**
	 * Resumo das atividades (páginas curtidas, comentários feitos, etc.
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

		String str = "Atividade de " + name.split(" ")[0] + " dos últimos " + diff + " dias:\n\n"
				+ statuses + " novas atualizações de Status\n"
				+ likes + " novas páginas curtidas\n";
		
		if(ui.showInput("Você deseja enviar esse relatório por email?").charAt(0) == Character.toLowerCase('s')){
			s.sendActivitiesEmail(this, str, ui.showInput("Para qual email deseja enviar o relatório? (Para mais de um destinatário, separe os emails com vírgulas)").split(" *,+ *"));
		}
		
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
	public Object[] getFriends(){
		return friends.toArray();
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

//			if(fb.getMe().getRelationshipStatus() != null){
//				data += "\n\nEm um relacionamento sério";
//			}
		}catch(IllegalStateException | FacebookException | ParseException e){
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * Encerra a sessão
	 */
	public void logout(){
		fb.shutdown();
	}
}
