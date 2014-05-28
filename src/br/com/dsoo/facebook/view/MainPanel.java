package br.com.dsoo.facebook.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.ParseException;

import javax.mail.MessagingException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import br.com.dsoo.facebook.logic.Utils;
import br.com.dsoo.facebook.user.User;
import facebook4j.FacebookException;

public class MainPanel extends JPanelCustom implements MouseListener{

	private static final long serialVersionUID = 1L;

	private JLabel userLabel, activityReportLabel, agendaLabel;
	
	public MainPanel(User user) throws FacebookException{
		super(user);
		setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		userLabel = new JLabel("username");
		userLabel.addMouseListener(this);
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		activityReportLabel = new JLabel("Resumo de atividades");
		userLabel.addMouseListener(this);
		agendaLabel = new JLabel("Agenda");
		userLabel.addMouseListener(this);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.GRAY);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
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
		
		JSeparator separator = new JSeparator();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(separator, GroupLayout.PREFERRED_SIZE, 137, Short.MAX_VALUE)
						.addComponent(userLabel)
						.addComponent(activityReportLabel)
						.addComponent(agendaLabel))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addComponent(userLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(activityReportLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(agendaLabel)
					.addContainerGap(176, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
	
	@Override
	void setForm(){
		
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		
	}

	private Font oldLabelFont;
	@Override
	public void mouseEntered(MouseEvent e){
		if(!(e.getSource() instanceof JLabel))
			return;

		oldLabelFont = ((JLabel)e.getSource()).getFont();
		((JLabel)e.getSource()).setFont(new Font(oldLabelFont.getName(), Font.BOLD, oldLabelFont.getSize()));
	}

	@Override
	public void mouseExited(MouseEvent e){
		if(!(e.getSource() instanceof JLabel))
			return;

		((JLabel)e.getSource()).setFont(oldLabelFont);
	}
	
	@Override
	public void mouseClicked(MouseEvent e){
		toggleLoading();
		try{
			if(e.getSource() == userLabel){
				Alert.show(this, "Dados do usuário", user.toString());
			}else if(e.getSource() == activityReportLabel){			
				Alert.show(this, "Resumo de Atividades", user.getActivitiesReport());
			}else if(e.getSource() == agendaLabel){
				Alert.show(this, "Agenda", Utils.formatEvents(user.getUserAgenda()));
			}
		}catch(FacebookException | ParseException | MessagingException | IOException e1){
			Alert.showError(e1);
		}finally{
			toggleLoading();
		}
	}


	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
}
