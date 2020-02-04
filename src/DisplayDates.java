import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class DisplayDates extends JDialog {
	private JPanel panel;
	private ArrayList<String> dates;
	private JLabel dText,dateOutput;

	public DisplayDates(JFrame paOccupy, SiteModel model) {

		super(paOccupy, "List", true);
		
		Date sDate = 
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));

		dText = new JLabel("The dates that are taken for this campsite are:");

		//StringBuilder sb = new StringBuilder();


		for(int i=0; i< arrayList.size(); i++) {
				System.out.println(arrayList.get(i));
			}
		


		panel.add(dText);
		//panel.add(dateOutput);

		getContentPane().add(panel);
		pack();	
	}
}
