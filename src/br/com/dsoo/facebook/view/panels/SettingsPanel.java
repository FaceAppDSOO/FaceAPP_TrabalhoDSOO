package br.com.dsoo.facebook.view.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import facebook4j.FacebookException;
import facebook4j.Friend;

public class SettingsPanel extends ConfigurePanel implements ChangeListener, KeyListener, ItemListener{

	private static final long serialVersionUID = 1L;
	private final JSpinner spinnerFeed, spinnerActivity, spinnerAgenda;
	private final JTextField txtActivityEmail;
	private final JCheckBox chkActivitySendEmail, chkLikePhotos, chkDownloadPhotos, chkLikeUserStatuses;
	private final JButton btConfigureUsersToLikeStatuses, btConfigurePhotoDownloadPath, btSave, btCancel;
	
	private String downloadPath, activityEmail;
	private String[] friendsIds;

	public SettingsPanel(User user){
		super(user);
		
		btSave = new JButton("Salvar");
		btSave.setEnabled(false);
		btSave.addActionListener(this);
		btCancel = new JButton("Cancelar");
		btCancel.setEnabled(false);
		btCancel.addActionListener(this);
		
		JPanel pLeft = new JPanel();
		JLabel lblMensagensASerem = new JLabel("Quantidade de postagens do Feed");

		spinnerFeed = new JSpinner();
		spinnerFeed.setValue(20);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Opera\u00E7\u00F5es autom\u00E1ticas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		chkLikePhotos = new JCheckBox("Curtir fotos onde \u00E9 marcado");
		
		chkDownloadPhotos = new JCheckBox("Baixar fotos onde \u00E9 marcado");
		
		chkLikeUserStatuses = new JCheckBox("Curtir status de uma lista de amigos");
		
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
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btCancel)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(pLeft, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addComponent(pRight, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(pLeft, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btSave)
								.addComponent(btCancel)))
						.addComponent(pRight, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
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
		
		setData();
		
		addListeners();
	}
	
	@Override
	void addListeners(){
		spinnerActivity.addChangeListener(this);
		spinnerAgenda.addChangeListener(this);
		spinnerFeed.addChangeListener(this);
		
		chkActivitySendEmail.addItemListener(this);
		txtActivityEmail.addKeyListener(this);
		
		chkLikePhotos.addItemListener(this);

		chkDownloadPhotos.addItemListener(this);
		btConfigurePhotoDownloadPath.addActionListener(this);
		
		chkLikeUserStatuses.addItemListener(this);
		btConfigureUsersToLikeStatuses.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		Object btn = e.getSource();
		
		//Salvar
		if(btn == btSave){
			choice = SAVE;
			getData();
			setChanged(false);
		//Cancelar
		}else if(btn == btCancel){
			choice = CANCEL;
			setData();
			setChanged(false);
		//Configurar pasta de destino
		}else if(btn == btConfigurePhotoDownloadPath){
			JFileChooser chooser = new JFileChooser(downloadPath);
			chooser.setDialogTitle("Pasta destino");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setApproveButtonText("Selecionar");
			
			if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
				File path = chooser.getSelectedFile();
				path.mkdirs();
				try{
					downloadPath = path.getCanonicalPath() + "\\";
					setChanged(true);
					//user.getSettings().setDownloadPhotoFilePath(path.getCanonicalPath() + "\\");
				}catch(IOException e1){
					Alert.showError(e1);
				}
			}
		//Configurar usuários da resposta automática
		}else if(btn == btConfigureUsersToLikeStatuses){
			UsersChooserPanel chooser = new UsersChooserPanel(user);

			showChild(chooser, "Selecionar amigos");
			
			if(chooser.getChoice() == ConfigurePanel.SAVE){
				Friend[] friends = (Friend[])chooser.getData();
				String ids = "";
				
				for(Friend friend : friends){
					ids += friend.getId() + ",";
				}
				
				friendsIds = ids.trim().substring(0, ids.length() - 1).split(",");
				setChanged(true);
				//user.getSettings().setUsersIdsToLikeStatuses(ids.substring(0, ids.length() - 1).trim().split(","));
			}
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e){
		setChanged(true);
		
		txtActivityEmail.setEnabled(chkActivitySendEmail.isSelected());
		btConfigurePhotoDownloadPath.setEnabled(chkDownloadPhotos.isSelected());
		btConfigureUsersToLikeStatuses.setEnabled(chkLikeUserStatuses.isSelected());
	}
	
