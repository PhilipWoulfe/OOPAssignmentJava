package projFlight.models;

public class Airport {
	private String airCode;
	private String name;
	
	public String getAirCode() {
		return airCode;
	}
	public void setAirCode(String airCode) {
		if (airCode.length() == 3) {
			this.airCode = airCode;
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
