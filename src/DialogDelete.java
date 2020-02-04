import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class DialogDelete extends JDialog implements ActionListener {

	private JLabel row;
	private JTextField rowSelect;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel;
	private SiteModel site1;


	public DialogDelete(JFrame paOccupy,SiteModel site) {

		super(paOccupy, "Delete", true);
		site1 = site;


		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));	

		row = new JLabel("Input Row Number To Delete (Starts at index 1): ");
		rowSelect = new JTextField(5);
		panel.add(row);
		panel.add(rowSelect);

		okButton = new JButton("Ok");
		cancelButton = new JButton("Cancel");

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		panel.add(okButton);
		panel.add(cancelButton);

		getContentPane().add(panel);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(okButton == e.getSource()){

			try {
				int index = Integer.parseInt(rowSelect.getText());
				if(index <= 0 || index >= site1.getSize()) {
					throw new IllegalArgumentException();
				}
				site1.removeSite(site1.getSite(index));
				int checkIn = site1.getSite(index).dateToDay();
				int checkOut = site1.getSite(index).dateToDay()+site1.getSite(index).getDaysStaying()-1;
				int siteNum = site1.getSite(index).getSiteNumber();
				if(!site1.checkAvail(checkIn,checkOut,siteNum)) {
					site1.changeAvail(checkIn,checkOut,siteNum,false);
				}
				dispose();
			}
			catch(IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this,"You entered a row that is out of range, please try again.");
			}

		}

		if(cancelButton == e.getSource()) {
			dispose();
		}
	}
}
