package br.com.dsoo.facebook.view.panels;

import br.com.dsoo.facebook.user.User;

public abstract class ConfigurePanel extends JPanelCustom{

	private static final long serialVersionUID = 1L;
	public static final int SAVE = 0, CANCEL = 1;
	
	boolean changed = false;
	int choice = 1;
	
	public ConfigurePanel(User user){
		super(user);
	}

	public int getChoice(){
		return choice;
	}
	
	public boolean changed(){
		return changed;
	}
	
	void setChanged(boolean change){
		changed = change;
	}
	
	public abstract Object getData();
	public abstract void setData();
}
