import java.util.GregorianCalendar;

public class RV extends Site{

	/** Represents the power supplied to the site */
	private int power; // 30, 40, 50 amps of service.
	
	/******************************************************************
     * This is the default constructor for Tent that extends to site 
     * and also instantiates the number of tenters
     * @param power this is an integer that has the num of power being used
     * @param days this is an integer of the amount of days they are staying
     * @param sitNum this is an int of the site num they staying at
     * @param nameReserving is a string of the registering name
     * @param date is the check in date 
     *****************************************************************/
    public RV() {
        super();
        this.power = 0;
    }
	
	/***********************************************************************
	 * This is the constructor for Tent that extends to site and also 
	 * instantiates the number of tenters
	 * @param power this is an integer that has the num of power being used
	 * @param days this is an integer of the amount of days they are staying
	 * @param sitNum this is an int of the site num they staying at
	 * @param nameReserving is a string of the registering name
	 * @param date is the check in date 
	 **********************************************************************/
	public RV(String name, GregorianCalendar date, int days, int siteNum,int power) {
		super(name, date, days, siteNum);
		this.power = power;
	}

	/***********************************************************************
	 * This method retrieves the amount of power being used
	 * @return the amount of power as an int
	 */
	public int getPower() {
		return power;
	}
	/***********************************************************************
	 * this method sets the amount of power being used
	 * @param power sets the amount of power as an int
	 */
	public void setPower(int power) {
		this.power = power;
	}

	/***********************************************************************
	 * This method calculates the amount of money owed for a certain
	 * reservation
	 * 
	 * @return The cost of renting that reservation
	 */
	public double getCost() {
		return this.getDaysStaying()*this.getPower();
	}
}
