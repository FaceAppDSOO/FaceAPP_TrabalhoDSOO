package br.com.dsoo.facebook.view.panels;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.LoadingDialog;

public abstract class JPanelCustom extends JPanel implements ActionListener, MouseListener, KeyListener{

	private static final long serialVersionUID = 1L;
	
	LoadingDialog loading;
	User user;
	
	public JPanelCustom(User user){
		super();
		this.user = user;
	}
	
	Container findParent(){
		Container parent = getParent();
		if(parent != null){
			parent = parent.getParent();
			if(parent != null){
				parent = parent.getParent();
			}
		}
		
		return parent;
	}
	
	void showLoading(){
		Container parent = findParent();

		loading = new LoadingDialog(parent == null ? null : (JFrame)parent, "Carregando...");
	}
	
	void closeParent(){
		Container parent = findParent();
		WindowEvent wev = null;
		if(parent instanceof JFrame){			
			wev = new WindowEvent((JFrame)parent, WindowEvent.WINDOW_CLOSING);
		}else if(parent instanceof JDialog){
			wev = new WindowEvent((JDialog)parent, WindowEvent.WINDOW_CLOSING);
		}
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
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
	
	public void showChild(JPanel panel, String title){
		showChild(panel, title, panel.getHeight(), panel.getWidth());
	}
	
	public void showChild(Component comp, String title, int height, int width){
		JDialog dialog = new JDialog((JFrame)findParent(), title, true);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(this);
		
		if(comp instanceof JPanel){
			((JPanel)comp).setSize(new Dimension(height, width));
			dialog.setContentPane((JPanel)comp);
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
			dialog.setContentPane(panel);
		}
		
		dialog.setResizable(false);
		dialog.pack();
		
		hideLoading();
		
		dialog.setLocation(
				dialog.getX() - (dialog.getWidth() / 2),
				dialog.getY() - (dialog.getHeight() / 2)
				);
		
		dialog.setVisible(true);
	}

	@Override public void actionPerformed(ActionEvent e){}
	
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseClicked(MouseEvent arg0){}

	@Override public void keyPressed(KeyEvent e){}
	@Override public void keyReleased(KeyEvent e){}
	@Override public void keyTyped(KeyEvent e){}

	void addListeners(){}
	void doPop(JPopupMenu menu, MouseEvent e){}
}
