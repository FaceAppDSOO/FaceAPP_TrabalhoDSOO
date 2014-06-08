package br.com.dsoo.facebook.view.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import br.com.dsoo.facebook.view.panels.JPanelCustom;
import br.com.dsoo.facebook.view.panels.MainPanel;
import br.com.dsoo.facebook.view.panels.SettingsPanel;
import facebook4j.FacebookException;

public class FaceAppFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JMenuItem faceItem, quitItem, settingsItem;
	
	private JPanelCustom mainPanel, settingsPanel;
	
	private User user;
	
	public FaceAppFrame(User user) throws FacebookException{
		super("FaceApp");
		this.user = user;
		
		setForm();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(600, 400);
		
		setLocation(getX() - 300, getY() - 200);
		
		setResizable(false);
		setVisible(true);
	}
	
	private void setForm() throws FacebookException{
		JMenuBar bar = new JMenuBar();
		JMenu faceMenu, userMenu;
		
		setJMenuBar(bar);		
		
		bar.add(faceMenu = new JMenu(user.getFirstName()));
		bar.add(userMenu = new JMenu("Configura��es"));
		
		faceMenu.add(faceItem = new JMenuItem("Facebook"));
		faceItem.addActionListener(this);
		faceMenu.addSeparator();
		faceMenu.add(quitItem = new JMenuItem("Sair"));
		quitItem.addActionListener(this);
		userMenu.add(settingsItem = new JMenuItem("Configura��es da aplica��o"));
		settingsItem.addActionListener(this);
		
		setContentPane(mainPanel = new MainPanel(user));
		settingsPanel = new SettingsPanel(user);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == quitItem){
			if(Alert.showYesNo(this, "Sair", "Deseja mesmo sair?")){
				user.logout();
				System.exit(DO_NOTHING_ON_CLOSE);
			}
		}else{
			getContentPane().setVisible(false);
			user = ((JPanelCustom)getContentPane()).getUser();
			getContentPane().setVisible(false);
			
			if(e.getSource() == faceItem){
				mainPanel.setUser(user);
				setContentPane(mainPanel);
				mainPanel.setVisible(true);
				((MainPanel)mainPanel).initializeNewsFeed();
			}else if(e.getSource() == settingsItem){
				settingsPanel.setUser(user);
				setContentPane(settingsPanel);
				settingsPanel.setVisible(true);
			}
			
			pack();
		}
	}

}