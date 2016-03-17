package projFlight.models;

public class Flight {
	private int bookingRef;
	private int userID;
	private String deptLeg1Aircode;
	private String destLeg1AirCode;
	private String deptLeg2AirCode;
	private String destLeg2AirCode;
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
	public String getDeptLeg1Aircode() {
		return deptLeg1Aircode;
	}
	public void setDeptLeg1Aircode(String deptLeg1Aircode) {
		this.deptLeg1Aircode = deptLeg1Aircode;
	}
	public String getDestLeg1AirCode() {
		return destLeg1AirCode;
	}
	public void setDestLeg1AirCode(String destLeg1AirCode) {
		this.destLeg1AirCode = destLeg1AirCode;
	}
	public String getDeptLeg2AirCode() {
		return deptLeg2AirCode;
	}
	public void setDeptLeg2AirCode(String deptLeg2AirCode) {
		this.deptLeg2AirCode = deptLeg2AirCode;
	}
	public String getDestLeg2AirCode() {
		return destLeg2AirCode;
	}
	public void setDestLeg2AirCode(String destLeg2AirCode) {
		this.destLeg2AirCode = destLeg2AirCode;
	}
	public boolean isHasInsurance() {
		return hasInsurance;
	}
	public void setHasInsurance(boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	
	
}
