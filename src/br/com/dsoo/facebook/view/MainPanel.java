package br.com.dsoo.facebook.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;

import javax.mail.MessagingException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.user.User;
import facebook4j.FacebookException;

public class MainPanel extends JPanelCustom{

	private static final long serialVersionUID = 1L;

	private JLabel userName, activityReportLabel, agendaLabel;
	
	
	public MainPanel(User user) throws FacebookException{
		super(user);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		userName = new JLabel(user.getFirstName());
		userName.addMouseListener(this);
		activityReportLabel = new JLabel("Resumo de atividades");
		activityReportLabel.addMouseListener(this);
		agendaLabel = new JLabel("Agenda");
		agendaLabel.addMouseListener(this);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.GRAY);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JSeparator separator = new JSeparator();
		
		JLabel userPic = new JLabel(new ImageIcon(user.getUserPic()));
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(337, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(activityReportLabel)
					.addContainerGap(44, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(agendaLabel)
					.addContainerGap(112, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(userName)
					.addContainerGap(94, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(userPic)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
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
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}

	@Override
	public void actionPerformed(ActionEvent e){
	}

	@Override
	public void mouseClicked(MouseEvent e){
		showLoading();
		try{
			String data = null, title = null;
			
			if(e.getSource() == userName){
				title = "Dados do usuário";
				data = user.toString();
			}else if(e.getSource() == activityReportLabel){
				title = "Resumo de Atividades";
				data = user.getActivitiesReport();
			}else{
				title = "Agenda";
				data = Utils.formatEvents(user.getUserAgenda());
			}
			
			hideLoading();
			Alert.show(this, title, data);
		}catch(FacebookException | ParseException | MessagingException | IOException e1){
			Alert.showError(e1);
		}
	}
}
