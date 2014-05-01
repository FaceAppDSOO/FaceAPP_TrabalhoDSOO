package br.com.dsoo.facebook.user;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.logic.constants.Time;
import br.com.dsoo.facebook.logic.exceptions.TypeMismatchException;
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
	 * @return Fam�lia do usu�rio
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

	/**
	 * Resumo das atividades (p�ginas curtidas, coment�rios feitos, etc.
	 * @return Resumo
	 * @throws FacebookException
	 * @throws ParseException 
	 * @throws TypeMismatchException 
	 */
	public String getActivitiesReport() throws FacebookException, ParseException{
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

		return "Atividade de " + name.split(" ")[0] + " dos �ltimos " + diff + " dias:\n\n"
		+ statuses + " novas atualiza��es de Status\n"
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
	 * @return Amigos do usu�rio
	 * @throws IllegalStateException
	 * @throws FacebookException
	 */
	public String getName(){
		return name;
	}

	/**
	 * 
	 * @return Amigos do usu�rio
	 */
	public Object[] getFriends(){
		return friends.toArray();
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
				+ "\nNome: " + getName()
				+ "\nIdade: " + getAge() + " anos"
				+ "\n\nN�mero de amigos: " + friends.size();

			if(fb.getMe().getRelationshipStatus() != null){
				data += "\n\nEm um relacionamento s�rio";
			}
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
