package br.com.dsoo.facebook.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.mail.EmailException;

import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.logic.exceptions.NoSuchOptionException;
import br.com.dsoo.facebook.user.User;
import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Family;

public class Ui{

	private final char SEE_USER_DATA = '1';
	private final char POST_STATUS_MESSAGE = '2';
	private final char OBTAIN_ACTIVITIES_REPORT = '3';
	private final char SEE_FAMILY_TREE = '4';
	private final char USER_AGENDA = '5';
	private final char QUIT = '0';
	
	private Clipboard clipboard;
	private StringSelection selection;
	private Scanner input;
	
	public Ui(){
		input = new Scanner(System.in);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public void showOptions(User user) throws FacebookException, ParseException, EmailException{
		char option = '0';
		do{
			try{
				option = showInput("--------------- Bem vindo! ---------------\n",
						"[1] - Ver informações do usuário",
						"[2] - Postar atualização de Status",
						"[3] - Obter resumo de atividades",
						"[4] - Ver árvore genealógica",
						"[5] - Ver agenda",
						"[0] - Sair").charAt(0);

				switch(option){
					case SEE_USER_DATA:
						show("Informações do usuário:", user.toString());
						break;
						
					case POST_STATUS_MESSAGE:
						show("Postagem feita com sucesso!", "ID: " + user.postStatusMessage(showInput("No que você está pensando?")));
						break;

					case OBTAIN_ACTIVITIES_REPORT:
						show("Resumo de atividades:", user.getActivitiesReport());
						break;
						
					case SEE_FAMILY_TREE:
						show("Árvore genealógica:", formatFamily(user.getFamilyTree()));
						break;
						
					case USER_AGENDA:
						show("Agenda:", formatEvents(user.getUserAgenda()));
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
	
	/**
	 * 
	 * @param events
	 * @return String formatada com os eventos
	 */
	private String formatEvents(ArrayList<Event> events){
		String str = "";
		
		for(int i = 0; i < events.size(); i++){
			str += events.get(i).getName() + ": " + Utils.dateToString(events.get(i).getStartTime()) + "\n";
			
			if(i > 0 && i < events.size() - 1)
				str += "------------------------------------";
		}
		
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 
	 * @param
	 * @return String formatada com a família
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
	 * Exibe a mensagem solicitada
	 * @param msg
	 */
	public void show(String ... msg){
		String str = "";
		
		for(String word : msg){
			str += word + "\n";
		}
		
		System.out.println(str.substring(0, str.length() - 1));
	}
	
	/**
	 * Exibe a mensagem solicitada
	 * @param msg
	 * @return Dado inserido pelo usuário
	 */
	public String showInput(String ... msg){
		show(msg);

		return input.nextLine();
	}

	/**
	 * Solicita o código de autenticação
	 * @param facebook
	 * @return Código de autenticação
	 */
	public String showAuthenticationMessage(Facebook facebook){
		selection = new StringSelection(facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.getValue()));
		clipboard.setContents(selection, selection);
		
		return showInput("O link de autenticação foi colado na sua\nárea de transferência. Cole no seu\nnavegador e copie o código de resposta:");
	}
}
