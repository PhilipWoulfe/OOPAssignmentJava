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
	
	public int getBookingRef() {
		return bookingRef;
	}
	public void setBookingRef(int bookingRef) {
		this.bookingRef = bookingRef;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getDeptLeg1Airport() {
		return deptLeg1Airport;
	}
	public void setDeptLeg1Airport(String deptLeg1Airport) {
		this.deptLeg1Airport = deptLeg1Airport;
	}
	public String getDestLeg1Airport() {
		return destLeg1Airport;
	}
	public void setDestLeg1Airport(String destLeg1Airport) {
		this.destLeg1Airport = destLeg1Airport;
	}
	public String getDeptLeg2Airport() {
		String deptLeg2Airport = this.deptLeg2Airport;
		return deptLeg2Airport;
	}
	public void setDeptLeg2Airport(String deptLeg2Airport) {
		this.deptLeg2Airport = deptLeg2Airport;
	}
	public String getDestLeg2Airport() {
		String destLeg2Airport = this.destLeg2Airport;
		return destLeg2Airport;
	}
	public void setDestLeg2Airport(String destLeg2Airport) {
		this.destLeg2Airport = destLeg2Airport;
	}
	public boolean isHasInsurance() {
		return hasInsurance;
	}
	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	public String getLeg1SeatType() {
		return leg1SeatType;
	}
	public void setLeg1SeatType(String leg1SeatType) {
		this.leg1SeatType = leg1SeatType;
	}
}
