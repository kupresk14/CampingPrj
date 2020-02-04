import java.awt.*;
import java.text.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
/************************************************************************
 * @description These dialog boxes are invoked with the user selects 
 * Check-in. The only difference between the two dialog boxes is: for 
 * Reserving a Tent site: ask the number of tenters; for RVs, ask the 
 * power needed.
 *
 * @author Kyler Kupres
 * @version 2.0  
 * 
 *************************************************************************/
public class DialogCheckInRv extends JDialog implements ActionListener {
	/**JLabels that are used for name, power, sitenum, date, daysstaying*/
	private JLabel nameLabel,powLabel,requestLabel,occuLabel,daysLabel;
	/**JTextField used to store name of user*/
	private JTextField nameTxt;
	/**JTextField used to store the date occupied*/
	private JTextField OccupiedOnTxt;
	/**JTextField used to store the days staying*/
	private JTextField stayingTxt;
	/**JTextField used to store the site number*/
	private JTextField siteNumberTxt;
	/**Used to select from 30,40,or 50 watts*/
	private JComboBox powerNum;
	/**An okay button to enter data into program*/
	private JButton okButton;
	/**A cancel button to cancel this panel*/
	private JButton cancelButton;
	/**The JPanel used for DialogCheckInRV*/
	private JPanel panel;
	/**Used to determine if data is sent and GUI close*/
	private boolean closeStatus;
	/**A new RV object called unit*/
	private RV unit;  	
	/**************************************************************
	 * A method that is used to set up the panel for the Dialog
	 * CheckInRV. Adds all of the JLabels and also adds JText
	 * Fields/combo-boxes for everything needed.
	 *
	 * @param paOccupy - A JFrame passed from GUICampingReg
	 * @param d - A new RV object
	 **************************************************************/
	public DialogCheckInRv(JFrame paOccupy, RV d) {
		/*Insures that a user cannot open more than one CheckIn*/
		super(paOccupy,"Reserve an RV site", true);
		/*Sets closeStatus to false*/
		closeStatus = false;
		/*Sets the RV object to d*/
		unit = d;
		/*Instantiate panel as a new panel*/
		panel = new JPanel();
		/*Set layout of panel to GridLayout*/
		panel.setLayout(new GridLayout(6,2));

		/**Used to store the Name of reserver*/
		nameLabel = new JLabel ("Name of Reserver:");
		/**USed to store the requested site number*/
		requestLabel = new JLabel ("Requested site number:");
		/**Used to store the check-in date*/
		occuLabel = new JLabel ("Occupied on Date:");
		/**Used to store the amount of days staying*/
		daysLabel = new JLabel ("Days planning on staying:");
		/**Used to store the power used for RV*/
		powLabel = new JLabel ("Type of power in Amps");

		/*Code below instantiates all of the text fields*/
		nameTxt = new JTextField(5);
		siteNumberTxt = new JTextField(3);
		OccupiedOnTxt = new JTextField(10);
		stayingTxt = new JTextField(6);

		/*okButton is turned into a JButton called "Ok"*/
		okButton = new JButton("Ok");
		/*cancelButton is turned into a JButton called "Cancel*/
		cancelButton = new JButton("Cancel");

		/*Adds actionListeners to each of the buttons*/
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		/**Array that stores the numbers available for Amp. power*/
		Integer[] ampNumber = {30,40,50};
		/*Adds the numbers for Amperage to the combo-box*/
		powerNum = new JComboBox(ampNumber);
		/*Set the color of the combo-box to cyan*/
		powerNum.setBackground(Color.cyan);

		/*Add each component to the JPanel and packs it*/
		panel.add(nameLabel);
		panel.add(nameTxt);
		panel.add(requestLabel);
		panel.add(siteNumberTxt);
		panel.add(occuLabel);
		panel.add(OccupiedOnTxt);
		panel.add(daysLabel);
		panel.add(stayingTxt);
		panel.add(powLabel);
		panel.add(powerNum);
		panel.add(okButton);
		panel.add(cancelButton);
		getContentPane().add(panel);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		if(okButton == e.getSource()) {
			/*Sets the closeStatus to true*/
			closeStatus = true;
			/*Set up a simpleDateFormat for the dates inputed*/
			SimpleDateFormat dateF;
			dateF = new SimpleDateFormat("MM/dd/yyyy");
			/**Used to store the date user enters*/
			Date inDate;

			/*****************************************************************************
			 * Tries to add all the information that is inputed in the pane to the 
			 * program and checks if inputs are out of range, less than excepted values,
			 * ect. 
			 * 
			 * @throws Exception e - For most exceptions
			 * @throws IllegalArgumentException ex - 
			 * 
			 ****************************************************************************/
			try {
				GregorianCalendar curDate = new GregorianCalendar(2018,0,1);
				String name = nameTxt.getText();
				int siteNum = Integer.parseInt(siteNumberTxt.getText());
				inDate = dateF.parse(OccupiedOnTxt.getText());

				/*Checks if the input is 00/00/YYYY and throws an exception.*/				
				if(inDate.toString().substring(3,4)=="00" 
						&& inDate.toString().substring(0, 1) == "00" 
						&& Integer.parseInt(inDate.toString().substring(6,9)) >= 0000){
					throw new Exception();
				}		

				/* Checks if the date input is greater than 10 characters or less that 10
				 * characters. It has to be 10.*/ 
				if(OccupiedOnTxt.getText().length() < 10 ||
						OccupiedOnTxt.getText().length() > 10)
					throw new Exception();
				
				/*Parses days staying from string to integer*/
				int daysStay = Integer.parseInt(stayingTxt.getText());	
				/*Parses the amps selected into an integer*/
				Integer powerA = (Integer) powerNum.getSelectedItem();
				/**Used to store a GregorianCalender from the string user entered for date*/		
				GregorianCalendar cal = new GregorianCalendar();
				cal.setLenient(false);
				cal.setTime(inDate);
				cal.getTime();

				/*Checks if the date user set is less than the starting date of 01/01/2018*/
				if(cal.compareTo(curDate) < 0)	{
					throw new Exception("The date has to start at 01/01/2018 or above.");
				}
				/*Checks the length of the name, siteNum, and days Staying*/
				if(name.length() == 0 || siteNum > 5 || siteNum <= 0 || daysStay <= 0){
					throw new Exception();
				}
				/*Checks the length of siteNum, and days Staying*/
				if(siteNum <= 0 || daysStay <= 0 || daysStay > 364) {
					throw new IllegalArgumentException();
				}
				/*Sends name,siteNum,cal,daysStay,and powerA to the Site class*/
				unit.setNameReserving(name);
				unit.setSiteNumber(siteNum);
				unit.setCheckIn(cal); 
				unit.setDaysStaying(daysStay);
				unit.setPower(powerA);				
				/*Closes this JPanel*/
				dispose();
			}
			catch(IllegalArgumentException ex){
				JOptionPane.showMessageDialog(panel, "A negative number, no info, or a zero was" +
				" entered, please try again. Number of sites is 1-5\n - Date must be after 01/01/2018 \n" +
				"- Maximum of 20 people may stay \n - Max amount of days is 364 (We need a day to clean).");
			}
			catch(Exception e1)	{
				JOptionPane.showMessageDialog(panel, "An error occured / invalid"+
				" input entered, please try"+
				" again (Format of date is MM/DD/YYYY).\n" +"Ex: 00/00/0000"+
				" is invalid, days over the days in the month" +
				" will not work.\n"+"Must be within the vaild amount of days in months 1-12.");
			}				
		}

		if(cancelButton == e.getSource()) {
			/*Sets closeStatus to false and disposes this JPanel*/
			closeStatus = false;
			dispose();
		}
	}

	/*****************************************************************************
	 * Method that is used to check the close-status of the program and will
	 * return that status when called upon.
	 * 
	 * @return closeStatus - boolean that checks if Panel can close or not
	 ****************************************************************************/
	public boolean closeChk() {
		return closeStatus;
	}
}


