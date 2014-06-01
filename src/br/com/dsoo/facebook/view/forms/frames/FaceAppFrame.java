package br.com.dsoo.facebook.view.forms.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import br.com.dsoo.facebook.view.forms.panels.MainPanel;
import facebook4j.FacebookException;

public class FaceAppFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JMenuItem faceItem, quitItem, settingsItem;
	
	private User user;
	
	public FaceAppFrame(User user) throws FacebookException{
		super("FaceApp");
		this.user = user;
		
		setForm();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(600, 400);
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
		faceItem.addActionListener(this);
		faceMenu.addSeparator();
		faceMenu.add(quitItem = new JMenuItem("Sair"));
		quitItem.addActionListener(this);
		userMenu.add(settingsItem = new JMenuItem("Configurações da aplicação"));
		settingsItem.addActionListener(this);
		
		setContentPane(new MainPanel(user));
	}
	
	public void setData(User user){
		this.user = user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		try{
			if(e.getSource() == quitItem){
				if(Alert.showYesNo(this, "Sair", "Deseja mesmo sair?")){
					user.logout();
					System.exit(DO_NOTHING_ON_CLOSE);
				}
			}else if(e.getSource() == faceItem){
				getContentPane().setVisible(false);
				setContentPane(new MainPanel(user));
			}
		}catch(FacebookException e1){
			Alert.showError(e1);
		}
	}

}
