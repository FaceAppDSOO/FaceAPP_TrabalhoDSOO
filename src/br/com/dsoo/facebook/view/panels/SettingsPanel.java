package br.com.dsoo.facebook.view.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.Alert;
import facebook4j.Friend;

public class SettingsPanel extends JPanelCustom implements ChangeListener, KeyListener{

	private static final long serialVersionUID = 1L;
	private final JSpinner spinnerFeed, spinnerActivity, spinnerAgenda;
	private final JTextField txtActivityEmail;
	private final JCheckBox chkActivitySendEmail, chkLikePhotos, chkDownloadPhotos, chkLikeUserStatuses;
	private final JButton btConfigureUsersToLikeStatuses, btConfigurePhotoDownloadPath;

	public SettingsPanel(User user){
		super(user);
		
		JPanel pLeft = new JPanel();
		JLabel lblMensagensASerem = new JLabel("Quantidade de postagens do Feed");

		spinnerFeed = new JSpinner();
		spinnerFeed.setValue(20);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Opera\u00E7\u00F5es autom\u00E1ticas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		chkLikePhotos = new JCheckBox("Curtir fotos onde \u00E9 marcado");
		
		chkDownloadPhotos = new JCheckBox("Baixar fotos onde \u00E9 marcado");
		chkDownloadPhotos.setToolTipText("O download de fotos \u00E9 limitado em 25 fotos por vez!");
		
		chkLikeUserStatuses = new JCheckBox("Resposta autom\u00E1tica do bate-papo");
		
		btConfigureUsersToLikeStatuses = new JButton("Configurar");
		btConfigureUsersToLikeStatuses.setFont(new Font("Tahoma", Font.PLAIN, 8));
		btConfigureUsersToLikeStatuses.setEnabled(false);
		
		btConfigurePhotoDownloadPath = new JButton("...");
		btConfigurePhotoDownloadPath.setToolTipText("Configurar pasta de destino");
		btConfigurePhotoDownloadPath.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(chkLikePhotos)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(chkDownloadPhotos)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btConfigurePhotoDownloadPath, 0, 0, Short.MAX_VALUE))
						.addComponent(chkLikeUserStatuses)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(btConfigureUsersToLikeStatuses)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(chkLikePhotos)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(chkDownloadPhotos)
						.addComponent(btConfigurePhotoDownloadPath))
					.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
					.addComponent(chkLikeUserStatuses)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btConfigureUsersToLikeStatuses))
		);
		panel_2.setLayout(gl_panel_2);
		
		GroupLayout gl_pLeft = new GroupLayout(pLeft);
		gl_pLeft.setHorizontalGroup(
			gl_pLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pLeft.createSequentialGroup()
					.addGroup(gl_pLeft.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pLeft.createSequentialGroup()
							.addComponent(lblMensagensASerem)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerFeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pLeft.setVerticalGroup(
			gl_pLeft.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pLeft.createSequentialGroup()
					.addGroup(gl_pLeft.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMensagensASerem)
						.addComponent(spinnerFeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(229, Short.MAX_VALUE))
		);
		pLeft.setLayout(gl_pLeft);
		
		JPanel pRight = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(pLeft, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pRight, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pLeft, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
						.addComponent(pRight, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Resumo de atividades", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Agenda", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_pRight = new GroupLayout(pRight);
		gl_pRight.setHorizontalGroup(
			gl_pRight.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
		);
		gl_pRight.setVerticalGroup(
			gl_pRight.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pRight.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(231, Short.MAX_VALUE))
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
		
		chkLikePhotos.addChangeListener(this);

		chkDownloadPhotos.addChangeListener(this);
		btConfigurePhotoDownloadPath.addActionListener(this);
		
		chkLikeUserStatuses.addChangeListener(this);
		btConfigureUsersToLikeStatuses.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		//Configurar pasta de destino
		if(e.getSource() == btConfigurePhotoDownloadPath){
			JFileChooser chooser = new JFileChooser(user.getSettings().getDownloadPhotoFilePath());
			chooser.setDialogTitle("Pasta destino");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setApproveButtonText("Selecionar");
			
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				File path = chooser.getSelectedFile();
				path.mkdirs();
				try{
					user.getSettings().setDownloadPhotoFilePath(path.getCanonicalPath() + "\\");
				}catch(IOException e1){
					Alert.showError(e1);
				}
			}
		//Configurar usuários da resposta automática
		}else if(e.getSource() == btConfigureUsersToLikeStatuses){
			UsersChooserPanel chooser = new UsersChooserPanel(user);

			showChild(chooser, "Selecionar amigos");
			
//			JDialog d = new JDialog((JFrame)findParent(), "Selecionar amigos", true);
//			d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			d.setContentPane(chooser);
//			d.setResizable(false);
//			d.pack();
//			d.setLocationRelativeTo(this);
//			d.setVisible(true);
			
			if(chooser.getChoice() == ConfigurePanel.SAVE){
				Friend[] friends = (Friend[])chooser.getData();
				String ids = "";
				
				for(Friend friend : friends){
					ids += friend.getId() + ",";
				}
				
				user.getSettings().setUsersIdsToLikeStatuses(ids.substring(0, ids.length() - 1).split(","));
			}
		}
	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		boolean flag = false;
		int param = 0;
		
		//Quantidade de postagens do Feed
		if(e.getSource() == spinnerFeed){
			param = (int)spinnerFeed.getValue();
			if(param > 0)
				user.getSettings().setNewsFeedSize(param);
			
		//Dias a serem pesquisados (Atividades)
		}else if(e.getSource() == spinnerActivity){
			param = (int)spinnerActivity.getValue();
			if(param > 0){
				user.getSettings().setActivitiesReportSince(param);
			}
			
		//Dias a serem pesquisados (Agenda)
		}else if(e.getSource() == spinnerAgenda){
			param = (int)spinnerAgenda.getValue();
			if(param > 0){
				user.getSettings().setAgendaSince(param);
			}
			
		//Enviar Resumo como e-mail
		}else if(e.getSource() == chkActivitySendEmail){
			flag = chkActivitySendEmail.isSelected();
			txtActivityEmail.setEnabled(flag);
			user.getSettings().setSendActivitiesReportEmail(flag);
			
		//Curtir fotos onde é marcado
		}else if(e.getSource() == chkLikePhotos){
			flag = chkLikePhotos.isSelected();
			user.getSettings().setLikePhotosWhenTagged(flag);
			
		//Baixar fotos onde é marcado
		}else if(e.getSource() == chkDownloadPhotos){
			flag = chkDownloadPhotos.isSelected();
			user.getSettings().setDownloadPhotosWhenTagged(flag);
			
		//Resposta automática no bate-papo
		}else if(e.getSource() == chkLikeUserStatuses){
			flag = chkLikeUserStatuses.isSelected();
			user.getSettings().setLikeUsersListStatuses(flag);
			btConfigureUsersToLikeStatuses.setEnabled(flag);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getSource() == txtActivityEmail){
			user.getSettings().setActivitiesReportEmail(txtActivityEmail.getText().trim());
		}
	}
}
