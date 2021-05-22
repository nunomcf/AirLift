package stubs;

import common.ClientComm;
import common.Message;
import common.MessageType;
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
    
    /**
	   *  Operation travel to Airport.
	   *
	   *  It is called by the Passengers when they travel to the airport in the beginning of the simulation.
	   *  
	   */
    public void travelToAirport() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.TRAVELTOAIRPORT);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation wait in queue.
	   *
	   *  It is called by the Passengers to go to the queue waiting for boarding in the plane.
	   *  
	   */
    public void waitInQueue() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.WAITINQUEUE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation show documents.
	   *
	   *  It is called by the Passengers to show the documents to the hostess check them.
	   *  
	   */
    public void showDocuments() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.SHOWDOCUMENTS);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation park at transfer gate.
	   *
	   *  It is called by the Pilot when he park the plane at the transfer gate.
	   *
	   *  @return true, if is the last flight of the simulation -
	   *          false, otherwise
	   */
    public boolean parkAtTransferGate() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.PARKATTRANSFERGATE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);		
		nm = (Message) cc.readObject();

		return nm.getBoolVal1();
    }
    
    /**
	   *  Operation inform plane ready for boarding.
	   *
	   *  It is called by the Pilot to signal that the plane is ready for boarding.
	   *  
	   */
    public void informPlaneReadyForBoarding() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.INFORMPLANEREADYFORBOARDING);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation fly to the destination point.
	   *
	   *  It is called by the Pilot when the plane is flying to the destination airport.
	   *  
	   */
    public void flyToDestinationPoint() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.FLYTODESTINATIONPOINT);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	 *  Operation wait for next flight.
	 *
	 *  It is called by the Hostess when she is waiting for the next flight.
	 *
	 *  @return true, if is the last flight of the simulation -
	 *          false, otherwise
	 */
    public boolean waitForNextFlight() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.WAITFORNEXTFLIGHT);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);		
		nm = (Message) cc.readObject();

		return nm.getBoolVal1();
    }
    
    /**
	   *  Operation prepare for pass boarding.
	   *
	   *  It is called by the Hostess when she is ready to receive passengers from the queue in the plane.
	   *  
	   */
    public void prepareForPassBoarding() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.PREPAREFORPASSBOARDING);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	 *  Operation check documents.
	 *
	 *  It is called by the Hostess when she checks the documents of the passengers that showed the documents previously.
	 *
	 *  @return currentFlightPassengers in the plane
	 */
    public int checkDocuments() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.CHECKDOCUMENTS);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);		
		nm = (Message) cc.readObject();

		return nm.getIntVal1();
    }
    
    /**
	 *  Operation wait for next passenger.
	 *
	 * It is called by the Hostess to calling the next passenger to enter in the plane.
	 *	  @param  currentPassengers number of passengers that are already in the plane.
	 *	  @param  lastF last Flight Flag
	 *    @return true, if is the last passenger -
	 *            false, otherwise
	 */
    public boolean waitForNextPassenger(int currentPassengers, boolean lastF) {
    	Message nm = new Message();
		nm.setMessageType(MessageType.WAITFORNEXTPASSENGER);
		nm.setIntVal1(currentPassengers);
		nm.setBoolVal1(lastF);
		
		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);		
		nm = (Message) cc.readObject();

		return nm.getBoolVal1();
    }

}
