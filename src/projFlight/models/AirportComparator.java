package projFlight.models;

import java.util.Comparator;

public class AirportComparator implements Comparator<Airport> {
    @Override
    public int compare(Airport a1, Airport a2) {
        return a1.getName().compareTo(a2.getName());
    }
}
