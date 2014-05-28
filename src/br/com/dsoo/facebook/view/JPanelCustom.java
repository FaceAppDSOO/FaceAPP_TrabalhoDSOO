package br.com.dsoo.facebook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.dsoo.facebook.user.User;

public class JPanelCustom extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	Icon loader;
	boolean loaderVisible;
	User user;
	
	public JPanelCustom(User user){
		super();
		this.user = user;
		
		loaderVisible = false;
		loader = new ImageIcon("../../../../../../files/loading.gif");
		
		setForm();
	}
	
	void setForm(){}
	
	void toggleLoading(){
		JLabel icon = new JLabel(loader);
		if(loaderVisible){
			remove(icon);
		}else{
			add(icon);
		}
		loaderVisible = !loaderVisible;
	}

	@Override
	public void actionPerformed(ActionEvent e){}
}
