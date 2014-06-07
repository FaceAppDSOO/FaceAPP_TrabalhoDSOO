package br.com.dsoo.facebook.view.forms.panels;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.com.dsoo.facebook.user.User;

import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class SettingsPanel extends JPanelCustom implements ChangeListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private final JSpinner spinnerFeed, spinnerActivity, spinnerAgenda;
	private final JTextField txtActivityEmail;
	private final JCheckBox chkActivitySendEmail;

	public SettingsPanel(User user){
		super(user);
		
		JPanel pLeft = new JPanel();
		JLabel lblMensagensASerem = new JLabel("Quantidade de postagens do Feed");

		spinnerFeed = new JSpinner();
		spinnerFeed.setValue(20);
		
		GroupLayout gl_pLeft = new GroupLayout(pLeft);
		gl_pLeft.setHorizontalGroup(
			gl_pLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pLeft.createSequentialGroup()
					.addComponent(lblMensagensASerem)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerFeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pLeft.setVerticalGroup(
			gl_pLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pLeft.createSequentialGroup()
					.addGroup(gl_pLeft.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMensagensASerem)
						.addComponent(spinnerFeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(258, Short.MAX_VALUE))
		);
		pLeft.setLayout(gl_pLeft);
		
		JPanel pRight = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(pLeft, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(pRight, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pRight, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
						.addComponent(pLeft, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Resumo de atividades", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(130, 135, 144)), "Agenda", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_pRight = new GroupLayout(pRight);
		gl_pRight.setHorizontalGroup(
			gl_pRight.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
		);
		gl_pRight.setVerticalGroup(
			gl_pRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pRight.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(131, Short.MAX_VALUE))
		);
		
		JLabel lblDiasASerem_1 = new JLabel("Dias a serem pesquisados");
		
		spinnerAgenda = new JSpinner();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(lblDiasASerem_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spinnerAgenda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(49, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiasASerem_1)
						.addComponent(spinnerAgenda, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblDiasASerem = new JLabel("Dias a serem pesquisados");
		
		spinnerActivity = new JSpinner();
		
		chkActivitySendEmail = new JCheckBox("Enviar Resumo como e-mail");
		
		JLabel lblNewLabel = new JLabel("E-mail");
		
		txtActivityEmail = new JTextField();
		txtActivityEmail.setEnabled(chkActivitySendEmail.isSelected());
		txtActivityEmail.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblDiasASerem)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerActivity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txtActivityEmail, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
						.addComponent(chkActivitySendEmail))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDiasASerem)
						.addComponent(spinnerActivity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chkActivitySendEmail)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(txtActivityEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		pRight.setLayout(gl_pRight);
		setLayout(groupLayout);
		
		addListeners();
	}
	
	@Override
	void addListeners(){
		spinnerActivity.addChangeListener(this);
		spinnerAgenda.addChangeListener(this);
		spinnerFeed.addChangeListener(this);
		chkActivitySendEmail.addChangeListener(this);
		txtActivityEmail.addKeyListener(this);
	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		if(e.getSource() == spinnerFeed){
			int size = (int)spinnerFeed.getValue();
			if(size > 0)
				user.getSettings().setNewsFeedSize(size);
		}else if(e.getSource() == spinnerActivity){
			int days = (int)spinnerActivity.getValue();
			if(days > 0){
				user.getSettings().setActivitiesReportSince(days);
			}
		}else if(e.getSource() == spinnerAgenda){
			int days = (int)spinnerAgenda.getValue();
			if(days > 0){
				user.getSettings().setAgendaSince(days);
			}
		}else if(e.getSource() == chkActivitySendEmail){
			boolean enabled = chkActivitySendEmail.isSelected();
			txtActivityEmail.setEnabled(enabled);
			user.getSettings().setSendActivitiesReportEmail(enabled);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getSource() == txtActivityEmail){
			user.getSettings().setActivitiesReportEmail(txtActivityEmail.getText().trim());
		}
	}
}
