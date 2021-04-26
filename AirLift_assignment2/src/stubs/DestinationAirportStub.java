package stubs;

import common.Parameters;

public class DestinationAirportStub {
	
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
    public DestinationAirportStub() {
			serverHostName = Parameters.destinationAirportHostName;
			serverPort = Parameters.destinationAirportPort;
    }

}
