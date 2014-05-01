package br.com.dsoo.facebook.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.logic.exceptions.NoSuchOptionException;
import br.com.dsoo.facebook.user.User;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Family;
import facebook4j.ResponseList;

public class Ui{

	private final char SEE_USER_DATA = '1';
	private final char POST_STATUS_MESSAGE = '2';
	private final char OBTAIN_ACTIVITIES_REPORT = '3';
	private final char SEE_FAMILY_TREE = '4';
	private final char SEARCH_NEAR_EVENTS = '5';
	private final char QUIT = '0';
	
	private Clipboard clipboard;
	private StringSelection selection;
	private Scanner input;
	
	public Ui(){
		input = new Scanner(System.in);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public void showOptions(User user) throws FacebookException, ParseException{
		char option = '0';
		do{
			try{
				option = showInput("--------------- Bem vindo! ---------------\n\n"
						+ "[1] - Ver informa��es do usu�rio\n"
						+ "[2] - Postar atualiza��o de Status\n"
						+ "[3] - Obter resumo de atividades\n"
						+ "[4] - Ver �rvore geneal�gica\n"
						+ "[5] - Pesquisar eventos pr�ximos\n"
						+ "[0] - Sair").charAt(0);

				switch(option){
					case SEE_USER_DATA:
						show(user.toString());
						break;
						
					case POST_STATUS_MESSAGE:
						show("Postagem feita com sucesso!\nID: " + user.postStatusMessage(showInput("No que voc� est� pensando?")));
						break;

					case OBTAIN_ACTIVITIES_REPORT:
						show(user.getActivitiesReport());
						break;
						
					case SEE_FAMILY_TREE:
						show(formatFamily(user.getFamilyTree()));
						break;
						
					case SEARCH_NEAR_EVENTS:
						show(formatEvents(user.getNearEvents()));
						break;
						
					case QUIT:
						return;

					default:
						throw new NoSuchOptionException(option);
				}
			}catch(NoSuchOptionException e){
				show(e.getMessage());
			}
		}while(option != QUIT);
	}
	
	private String formatEvents(ResponseList<Event> nearEvents){
		return "TODO";
	}

	/**
	 * 
	 * @param
	 * @return String com a fam�lia formatada
	 */
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
				str += "Av�s: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.PARENTS){
				str += "Pais: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.UNCLES){
				str += "Tios: ";
			}else if(key == br.com.dsoo.facebook.logic.constants.Family.BROTHERS_AND_SISTERS){
				str += "Irm�os(�s): ";
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
	 * Exibe a mensagem solicitada
	 * @param msg
	 */
	public void show(String msg){
		System.out.println("\n" + msg);
	}
	
	/**
	 * Exibe a mensagem solicitada
	 * @param msg
	 * @return Dado inserido pelo usu�rio
	 */
	public String showInput(String msg){
		show(msg);

		return input.nextLine();
	}

	/**
	 * Solicita o c�digo de autentica��o
	 * @param facebook
	 * @return C�digo de autentica��o
	 */
	public String showAuthenticationMessage(Facebook facebook){
		selection = new StringSelection(facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.getProperty()));
		clipboard.setContents(selection, selection);
		
		return showInput("O link de autentica��o foi colado na sua\n�rea de transfer�ncia. Cole no seu\nnavegador e copie o c�digo de resposta:");
	}
}
