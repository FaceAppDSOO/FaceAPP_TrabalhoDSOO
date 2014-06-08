package br.com.dsoo.facebook.view.adapters;

import facebook4j.Friend;

public class CheckBoxFriend{
	private final Friend friend;
	private boolean selected;

	public CheckBoxFriend(Friend friend){
		this.friend = friend;
		selected = false;
	}

	public boolean isSelected(){
		return selected;
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}

	public Friend getFriend(){
		return friend;
	}
}

