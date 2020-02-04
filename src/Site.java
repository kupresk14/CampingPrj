import java.io.*;
import java.util.*;

public class Site implements Serializable {
	private static final long serialVersionUID = 1L;
	/** The name of the person who is occupying the Site */
	protected String nameReserving;
	/** The date the Site was checked-in (occupied) */
	protected GregorianCalendar checkIn;
	/** The estimated number of days the person is reserving */
	/** This is just an estimate when the camper is */
	/** is checking in */
	protected int daysStaying;
	/** The Site number */
	protected int siteNumber;
	
	public Site() {
        nameReserving = "";
        daysStaying = 0;
        siteNumber = 0;
    }
	
	/******************************************************************
	 * 
	 *****************************************************************/
	public Site(String name, GregorianCalendar date, int days, int siteNum) {
		checkIn = date;
		nameReserving = name;
		daysStaying = days;
		siteNumber = siteNum;
	}
	/******************************************************************
	 * a Method that retrieves the name of the person making the 
	 * reservation
	 * @return the name reservation
	 */
	public String getNameReserving() {
		return nameReserving;
	}
	/******************************************************************
	 * a Method that sets the name of the person making the reservation
	 * 
	 ******************************************************************/
	public void setNameReserving(String nameReserving) {
		this.nameReserving = nameReserving;
	}
	
	/******************************************************************	
	 * a Method that retrieves the starting date
	 * @return the check in dates
	 ******************************************************************/
	public GregorianCalendar getCheckIn() {
		return checkIn;
	}
	
	/******************************************************************	
	 * a Method that sets the starting Calendar dates
	 * 
	 ******************************************************************/
	public void setCheckIn(GregorianCalendar checkIn) {
		this.checkIn = checkIn;
	}
	/***********************************************************************	
	 * a Method that retrieves the amount of days someone is staying
	 * @return the days you are staying
	 */
	public int getDaysStaying() {
		return daysStaying;
	}
	/***********************************************************************	
	 * a Method that sets the amount of days they wish to stay
	 */
	public void setDaysStaying(int daysStaying) {
		this.daysStaying = daysStaying;
	}
	/***********************************************************************	
	 * a Method that retrieves the site number
	 * @return the site number they are staying at
	 */
	public int getSiteNumber() {
		return siteNumber;
	}
	/***********************************************************************	
	 * a Method that sets the site number
	 */
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	/***********************************************************************	
	 * a Method that converts a Gregorian Calendar date to an Integer
	 * @param day is an integer of what "day" or array location we should
	 * place the information.
	 * @return the day of the year that Calendar represents
	 */
	public int dateToDay() {
		
		int day=0;

		GregorianCalendar start = new GregorianCalendar(2018,0,1);
		
		day = (int)((checkIn.getTimeInMillis()-start.getTimeInMillis())/(1000*60*60*24));

		return day;
	}
	/***********************************************************************	
	 * a Method that converts a Long to a Gregorian Calendar
	 * @param day is a Gregorian Calendar of what "day" or array location 
	 * we should place the information.
	 * @param start is a Gregorian Calendar that holds the first date
	 * a user can rent on
	 * @return the day of the year that Calendar represents
	 */
	public GregorianCalendar dayToDate(long cal) {
		
		GregorianCalendar day = new GregorianCalendar();
		GregorianCalendar start = new GregorianCalendar(2018,0,1);
		
		day.setTimeInMillis((cal+start.getTimeInMillis())*1000*60*60*24);
				
		return day;
	}
	
	public String dateToString() {
        String[] s = new String[3];
        s[0] = (""+checkIn.get(GregorianCalendar.MONTH));
        s[1] = (""+checkIn.get(GregorianCalendar.DAY_OF_MONTH));
        s[2] = (""+checkIn.get(GregorianCalendar.YEAR));
        switch(s[0]) {
        case "0": s[0] = "January";
                break;
        case "1": s[0] = "February";
                break;
        case "2": s[0] = "March";
                break;
        case "3": s[0] = "April";
                break;
        case "4": s[0] = "May";
                break;
        case "5": s[0] = "June";
                break;
        case "6": s[0] = "July";
                break;
        case "7": s[0] = "August";
                break;
        case "8": s[0] = "September";
                 break;
        case "9": s[0] = "October";
                 break;
        case "10": s[0] = "November";
                 break;
        case "11": s[0] = "December";
                break;
    }
        return (s[0] + " " + s[1] + ", " + s[2]);
    }
	
	public double getCost() {
		return 0;
	}
}

