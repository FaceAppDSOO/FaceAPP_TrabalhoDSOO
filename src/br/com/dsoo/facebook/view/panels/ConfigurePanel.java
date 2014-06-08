package br.com.dsoo.facebook.view.panels;

import javax.swing.JButton;

import br.com.dsoo.facebook.user.User;

public abstract class ConfigurePanel extends JPanelCustom{

	private static final long serialVersionUID = 1L;
	public static final int SAVE = 0, CANCEL = 1;
	
	final JButton btSave, btCancel;
	int choice = 1;
	
	public ConfigurePanel(User user){
		super(user);
		btSave = new JButton("Salvar");
		btCancel = new JButton("Cancelar");
	}

	public int getChoice(){
		return choice;
	}
	
	public abstract Object getData();
	public abstract void setData();
}
