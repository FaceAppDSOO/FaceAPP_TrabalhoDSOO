package br.com.dsoo.facebook.view.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import br.com.dsoo.facebook.view.adapters.CheckBoxFriend;
import br.com.dsoo.facebook.view.cellrenderers.CheckBoxListRenderer;
import facebook4j.Friend;

public class CheckBoxList extends JList<CheckBoxFriend> implements MouseListener{

	private static final long serialVersionUID = 1L;
	
	public CheckBoxList(Friend[] friends){
		CheckBoxFriend[] check = new CheckBoxFriend[friends.length];
		
		for(int i = 0; i < friends.length; i++){
			check[i] = new CheckBoxFriend(friends[i]);
		}
		
		setListData(check);
		setCellRenderer(new CheckBoxListRenderer());
		
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		addMouseListener(this);
	}
	
	public Friend[] getSelected(){
		ArrayList<Friend> selectedFriends = new ArrayList<>();
		
		for(int i = 0; i < getModel().getSize(); i++){
			Object item = getModel().getElementAt(i);
			
			if(((CheckBoxFriend)item).isSelected()){
				selectedFriends.add(((CheckBoxFriend)item).getFriend());
			}
		}

		return selectedFriends.toArray(new Friend[selectedFriends.size()]);
	}

	@Override
	public void mouseClicked(MouseEvent e){
		int index = locationToIndex(e.getPoint());

		if(index != -1){
			CheckBoxFriend c = (CheckBoxFriend)getModel().getElementAt(index);
			c.setSelected(!c.isSelected());
			repaint();
		}
	}

	@Override public void mousePressed(MouseEvent e){}
	@Override public void mouseReleased(MouseEvent e){}
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
}
