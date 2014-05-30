package br.com.dsoo.facebook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import br.com.dsoo.facebook.user.User;

public class JPanelCustom extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	User user;
	
	public JPanelCustom(User user){
		super();
		this.user = user;
	}
	
	void setDataToFrame(){
		((FaceAppFrame)getParent().getParent().getParent()).setData(user);
	}

	@Override
	public void actionPerformed(ActionEvent e){}
}
