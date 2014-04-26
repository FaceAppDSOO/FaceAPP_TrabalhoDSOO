package br.com.dsoo.facebook.view;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JOptionPane;

import br.com.dsoo.facebook.settings.Constants;
import facebook4j.Facebook;

public class Ui{

	public Ui(){}
	
	public static void show(String msg){
		JOptionPane.showMessageDialog(null, msg);
	}
	
	public static String showInput(String msg){
		return JOptionPane.showInputDialog(msg);
	}

	public static String showAuthenticationDialog(Facebook facebook){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(facebook.getOAuthAuthorizationURL(Constants.AUTH_URL));
		clipboard.setContents(selection, selection);
		
		return JOptionPane.showInputDialog("O link de autenticação foi colado na sua área de\ntransferência. Cole no seu navegador e copie o\ncódigo de resposta no campo abaixo:");
	}
}
