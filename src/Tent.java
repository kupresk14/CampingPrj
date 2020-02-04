import java.util.GregorianCalendar;

public class Tent extends Site{
	
	/** Represents the number of tenters on the site */
	private int numOfTenters;

	/***
     * This is the constructor for Tent that extends to site and also a
     * instantiates the number of tenters
     * @param numOfTenters this is an integer that has the num of tenters
     **/
    public Tent() {
        super();
        this.numOfTenters = 0;
    }
	/***********************************************************************
	 * This is the constructor for Tent that extends to site and also 
	 * instantiates the number of tenters
	 * 
	 * @param days this is an integer of the amount of days they are staying
	 * @param nameReserving is a string of the registering name	 * 
	 * @param date is the check in date 
	 * @param sitNum this is an int of the site num they staying at
	 * @param numOfTenters this is an integer that has the num of tenters
	 **********************************************************************/
	public Tent(String nameReserving, GregorianCalendar date, int days, int siteNum, int numOfTenters) {
		
		super(nameReserving, date, days, siteNum);
		this.numOfTenters = numOfTenters;
		
	}
	/***********************************************************************
	 * sets the number of tenters
	 * @param numOfTenters represents an int of the number of tenters
	 */
	public void setNumOfTenters(int numOfTenters) {
		this.numOfTenters = numOfTenters;
	}
	/***********************************************************************
	 * gets the number of tenters
	 * @return the number of tenters as an integer
	 */
	public int getNumOfTenters() {
		return numOfTenters;
	}
	/***********************************************************************
	 * This method calculates the amount of money owed for a certain
	 * reservation
	 * 
	 * @return The cost of renting that reservation
	 */
	public double getCost() {
		return this.getDaysStaying()*this.getNumOfTenters()*3;
	}
}

