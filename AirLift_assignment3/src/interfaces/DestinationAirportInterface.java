package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Random;

import common.States;

public interface DestinationAirportInterface extends Remote {
	
	/**
	   *  Operation fly to departure point.
	   *
	   *  It is called by a Pilot when he is flying back to the departure point.
	   *  
	   */
	public void flyToDeparturePoint() ;
	
	/**
	 * Sets the state of hasTerminated if it's time to stop.
     * @throws RemoteException RemoteException
	 */
	public void terminate() throws RemoteException;

	/**
	 * Gets this shared region's termination status.
	 * @return if this shared region has terminated
     * @throws RemoteException RemoteException
	 */
	public boolean getTerminationState() throws RemoteException;

}
