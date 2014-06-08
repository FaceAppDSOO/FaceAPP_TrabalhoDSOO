package br.com.dsoo.facebook.view.cellrenderers;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import br.com.dsoo.facebook.view.adapters.CheckBoxFriend;

public class CheckBoxListRenderer implements ListCellRenderer<Object>{

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus){
		CheckBoxFriend c = (CheckBoxFriend)value;
		
		JCheckBox checkbox = new JCheckBox(c.getFriend().getName());
		checkbox.setSelected(c.isSelected());
		checkbox.setEnabled(list.isEnabled());
        checkbox.setFocusPainted(false);
        checkbox.setBorderPainted(true);
        return checkbox;
	}

}
