package br.com.dsoo.facebook.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import br.com.dsoo.facebook.logic.constants.Time;
import br.com.dsoo.facebook.logic.exceptions.TypeMismatchException;
import br.com.dsoo.facebook.logic.utils.Utils;
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
import facebook4j.internal.org.json.JSONException;

public class User{

	private String name;
	
	private Facebook fb;
	private ResponseList<Friend> friends;
	private ResponseList<Family> family;

	public User(Facebook f) throws FacebookException{
		fb = f;
		friends = fb.getFriends();
		family = fb.getFamily();
		name = fb.getName();
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
			if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.GRANDPARENT.getType())){
				gParents.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.PARENTS.getType())){
				parents.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.UNCLES.getType())){
				uncles.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS.getType())){
				brothers.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.COUSINS.getType())){
				cousins.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.SONS.getType())){
				sons.add(p);
			}else if(p.getRelationship().matches(br.com.dsoo.facebook.logic.constants.Family.GRANDSONS.getType())){
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
	
	public String formatFamily(HashMap<br.com.dsoo.facebook.logic.constants.Family, ArrayList<Family>> f){
		br.com.dsoo.facebook.logic.constants.Family[] keySet = {
				br.com.dsoo.facebook.logic.constants.Family.GRANDPARENT,
				br.com.dsoo.facebook.logic.constants.Family.PARENTS,
				br.com.dsoo.facebook.logic.constants.Family.UNCLES,
				br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS,
				br.com.dsoo.facebook.logic.constants.Family.COUSINS,
				br.com.dsoo.facebook.logic.constants.Family.SONS,
				br.com.dsoo.facebook.logic.constants.Family.GRANDSONS
		};

		String str = "";
		
		for(br.com.dsoo.facebook.logic.constants.Family key : keySet){
			if(f.get(key).isEmpty())
				continue;

			if(key == br.com.dsoo.facebook.logic.constants.Family.GRANDPARENT){
				str += "Avós: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.PARENTS){
				str += "Pais: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.UNCLES){
				str += "Tios: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS){
				str += "Irmãos(ãs): ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.COUSINS){
				str += "Primos(as): ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.SONS){
				str += "Filhos(as): ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.GRANDSONS){
				str += "Netos(as): ";
			}else{
				str += "\nOutros: ";
			}

			for(Family p : f.get(key)){
				str += p.getName() + ", ";
			}

			str = str.substring(0, str.length() - 2) + "\n";
		}

		return str;
	}

	/**
	 * Resumo das atividades (páginas curtidas, comentários feitos, etc.
	 * @return Resumo
	 * @throws FacebookException
	 * @throws ParseException 
	 * @throws JSONException 
	 * @throws TypeMismatchException 
	 */
	public String getActivitiesReport() throws FacebookException, ParseException, JSONException, TypeMismatchException{
		Date since = Utils.toDate("2014-04-01");

		ResponseList<Post> allStatuses = fb.getStatuses((new Reading()).since(since));
		ResponseList<Like> allLikes = fb.likes().getUserLikes((new Reading()).since(since));

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

		likesLoop: while(allLikes != null){
			for(Like like : allLikes){
				if(like.getCreatedTime().before(since)){
					break likesLoop;
				}
				
				likes++;
			}

			likePaging = allLikes.getPaging();

			if(likePaging != null){
				allLikes = fb.fetchNext(likePaging);
				continue;
			}

			break;
		}

		long diff = Utils.differenceBetweenDates(since, new Date(), Time.DAYS);

		return "Atividade de " + name.split(" ")[0] + " dos últimos " + diff + " dias:\n\n"
		+ statuses + " novas atualizações de Status\n"
		+ likes + " novos Likes\n";
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
	 * @return Amigos do usuário
	 * @throws IllegalStateException
	 * @throws FacebookException
	 */
	public String getName(){
		return name;
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
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

		Date bDay = format.parse(fb.getMe().getBirthday());
		Date today = new Date();

		return (int)Utils.differenceBetweenDates(bDay, today, Time.YEARS);
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
			data = "ID: " + this.getId()
					+ "\nNome: " + this.getName()
					+ "\nIdade: " + this.getAge() + " anos"
					+ "\n\nNúmero de amigos: " + this.friends.size();

			if(fb.getMe().getRelationshipStatus() != null){
				data += "\n\nEm um relacionamento sério";
			}
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
