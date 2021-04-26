package stubs;

import common.Parameters;

public class DepartureAirportStub {
	
	/**
	 * Destination Airport server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Destination Airport server port
	 * @serialField serverPort
	 */
	private int serverPort;

    /**
     * Destination Airport Stub instantiation
     */
    public DepartureAirportStub() {
			serverHostName = Parameters.departureAirportHostName;
			serverPort = Parameters.departureAirportPort;
    }

}
