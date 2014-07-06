package br.com.dsoo.facebook.view.cellrenderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import br.com.dsoo.facebook.view.components.CheckBoxList;

public class CheckBoxListRenderer implements ListCellRenderer<Object>{

	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus){
		CheckBoxList.CheckBoxFriend c = (CheckBoxList.CheckBoxFriend)value;
		
		JCheckBox checkbox = new JCheckBox(c.getFriend().getName());
		checkbox.setSelected(c.isSelected());
		checkbox.setEnabled(list.isEnabled());
		checkbox.setBackground(Color.WHITE);
        checkbox.setFocusPainted(false);
        checkbox.setBorderPainted(true);
        return checkbox;
	}

}
