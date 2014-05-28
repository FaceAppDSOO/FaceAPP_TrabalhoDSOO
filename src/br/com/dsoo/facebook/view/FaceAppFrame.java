package br.com.dsoo.facebook.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import facebook4j.FacebookException;
import br.com.dsoo.facebook.user.User;

public class FaceAppFrame extends JFrame implements ActionListener, WindowListener{

	private static final long serialVersionUID = 1L;

	private MainPanel mainPanel;
	
	private JMenuItem faceItem, quitItem, settingsItem;
	
	private User user;
	
	public FaceAppFrame(User user) throws FacebookException{
		super("FaceApp");
		this.user = user;
		
		setForm();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(500, 300);
		setResizable(false);
		setVisible(true);
	}
	
	private void setForm() throws FacebookException{
		JMenuBar bar = new JMenuBar();
		JMenu faceMenu, userMenu;
		
		setJMenuBar(bar);		
		
		bar.add(faceMenu = new JMenu(user.getFirstName()));
		bar.add(userMenu = new JMenu("Configurações"));
		
		faceMenu.add(faceItem = new JMenuItem("Facebook"));
		faceMenu.addSeparator();
		faceMenu.add(quitItem = new JMenuItem("Sair"));
		userMenu.add(settingsItem = new JMenuItem("Configurações da aplicação"));
		
		setContentPane(mainPanel = new MainPanel(user));
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e){
		user.logout();
	}

	@Override public void windowActivated(WindowEvent arg0){}
	@Override public void windowClosing(WindowEvent arg0){}
	@Override public void windowDeactivated(WindowEvent arg0){}
	@Override public void windowDeiconified(WindowEvent arg0){}
	@Override public void windowIconified(WindowEvent arg0){}
	@Override public void windowOpened(WindowEvent arg0){}

}