	@Override
	public void stateChanged(ChangeEvent e){
		setChanged(true);
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		if(e.getSource() == txtActivityEmail){
			activityEmail = txtActivityEmail.getText().trim();
			setChanged(true);
		}
	}

	@Override
	public Object getData(){
		//Variáveis auxiliares
		boolean flag = false;
		int param = 0;
		
		
		//Quantidade de postagens do Feed
		param = (int)spinnerFeed.getValue();
		if(param > 0){
			user.getSettings().setNewsFeedSize(param);
		}
		
		//Dias a serem pesquisados (Atividades)
		param = (int)spinnerActivity.getValue();
		if(param > 0){
			user.getSettings().setActivitiesReportSince(param);
		}
		
		//Dias a serem pesquisados (Agenda)
		param = (int)spinnerAgenda.getValue();
		if(param > 0){
			user.getSettings().setAgendaSince(param);
		}
		
		//Enviar Resumo como e-mail
		flag = chkActivitySendEmail.isSelected();
		txtActivityEmail.setEnabled(flag);
		user.getSettings().setSendActivitiesReportEmail(flag);
		user.getSettings().setActivitiesReportEmail(activityEmail);
		
		//Curtir fotos onde é marcado
		flag = chkLikePhotos.isSelected();
		user.getSettings().setLikePhotosWhenTagged(flag);
		
		//Baixar fotos onde é marcado
		flag = chkDownloadPhotos.isSelected();
		user.getSettings().setDownloadPhotosWhenTagged(flag);
		user.getSettings().setDownloadPhotoFilePath(downloadPath);
		
		//Curtir status de uma lista de amigos
		flag = chkLikeUserStatuses.isSelected();
		user.getSettings().setLikeUsersListStatuses(flag);
		btConfigureUsersToLikeStatuses.setEnabled(flag);
		user.getSettings().setUsersIdsToLikeStatuses(friendsIds);
		
		
		//Armazena os dados no arquivo XML
		try{
			user.storeSettings();
		}catch(IOException | FacebookException e){
			Alert.showError(e);
		}
		
		///////////////////////////////////
		//Aqui não deve retornar nada mesmo
		return null;
	}

	@Override
	public void setData(){
		//Variáveis auxiliares
		boolean flag = false;
		int param = 0;


		//Quantidade de postagens do Feed
		param = user.getSettings().getNewsFeedSize();
		if(param > 0){
			spinnerFeed.setValue(param);
		}

		//Dias a serem pesquisados (Atividades)
		param = user.getSettings().getActivitiesReportSince();
		if(param > 0){
			spinnerActivity.setValue(param);
		}

		//Dias a serem pesquisados (Agenda)
		param = user.getSettings().getAgendaSince();
		if(param > 0){
			spinnerAgenda.setValue(param);
		}

		//Enviar Resumo como e-mail
		flag = user.getSettings().isSendActivitiesReportEmail();
		chkActivitySendEmail.setSelected(flag);
		txtActivityEmail.setEnabled(flag);
		txtActivityEmail.setText(user.getSettings().getActivitiesReportEmail());

		//Curtir fotos onde é marcado
		flag = user.getSettings().isLikePhotosWhenTagged();
		chkLikePhotos.setSelected(flag);

		//Baixar fotos onde é marcado
		flag = user.getSettings().isDownloadPhotosWhenTagged();
		chkDownloadPhotos.setSelected(flag);
		downloadPath = user.getSettings().getDownloadPhotoFilePath();

		//Curtir status de uma lista de amigos
		flag = user.getSettings().isLikeUsersListStatuses();
		chkLikeUserStatuses.setSelected(flag);
		friendsIds = user.getSettings().getUsersIdsToLikeStatuses();
		btConfigureUsersToLikeStatuses.setEnabled(flag);
	}
	
	@Override
	void setChanged(boolean change){
		btSave.setEnabled(change);
		btCancel.setEnabled(change);
		super.setChanged(change);
	}
}
