import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class SiteModel extends AbstractTableModel {	
	/** ArrayList of the reserved Sites*/
	private ArrayList<Site> listSites;

	private ArrayList<String> undoList;


	private ArrayList<boolean[]> siteDates = new ArrayList<boolean[]>();
	/** Array of the column headers*/
	private String[] columnNames = { "Name Reserving", "Checked in", "Days Staying", "Site #", "Tent/RV info"};

	/******************************************************************
	 * 
	 * Default constructor for SiteModel that makes an empty table
	 * 
	 *****************************************************************/
	public SiteModel() {
		listSites = new ArrayList<Site>();
		listSites.add(null);
		undoList = new ArrayList<String>();
	}

	public ArrayList<Site> getSites() {
		return listSites;
	}

	/******************************************************************
	 * 
	 * A method that adds a Site to SiteModel
	 * 
	 * @param site represents the Site being added
	 * 
	 * @throws IllegalArgumentException if the site number/date is 
	 * taken
	 * 
	 *****************************************************************/
	public void addSite(Site site) {
		//System.out.println("Date of reserved: "+site.dateToDay());
		//System.out.println("Size of array: " + siteDates.size());
		int checkIn = site.dateToDay();
		int checkOut = site.dateToDay()+site.getDaysStaying()-1;
		int siteNum = site.getSiteNumber();
		if(checkAvail(checkIn,checkOut,siteNum)) {
			changeAvail(checkIn,checkOut,siteNum,true);
		}
		else
			throw new IllegalArgumentException();

		listSites.add(site);
	}


	public Site getSite(int index) {
		return listSites.get(index);
	}
	/******************************************************************
	 * 
	 * A method that gets the value of the selected element on the table
	 * 
	 * @param row represents the row selected
	 * @param col represents the col selected
	 * 
	 * @return an Object that represents the selected element
	 * 
	 *****************************************************************/
	public Object getValueAt(int row, int col) {
		if(row == 0 && col == 0)
			return "[Name Reserving]";
		else if(row == 0 && col == 1)
			return "[Checked in]";
		else if(row == 0 && col == 2)
			return "[Days Staying]";
		else if(row == 0 && col == 3)
			return "[Site #]";
		else if(row == 0 && col == 4)
			return "[Tent/RV info]";
		else {
			switch(col) {
			case 0: 
				return listSites.get(row).getNameReserving();				
			case 1: 
				return listSites.get(row).dateToString();				
			case 2: 
				return listSites.get(row).getDaysStaying();
			case 3:
				return listSites.get(row).getSiteNumber();
			case 4:
				if(listSites.get(row) instanceof RV)
					return (((RV)listSites.get(row)).getPower()+" Amps");
				if(listSites.get(row) instanceof Tent)
					return (((Tent)listSites.get(row)).getNumOfTenters()+ " Tenters");
			}
		}
		return "";
	}

	/******************************************************************
	 * 
	 * A method that gets the column header
	 * 
	 * @param col represents the col selected
	 * 
	 * @return a String represents the selected header
	 * 
	 *****************************************************************/
	public String getColumnName(int col) {
		return columnNames[col];
	}

	/******************************************************************
	 * 
	 * A method that gets the size of the ArrayList listSites
	 * 
	 * @return an Integer of the size of listSites
	 * 
	 *****************************************************************/
	public int getSize() {
		return listSites.size();
	}

	/******************************************************************
	 * 
	 * A method that gets the amount of columns
	 * 
	 * @return an Integer of the amount of columns
	 * 
	 *****************************************************************/
	public int getColumnCount() {
		return columnNames.length;
	}

	/******************************************************************
	 * 
	 * A method that gets the amount of rows
	 * 
	 * @return an Integer of the amount of rows
	 * 
	 *****************************************************************/
	public int getRowCount() {
		return listSites.size();
	}

	/******************************************************************
	 * A method that is used to check if a site is open or not
	 * 
	 * @param site
	 * @return
	 */
	public boolean checkAvail(int checkIn, int checkOut, int siteNum) {
		if(checkOut >= (siteDates.size())) {
			for (int i = siteDates.size(); i <= checkOut; i++) {
				siteDates.add(i, new boolean[5]);
			}
		}
		boolean bool = true;
		for (int i = checkIn; i <= checkOut; i++) {
			if (siteDates.get(i)[siteNum-1]) 
				bool = false;
		}

		return bool;
	}

	public void changeAvail(int checkIn, int checkOut, int siteNum, boolean bool) {
		for (int i = checkIn; i <= checkOut; i++) {
			boolean[] temp = siteDates.get(i);
			temp[siteNum-1] = bool;
			siteDates.set(i, temp);
		} 
	}

	public void deleteDate(int pos){
		boolean[]temp = siteDates.get(pos);
		temp[pos] = false;  
		siteDates.set(pos, temp);
	}

	/******************************************************************
	 * 
	 * A method that returns the checkout date
	 * 
	 * @param site
	 * @return
	 */
	public GregorianCalendar checkOut(Site site) {
		GregorianCalendar outDate = new GregorianCalendar();
		outDate = site.getCheckIn();
		outDate.add(Calendar.DAY_OF_MONTH, site.getDaysStaying());
		return outDate;
	}

	public void reAddSite(int index) {
		int checkIn = listSites.get(index).dateToDay();
		int checkOut = listSites.get(index).dateToDay()+listSites.get(index).getDaysStaying()-1;
		int siteNum = listSites.get(index).getSiteNumber();
		if(checkAvail(checkIn,checkOut,siteNum)) {
			changeAvail(checkIn,checkOut,siteNum,true);
		}
		listSites.get(index).setNameReserving(listSites.get(index).getNameReserving().substring(2));
		undoList.remove(undoList.size()-1);
	}
	
	public void addIndexUndo(Site site) {
		undoList.add("D" + listSites.indexOf(site));
	}

	public String getUndoRecent() {
		return undoList.get(undoList.size()-1);
	}
	/*
	 * This method removes the site by adding a negative 1 to
	 * the site and then removes the dates from the Gregorian
	 * Calender.
	 * @param site of the site that is being removed from the list
	 */
	public void removeSite(Site site) {
		site.setNameReserving("-1" + site.getNameReserving());
		undoList.add("I" + listSites.indexOf(site));
		//Still needs to remove it from the gregorian calendar!
	}
	/**
	 * This method removes a data type forever when the it's undo'd 
	 * from existance.
	 * @param index is a char that will be converted to a integer
	 * that will find the certain index location
	 */
	public void removeSiteOffUndo(int index) {
		listSites.remove(index);
		undoList.remove(undoList.size()-1);
	}

	/******************************************************************
	 * 
	 * A method that saves a database to a file;
	 * Serialized format
	 * 
	 * @param fileName represents the location and name of the file
	 * @return void	
	 * 
	 *****************************************************************/
	public void saveAsSerialized(String fileName) {
		try {
			FileOutputStream file = new FileOutputStream(fileName + ".ser");
			ObjectOutputStream stream = new ObjectOutputStream(file);
			stream.writeObject(listSites);
			stream.close();	
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


	/******************************************************************
	 * 
	 * A method that loads the database from a specific file;
	 * Serialized format
	 *  
	 * @param fileName represents the name of the file used
	 * @return void	 
	 * 
	 *****************************************************************/
	public void loadAsSerialized(String fileName) throws Exception {
		try {
			if(fileName.substring(fileName.length()-4,fileName.length()).equals(".ser")) {
				FileInputStream file = new FileInputStream (fileName);
				ObjectInputStream stream = new ObjectInputStream(file);
				listSites = (ArrayList<Site>)stream.readObject();
				stream.close();
			}
			else {
				JOptionPane.showMessageDialog(null, "Please Select a .ser File");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Date> getBtwnD(GregorianCalendar cIn, GregorianCalendar cOut)
	{
	    ArrayList<Date> dates = new ArrayList<Date>();
	    Calendar calendar = new GregorianCalendar();
	    SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
	    try {	
	    	Date sDate = form.parse(cIn.toString());	
			Date eDate = form.parse(cOut.toString());
			calendar.setTime(sDate);
			
			while (calendar.getTime().before(eDate))
		    {
		        Date result = calendar.getTime();
		        dates.add(result);
		        calendar.add(Calendar.DATE, 1);
		    }
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
	}
	    return dates;
}
//	    public ArrayList<String> getOccupiedSpaces() {
//	        ArrayList<String> temp = new ArrayList<String>();
//	        for(int i = 0; i < siteDates.size(); i++) {
//	            for(int j = 0; j < 5; j++) {
//	                if(siteDates.get(i)[j]) {
//	                	Site s = new Site();
//                        String sDate = (""+s.dayToDate(i).getTime());
//                        temp.add(sDate + " Occupied at site" + j);
//	                  // System.out.print(temp.get(i));
//	                }
//	            }
//	        }
//	        return temp;
//	    }

	/******************************************************************
	 * 
	 * A method that saves a database to a file; Text format
	 * 
	 * @param fileName represents the location and name of the file
	 * @return void    
	 * 
	 *****************************************************************/
	public void saveAsText(String fileName){    
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName + ".txt")));
			out.println(listSites.size()-1);
			GregorianCalendar savDate = new GregorianCalendar();
			SimpleDateFormat dateF;
			dateF = new SimpleDateFormat("MM/dd/yyyy");
			for (int i = 1; i < listSites.size(); i++) {

				// listSites is an ArrayList<Site>
				Site SiteUnit = listSites.get(i);  
				savDate = SiteUnit.getCheckIn();
				String sDate = dateF.format(savDate.getTime());
				out.print(SiteUnit.getNameReserving()+":");    
				out.print(sDate+":");
				out.print(SiteUnit.getDaysStaying()+":");
				out.print(SiteUnit.getSiteNumber()+":");

				if (SiteUnit instanceof Tent)
					out.println(((Tent)SiteUnit).getNumOfTenters() + ":t");
				if (SiteUnit instanceof RV)
					out.println(((RV)SiteUnit).getPower() + ":r");
			}
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/******************************************************************
	 * 
	 * A method that loads the database from a specific file;
	 * Text format
	 *  
	 * @param fileName represents the name of the file used
	 * @return void     
	 * @throws IOException 
	 * @throws IOException 
	 * 
	 *****************************************************************/
	public void loadAsText(String fileName) throws Exception{
		listSites = new ArrayList<Site>();
		listSites.add(null);
		Scanner fileReader = new Scanner(new File(fileName)); 
		int siteAmount = fileReader.nextInt();
		String record;
		Site site;
		String[] cols;
		GregorianCalendar checkD = new GregorianCalendar();
		fileReader.nextLine();
		SimpleDateFormat dateF;    
		ArrayList<boolean[]> siteDates = new ArrayList<boolean[]>();

		for (int i = 0; i < siteAmount; i++) {
			site = new Site();
			dateF = new SimpleDateFormat();
			checkD = new GregorianCalendar();
			record = fileReader.nextLine();
			cols = record.split(":");
			if (cols.length>6)
				throw new IllegalArgumentException();            

			dateF = new SimpleDateFormat("MM/dd/yyyy");    
			checkD.setTime(dateF.parse(cols[1]));
			if(cols[5].equals("t")) 
				site = new Tent(cols[0],checkD,Integer.parseInt(cols[2]), Integer.parseInt(cols[3]),Integer.parseInt(cols[4]));
			else if(cols[5].equals("r"))
				site = new RV(cols[0], checkD,Integer.parseInt(cols[2]), Integer.parseInt(cols[3]),Integer.parseInt(cols[4]));
			else 
				throw new IllegalArgumentException();
			if(site.dateToDay()+site.getDaysStaying()-1 >= (siteDates.size())) {
				for (int j = siteDates.size(); j <= site.dateToDay()+site.getDaysStaying()-1; j++) {
					siteDates.add(i, new boolean[5]);
				}
			}
			this.fireTableRowsDeleted(i+1,i+1);
			addSite(site);
		}
		this.fireTableRowsInserted(0,listSites.size()-1);
	}


}
// add methods to add, delete, and update.
// add methods to load/save accounts from/to a binary file
// add other methods as needed

