package stubs;

import common.ClientComm;
import common.Message;
import common.MessageType;
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
    
    /**
	   *  Operation fly to departure point.
	   *
	   *  It is called by a Pilot when he is flying back to the departure point.
	   *  
	   */
    public void flyToDeparturePoint() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.FLYTODEPARTUREPOINT);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }

}
