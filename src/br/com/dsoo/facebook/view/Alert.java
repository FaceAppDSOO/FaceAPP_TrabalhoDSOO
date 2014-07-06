package br.com.dsoo.facebook.view;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JOptionPane;

import br.com.dsoo.facebook.logic.constants.AppData;
import facebook4j.Facebook;

public class Alert extends JOptionPane{

	static{
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	private static final long serialVersionUID = 1L;
	
	public static final String ERRO_FILHO_DA_PUTA = "#613";

	private static Clipboard clipboard;
	private static StringSelection selection;

	public static boolean showYesNo(Component parent, String title, String msg){
		return showConfirm(parent, title, msg, YES_OPTION, YES_OPTION, QUESTION_MESSAGE, "Sim", "Não");
	}

	public static String showInput(Component parent, String title, String msg){
		return showInput(parent, title, msg, INFORMATION_MESSAGE);
	}
	
	public static void show(Component parent, String title, String msg){
		show(parent, title, msg, INFORMATION_MESSAGE);
	}
	
	public static void showError(Exception e){
		if(!e.getMessage().contains(ERRO_FILHO_DA_PUTA)){
			showError(e.getMessage());
		}
	}
	
	public static void showError(String error){
		show(null, "Erro", error, ERROR_MESSAGE);
	}
	
	public static String showAuthenticationMessage(Facebook facebook){
		selection = new StringSelection(facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.value));
		clipboard.setContents(selection, selection);
		
		return showInput(null, "Autenticação", "O link de autenticação foi colado na sua área de transferência.\nCole no seu navegador e copie o código de resposta:");
	}
	
	public static boolean showConfirm(Component parent, String title, String msg, int flags, int yes, int icon, String ...options){
		return showOptionDialog(parent, msg, title, flags, icon, null, options, options[options.length - 1]) == yes;
	}

	public static String showInput(Component parent, String title, String msg, int icon){
		return showInputDialog(parent, msg, title, icon);
	}
	
	public static void show(Component parent, String title, String msg, int icon){
		showMessageDialog(parent, msg, title, icon);
	}
}
