/**
* <h1>Flight</h1>
* <p>Contains informationabout flight</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

package projFlight.models;

public class Flight {
	private int bookingRef;
	private int userID;
	private String deptLeg1Airport;
	private String destLeg1Airport;
	private String leg1SeatType;
	private String deptLeg2Airport;
	private String destLeg2Airport;
	private boolean hasInsurance;
	
	/**
	 * Gte bookign ref
	 * @return get boking ref
	 */
	public int getBookingRef() {
		return bookingRef;
	}
	
	/**
	 * set Booking ref
	 * @param bookingRef Set Booking ref
	 */
	public void setBookingRef(int bookingRef) {
		this.bookingRef = bookingRef;
	}
	
	/**
	 * get user id
	 * @return returns userid
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * set USer ID
	 * @param userID Sets the user ID
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	/**
	 * get Dept 1
	 * @return returns dept 1 airport
	 */
	public String getDeptLeg1Airport() {
		return deptLeg1Airport;
	}
	
	/**
	 * set Dept 1
	 * @param deptLeg1Airport sets Dept 1 airport
	 */
	public void setDeptLeg1Airport(String deptLeg1Airport) {
		this.deptLeg1Airport = deptLeg1Airport;
	}
	
	/**
	 * Get Dest 1 Airport
	 * @return Returns dest 1 Airport
	 */
	public String getDestLeg1Airport() {
		return destLeg1Airport;
	}
	
	/**
	 * Set Dest 1 Airport
	 * @param destLeg1Airport Set Dest 1 Airport
	 */
	public void setDestLeg1Airport(String destLeg1Airport) {
		this.destLeg1Airport = destLeg1Airport;
	}
	
	/**
	 * Get Dept 2 Airport
	 * @return Returns Dept 2 Airport
	 */
	public String getDeptLeg2Airport() {
		String deptLeg2Airport = this.deptLeg2Airport;
		return deptLeg2Airport;
	}
	
	/**
	 * Set Dept 2 Airport
	 * @param deptLeg2Airport Sets Dept 2 Airport
	 */
	public void setDeptLeg2Airport(String deptLeg2Airport) {
		this.deptLeg2Airport = deptLeg2Airport;
	}
	
	/**
	 * Get Dest 2 Airport
	 * @return Returns Dest 2 Airport
	 */
	public String getDestLeg2Airport() {
		String destLeg2Airport = this.destLeg2Airport;
		return destLeg2Airport;
	}
	
	/**
	 * Set Dest 2 Airport
	 * @param destLeg2Airport Sets Dest 2 Airport
	 */
	public void setDestLeg2Airport(String destLeg2Airport) {
		this.destLeg2Airport = destLeg2Airport;
	}
	
	/**
	 * Does FLight have insurance
	 * @return Whether Flight has insurance
	 */
	public boolean isHasInsurance() {
		return hasInsurance;
	}
	
	/**
	 * Set whether flight has insurance
	 * @param hasInsurance Set whether flight has insurance
	 */
	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	
	/**
	 * Set seat type
	 * @return returns seat type
	 */
	public String getLeg1SeatType() {
		return leg1SeatType;
	}
	
	/**
	 * Sets seat type
	 * @param leg1SeatType Set seat type
	 */
	public void setLeg1SeatType(String leg1SeatType) {
		this.leg1SeatType = leg1SeatType;
	}
}
