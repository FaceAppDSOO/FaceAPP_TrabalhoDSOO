package br.com.dsoo.facebook.view.forms.panels;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.LoadingDialog;
import br.com.dsoo.facebook.view.forms.frames.FaceAppFrame;

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
		Container parent = getParent();
		if(parent != null){
			parent = parent.getParent();
			if(parent != null){
				parent = parent.getParent();
			}
		}

		loading = new LoadingDialog(parent == null ? null : (JFrame)parent, "Carregando...");
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
	
	public void showChild(Component comp, String title, int height, int width){
		JFrame f = new JFrame(title);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLocationRelativeTo(this);
		
		if(comp instanceof JPanel){
			((JPanel)comp).setSize(new Dimension(height, width));
			f.setContentPane((JPanel)comp);
		}else{
			JPanel panel = new JPanel();
			GroupLayout layout = new GroupLayout(panel);
			
			layout.setHorizontalGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comp, GroupLayout.DEFAULT_SIZE, width, Short.MAX_VALUE)
					.addContainerGap());
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(comp, GroupLayout.DEFAULT_SIZE, height, Short.MAX_VALUE)
					.addContainerGap());
			
			panel.setLayout(layout);
			f.setContentPane(panel);
		}
		
		f.setResizable(false);
		f.pack();
		f.setVisible(true);
	}

	@Override public void actionPerformed(ActionEvent e){}
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseClicked(MouseEvent arg0){}

}
