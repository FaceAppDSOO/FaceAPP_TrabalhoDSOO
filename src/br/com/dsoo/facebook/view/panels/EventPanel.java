package br.com.dsoo.facebook.view.panels;

import java.awt.Color;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import br.com.dsoo.facebook.logic.Utils;
import facebook4j.Event;

public class EventPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private JTextPane txtName, txtLocation, txtTime;

	public EventPanel(Event event){
		super();
		setBackground(Color.WHITE);
		
		String location = event.getLocation();
		
		txtLocation = new JTextPane();
		txtLocation.setText(location != null ? "Local: " + event.getLocation() : "Local não especificado!");
		txtLocation.setEditable(false);
		
		txtName = new JTextPane();
		txtName.setFont(new Font("Tahoma", Font.BOLD, 11));
		txtName.setText(event.getName());
		txtName.setEditable(false);
		
		txtTime = new JTextPane();
		txtTime.setText("Data/Hora: " + Utils.dateToString(event.getStartTime()));
		txtTime.setEditable(false);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
							.addGap(59))
						.addComponent(txtLocation, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
						.addComponent(txtTime, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(txtLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
