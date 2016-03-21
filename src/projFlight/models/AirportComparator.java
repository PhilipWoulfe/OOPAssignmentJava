/**
* <h1>Flight Comparator</h1>
* <p>Allows two flights to be compared</p>
*
* @author  Philip Woulfe
* @version 1.0
* @since   2016-03-21 
*/

package projFlight.models;

import java.util.Comparator;

public class AirportComparator implements Comparator<Airport> {
    
	
	@Override
    public int compare(Airport a1, Airport a2) {
        return a1.getName().compareTo(a2.getName());
    }
}
