package br.com.dsoo.facebook.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.ParseException;
import java.util.Scanner;

import br.com.dsoo.facebook.logic.constants.AppData;
import br.com.dsoo.facebook.logic.exceptions.NoSuchOptionException;
import br.com.dsoo.facebook.logic.exceptions.TypeMismatchException;
import br.com.dsoo.facebook.user.User;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.internal.org.json.JSONException;

public class Ui{

	private final char SEE_USER_DATA = '1';
	private final char POST_STATUS_MESSAGE = '2';
	private final char OBTAIN_ACTIVITIES_REPORT = '3';
	private final char SEE_FAMILY_TREE = '4';
	private final char QUIT = '0';
	
	private Clipboard clipboard;
	private StringSelection selection;
	private Scanner input;
	
	public Ui(){
		input = new Scanner(System.in);
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	public void showOptions(User user) throws IllegalStateException, FacebookException, ParseException, JSONException, TypeMismatchException{
		char option = '0';
		do{
			try{
				option = this.showInput("--------------- Bem vindo! ---------------\n\n"
						+ "[1] - Ver informa��es do usu�rio\n"
						+ "[2] - Postar atualiza��o de Status\n"
						+ "[3] - Obter resumo de atividades\n"
						+ "[4] - Ver �rvore geneal�gica\n"
						+ "[0] - Sair").charAt(0);

				switch(option){
					case SEE_USER_DATA:
						this.show(user.toString());
						break;
						
					case POST_STATUS_MESSAGE:
						this.show("Postagem feita com sucesso!\nID: " + user.postStatusMessage(this.showInput("No que voc� est� pensando?")));
						break;

					case OBTAIN_ACTIVITIES_REPORT:
						this.show(user.getActivitiesReport());
						break;
						
					case SEE_FAMILY_TREE:
						this.show(user.formatFamily(user.getFamilyTree()));
						break;
						
					case QUIT:
						return;

					default:
						throw new NoSuchOptionException(option);
				}
			}catch(NoSuchOptionException e){
				this.show(e.getMessage());
			}
		}while(option != QUIT);
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
		this.show(msg);

		try{
			return input.nextLine();
		}catch(Exception e){
			return e.getMessage();
		}
	}

	/**
	 * Solicita o c�digo de autentica��o
	 * @param facebook
	 * @return C�digo de autentica��o
	 */
	public String showAuthenticationMessage(Facebook facebook){
		selection = new StringSelection(facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.getProperty()));
		clipboard.setContents(selection, selection);
		
		return this.showInput("O link de autentica��o foi colado na sua\n�rea de transfer�ncia. Cole no seu\nnavegador e copie o c�digo de resposta:");
	}
}
