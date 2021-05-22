package stubs;

import common.ClientComm;
import common.Message;
import common.MessageType;
import common.Parameters;
import entities.Passenger;

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
    
    /**
	   *  Operation board the plane.
	   *
	   *  It is called by a Passenger after his documents have been checked by the hostess.
	   */
    public void boardThePlane() {
    	Passenger p = (Passenger) Thread.currentThread();
    	
    	Message nm = new Message();
		nm.setMessageType(MessageType.BOARDTHEPLANE);
		nm.setEntityState(p.getPassengerState());
		nm.setEntityId(p.getPassengerId());

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation wait for end of flight.
	   *
	   *  It is called by a Passenger, blocking until it is waken up by the pilot when the flight reaches the destination airport.
	   */
    public void waitForEndOfFlight() {
    	Passenger p = (Passenger) Thread.currentThread();
    	
    	Message nm = new Message();
		nm.setMessageType(MessageType.WAITFORENDOFFLIGHT);
		nm.setEntityId(p.getPassengerId());

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation leave the plane.
	   *
	   *  It is called by a Passenger.
	   */
    public void leaveThePlane() {
    	Passenger p = (Passenger) Thread.currentThread();
    	
    	Message nm = new Message();
		nm.setMessageType(MessageType.LEAVETHEPLANE);
		nm.setEntityId(p.getPassengerId());

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation inform plane ready to takeoff.
	   *
	   *  It is called by the Hostess. This operation waits until all passengers of a certain flight have boarded the plane.
	   *  As soon as that happens, wakes up the pilot, allowing him to take off.
	   *  @param n_passengers number of passengers waiting for boarding
	   */
    public void informPlaneReadyToTakeOff(int n_passengers) {
    	Message nm = new Message();
		nm.setMessageType(MessageType.INFORMPLANEREADYTOTAKEOFF);
		nm.setIntVal1(n_passengers);
		
		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation wait for all in board.
	   *
	   *  It is called by the Pilot. This operation waits until the hostess informs him that all passengers have boarded the plane.
	   */
    public void waitForAllInBoard() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.WAITFORALLINBOARD);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	   *  Operation announce arrival.
	   *
	   *  It is called by Pilot. Notifies the passengers that the flight has reached the destination airport and waits until all passengers left the plane.
	   */
    public void announceArrival() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.ANNOUNCEARRIVAL);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
}
