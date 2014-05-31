package br.com.dsoo.facebook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.dsoo.facebook.user.User;

public class JPanelCustom extends JPanel implements ActionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	LoadingDialog loading;
	User user;
	
	public JPanelCustom(User user){
		super();
		this.user = user;
	}
	
	void setDataToFrame(){
		((FaceAppFrame)getParent().getParent().getParent()).setData(user);
	}
	
	void showLoading(){
		showLoading("Carregando...");
	}
	
	void showLoading(String msg){
		loading = new LoadingDialog(((JFrame)getParent().getParent().getParent()), msg);
	}
	
	void hideLoading(){
		if(loading != null){
			loading.setVisible(false);
			loading = null;
		}
	}

	public User getUser(){
		return user;
	}

	public void setUser(User user){
		this.user = user;
	}

	@Override public void actionPerformed(ActionEvent e){}
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseClicked(MouseEvent arg0){}

}
