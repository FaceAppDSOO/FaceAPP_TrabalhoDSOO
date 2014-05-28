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

	private static Clipboard clipboard;
	private static StringSelection selection;

	public static String showInput(String title, String msg){
		return showInput(null, title, msg);
	}
	
	public static String showInput(Component parent, String title, String msg){
		return showInput(parent, title, msg, INFORMATION_MESSAGE);
	}
	
	
	public static void show(String title, String msg){
		show(null, title, msg, INFORMATION_MESSAGE);
	}
	
	public static void show(Component parent, String title, String msg){
		show(parent, title, msg, INFORMATION_MESSAGE);
	}
	
	public static void showError(Exception e){
		showError(e.getMessage());
	}
	
	public static void showError(String error){
		show(null, "Erro", error, ERROR_MESSAGE);
	}
	
	public static String showAuthenticationMessage(Facebook facebook){
		selection = new StringSelection(facebook.getOAuthAuthorizationURL(AppData.AUTH_URL.getValue()));
		clipboard.setContents(selection, selection);
		
		return showInput("Autenticação", "O link de autenticação foi colado na sua área de transferência.\nCole no seu navegador e copie o código de resposta:");
	}

	public static String showInput(Component parent, String title, String msg, int type){
		return showInputDialog(parent, msg, title, type);
	}
	
	public static void show(Component parent, String title, String msg, int type){
		showMessageDialog(parent, msg, title, type);
	}
}
