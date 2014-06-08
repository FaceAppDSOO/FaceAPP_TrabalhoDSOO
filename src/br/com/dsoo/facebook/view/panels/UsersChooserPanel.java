package br.com.dsoo.facebook.view.panels;

import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.com.dsoo.facebook.user.User;
import br.com.dsoo.facebook.view.components.CheckBoxList;
import facebook4j.Friend;
import facebook4j.ResponseList;

public class UsersChooserPanel extends ConfigurePanel{

	private static final long serialVersionUID = 1L;
	private final JScrollPane container;
	
	private CheckBoxList checkList;

	public UsersChooserPanel(User user){
		super(user);
		btSave.addActionListener(this);
		btCancel.addActionListener(this);
		
		ResponseList<Friend> f = user.getFriends();
		
		checkList = new CheckBoxList(f.toArray(new Friend[f.size()]));
		container = new JScrollPane(checkList);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(container, GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btCancel)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(container, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btCancel)
						.addComponent(btSave))
					.addContainerGap())
		);
		setLayout(groupLayout);
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btSave){
			choice = SAVE;
		}else if(e.getSource() == btCancel){
			choice = CANCEL;
		}
		
		closeParent();
	}

	@Override
	public Object getData(){
		if(choice == CANCEL)
			return null;
		
		return checkList.getSelected();
	}

	@Override
	public void setData(){
		// TODO Auto-generated method stub
		
	}
}
