package br.com.dsoo.facebook.view.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javafx.scene.input.MouseButton;

import javax.mail.MessagingException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.border.EtchedBorder;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import facebook4j.Event;
import facebook4j.FacebookException;
import facebook4j.Post;

public class MainPanel extends JPanelCustom implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;
	
	private NewsFeedLoader newsFeedLoader = new NewsFeedLoader();
	private boolean feedLoaded = false;

	private JLabel userName, activityReportLabel, agendaLabel, userPic, lbLoading = new JLabel("Carregando...");
	private JButton btPost;
	private JPanel userPanel;
	private JScrollPane container;
	private JSeparator separator;

	private JPopupMenu feedPopupMenu;
	private JMenuItem refreshItem;
	
	public MainPanel(User user) throws FacebookException{
		super(user);
		showLoading();
		
		newsFeedLoader.addPropertyChangeListener(this);
		
		lbLoading.setVisible(false);
		
		try{
			user.likeAndDownloadTaggedPhotos();
		}catch(IOException e){
			Alert.showError(e);
		}
		
		setBackground(SystemColor.menu);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		//labels
		userName = new JLabel(user.getFirstName());
		userName.setToolTipText(user.getName());
		activityReportLabel = new JLabel("Resumo de atividades");
		agendaLabel = new JLabel("Agenda");
		separator = new JSeparator();
		
		btPost = new JButton("Nova postagem");
		
		userPanel = new JPanel();
		container = new JScrollPane();
		container.setViewportBorder(null);
		container.setBorder(null);
		loadUserFeed();
		
		feedPopupMenu = new JPopupMenu("Feed de notícias");
		refreshItem = new JMenuItem("Recarregar");
		feedPopupMenu.add(refreshItem);
		
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
						.addComponent(container, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lbLoading)
							.addPreferredGap(ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
							.addComponent(btPost)))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(userPanel, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btPost)
						.addComponent(lbLoading))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(container, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
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
	protected void addListeners(){
		
		btPost.addActionListener(this);
		userName.addMouseListener(this);
		activityReportLabel.addMouseListener(this);
		agendaLabel.addMouseListener(this);

		container.addMouseListener(this);
		
		refreshItem.addActionListener(this);
	}
	
	public void initializeNewsFeed(){
		loadUserFeed();
	}

	private void loadUserFeed(){
		if(feedLoaded){
			newsFeedLoader = new NewsFeedLoader();
			newsFeedLoader.addPropertyChangeListener(this);
			newsFeedLoader.execute();
			container.setViewport(new JViewport());
		}else{
			newsFeedLoader.execute();
		}
		lbLoading.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		//Nova postagem
		if(e.getSource() == btPost){
			try{
				String post = Alert.showInput(this, "Nova postagem", "No que você está pensando?");
				
				if(post != null && post != ""){
					user.postStatusMessage(post);
				}
			}catch(FacebookException e1){
				Alert.showError(e1);
			}
		//Botão direito no feed
		}else if(e.getSource() == refreshItem){
			initializeNewsFeed();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){
		Component comp = e.getComponent();
		
		if(e.getButton() == MouseButton.SECONDARY.ordinal()){
			if(comp == container || comp instanceof PostPanel){
				doPop(feedPopupMenu, e);
			}
		}else if(comp == userName || comp == activityReportLabel || comp == agendaLabel){
			showLoading();
			try{
				String data = null, title = null;

				if(comp == userName){
					data = user.toString();
					title = "Dados do usuário";
				}else if(comp == activityReportLabel){
					data = user.getActivitiesReport();
					title = "Resumo de Atividades";
				}else if(comp == agendaLabel){
					ListItemPanel events = loadUserEvents();
					
					if(events == null){
						Alert.show(this, "Agenda", "Nenhum evento encontrado!");
						return;
					}
					
					JScrollPane scroll = new JScrollPane(events);
					scroll.setViewportBorder(null);

					showChild(scroll, "Eventos", 200, 350);
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
		
		if(evs.size() == 0){
			return null;
		}
		
		for(int i = evs.size() - 1; i >= 0; i--){
			panel.add(new EventPanel(evs.get(i)));
			panel.add(Box.createRigidArea(new Dimension(10, 10)));
		}
		
		return panel;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt){
		if(evt.getSource() instanceof NewsFeedLoader){
			if(evt.getPropertyName().equalsIgnoreCase(NewsFeedLoader.PROPERTY_STATE)){
				if(newsFeedLoader.getState().equals(SwingWorker.StateValue.DONE)){
					try{
						container.setViewportView(newsFeedLoader.get());
						container.setViewportBorder(null);
						container.setBorder(null);
						lbLoading.setVisible(false);
						feedLoaded = true;
					}catch(InterruptedException e){
					}catch(ExecutionException ex){
						Alert.showError(ex);
					}
				}
			}
		}
	}
	
	private class NewsFeedLoader extends SwingWorker<ListItemPanel, Void>{

		public static final String PROPERTY_STATE = "state";
		
		@Override
		protected ListItemPanel doInBackground() throws Exception{
			ListItemPanel panel = new ListItemPanel();
			panel.setBorder(null);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			Post[] userFeed;

			try{
				userFeed = user.getNewsFeed();
			}catch(FacebookException | IOException e){
				Alert.showError(e);
				return null;
			}
			
			for(Post post : userFeed){
				try{
					panel.add(new PostPanel(user.getAccount(), post));
					panel.add(Box.createRigidArea(new Dimension(10, 10)));
				}catch(FacebookException e){}
				Thread.sleep(500);
			}
			
			return panel;
		}
	}
}
