package stubs;

import common.Parameters;

public class PlaneStub {
	/**
	 * Repository server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Repository server port
	 * @serialField serverPort
	 */
	private int serverPort;

    /**
     * Repository stub instantiation
     */
    public PlaneStub() {
			serverHostName = Parameters.planeHostName;
			serverPort = Parameters.planePort;
    }
}
