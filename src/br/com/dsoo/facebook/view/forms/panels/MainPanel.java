package br.com.dsoo.facebook.view.forms.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import facebook4j.Event;
import facebook4j.FacebookException;
import facebook4j.Post;

public class MainPanel extends JPanelCustom{

	private static final long serialVersionUID = 1L;

	private JLabel userName, activityReportLabel, agendaLabel, userPic;
	private JButton btPost;
	private JPanel userPanel;
	private JScrollPane container;
	private JSeparator separator;
	
	public MainPanel(User user) throws FacebookException{
		super(user);
		showLoading();
		setBackground(SystemColor.menu);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		//labels
		userName = new JLabel(user.getFirstName());
		userName.setToolTipText(user.getName());
		activityReportLabel = new JLabel("Resumo de atividades");
		agendaLabel = new JLabel("Agenda");
		separator = new JSeparator();
		
		//botões
		btPost = new JButton("Nova postagem");
		
		//panels
		userPanel = new JPanel();
		container = new JScrollPane(loadUserFeed());
		container.setViewportBorder(null);
		
		userPic = new JLabel(new ImageIcon(user.getUserPic()));
		
		userPanel.setAutoscrolls(true);
		userPanel.setForeground(Color.GRAY);
		userPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(userPanel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(container, GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
						.addComponent(btPost))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(userPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btPost)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(container, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
					.addGap(6))
		);
		
		GroupLayout gl_userPanel = new GroupLayout(userPanel);
		gl_userPanel.setHorizontalGroup(
			gl_userPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(activityReportLabel)
					.addContainerGap(44, Short.MAX_VALUE))
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(agendaLabel)
					.addContainerGap(112, Short.MAX_VALUE))
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(userName)
					.addContainerGap(94, Short.MAX_VALUE))
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(userPic)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		gl_userPanel.setVerticalGroup(
			gl_userPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_userPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(userPic)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(userName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(activityReportLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(agendaLabel)
					.addContainerGap(213, Short.MAX_VALUE))
		);
		userPanel.setLayout(gl_userPanel);
		setLayout(groupLayout);
		
		addListeners();
		
		hideLoading();
	}
	
	@Override
	void addListeners(){
		btPost.addActionListener(this);
		userName.addMouseListener(this);
		activityReportLabel.addMouseListener(this);
		agendaLabel.addMouseListener(this);
	}

	private ListItemPanel loadUserFeed(){
		ListItemPanel panel = new ListItemPanel();
		panel.setBorder(null);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Post[] userFeed;

		try{
			userFeed = user.getNewsFeed(20);
		}catch(FacebookException e){
			Alert.showError(e);
			return null;
		}
		
		for(Post post : userFeed){
			try{
				panel.add(new PostPanel(user.getAccount(), post));
				panel.add(Box.createRigidArea(new Dimension(10, 10)));
			}catch(FacebookException e){}
		}
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btPost){
			try{
				String post = Alert.showInput(this, "Nova postagem", "No que você está pensando?");
				
				if(post != null && post != ""){
					user.postStatusMessage(post);
				}
			}catch(FacebookException e1){
				Alert.showError(e1);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){
		showLoading();
		try{
			String data = null, title = null;
			
			if(e.getSource() == userName){
				data = user.toString();
				title = "Dados do usuário";
			}else if(e.getSource() == activityReportLabel){
				data = user.getActivitiesReport();
				title = "Resumo de Atividades";
			}else{				
				JScrollPane scroll = new JScrollPane(loadUserEvents());
				scroll.setViewportBorder(null);
				
				showChild(scroll, "Eventos", 170, 320);
			}
			
			hideLoading();
			
			if(data != null){
				Alert.show(this, title, data);
			}
		}catch(FacebookException | ParseException | MessagingException | IOException e1){
			Alert.showError(e1);
			hideLoading();
		}
	}

	private ListItemPanel loadUserEvents(){
		ArrayList<Event> evs = null;
		
		ListItemPanel panel = new ListItemPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		try{
			evs = user.getUserAgenda();
		}catch(FacebookException | ParseException e){
			Alert.showError(e);
			return null;
		}
		
		for(Event event : evs){
			panel.add(new EventPanel(event));
			panel.add(Box.createRigidArea(new Dimension(10, 10)));
		}
		
		return panel;
	}
}
