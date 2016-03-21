/**
* <h1>Airport</h1>
* <p>Contains information about an airport</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

package projFlight.models;

public class Airport {
	private String airCode;
	private String name;
	
	
	/**
	 * Gets AirCode
	 * @return Returns Aircode for Airport
	 */
	public String getAirCode() {
		return airCode;
	}
	
	/**
	 * Sets Aircode
	 * @param airCode sets AirCode from input
	 */
	public void setAirCode(String airCode) {
		
		// Check aircode is right length
		if (airCode.length() == 3) {
			this.airCode = airCode;
		}
	}
	
	/**
	 * Gets name of airport
	 * @return Returns name of Airport
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set name of airport
	 * @param name Sets airport name from input
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
	

    @Override
    public boolean equals(Object obj) {
       if (!(obj instanceof Airport))
            return false;
        if (obj == this)
            return true;

        Airport rhs = (Airport) obj;
        return name.equals(rhs.name) && airCode.equals(rhs.airCode);
    }
}
