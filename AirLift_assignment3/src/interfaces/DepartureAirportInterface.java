package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.States;
import common.StructBool;
import common.StructInt;


public interface DepartureAirportInterface extends Remote {
	
	/**
	   *  Operation travel to Airport.
	   *
	   *  It is called by the Passengers when they travel to the airport in the beginning of the simulation.
	   *  
	   */
	public States travelToAirport(int id) throws RemoteException;
	
	/**
	   *  Operation wait in queue.
	   *
	   *  It is called by the Passengers to go to the queue waiting for boarding in the plane.
	   *  
	   */
	public States waitInQueue(int id) throws RemoteException;
	
	/**
	   *  Operation show documents.
	   *
	   *  It is called by the Passengers to show the documents to the hostess check them.
	   *  
	   */
	public States showDocuments(int id) throws RemoteException;
	
	/**
	   *  Operation park at transfer gate.
	   *
	   *  It is called by the Pilot when he park the plane at the transfer gate.
	   *
	   *  @return true, if is the last flight of the simulation -
	   *          false, otherwise
	   */
	public StructBool parkAtTransferGate() throws RemoteException;
	
	/**
	   *  Operation inform plane ready for boarding.
	   *
	   *  It is called by the Pilot to signal that the plane is ready for boarding.
	   *  
	   */
	public States informPlaneReadyForBoarding() throws RemoteException;
	
	/**
	   *  Operation fly to the destination point.
	   *
	   *  It is called by the Pilot when the plane is flying to the destination airport.
	   *  
	   */
	public States flyToDestinationPoint() throws RemoteException;
	
	/**
	 *  Operation wait for next flight.
	 *
	 *  It is called by the Hostess when she is waiting for the next flight.
	 *
	 *  @return true, if is the last flight of the simulation -
	 *          false, otherwise
	 */
	public StructBool waitForNextFlight() throws RemoteException;
	
	/**
	   *  Operation prepare for pass boarding.
	   *
	   *  It is called by the Hostess when she is ready to receive passengers from the queue in the plane.
	   *  
	   */
	public States prepareForPassBoarding() throws RemoteException;
	
	/**
	 *  Operation check documents.
	 *
	 *  It is called by the Hostess when she checks the documents of the passengers that showed the documents previously.
	 *
	 *  @return currentFlightPassengers in the plane
	 */
	public StructInt checkDocuments() throws RemoteException;
	
	/**
	 *  Operation wait for next passenger.
	 *
	 * It is called by the Hostess to calling the next passenger to enter in the plane.
	 *	  @param  currentPassengers number of passengers that are already in the plane.
	 *	  @param  lastF last Flight Flag
	 *    @return true, if is the last passenger -
	 *            false, otherwise
	 */
	public StructBool waitForNextPassenger(int currentPassengers,boolean lastF) throws RemoteException;
	
	/**
	 * Sets the state of hasTerminated if it's time to stop.
     * @throws java.rmi.RemoteException RemoteException
	 */
	public void terminate() throws RemoteException;

	/**
	 * Gets this shared region's termination status.
	 * @return if this shared region has terminated
     * @throws java.rmi.RemoteException RemoteException
	 */
	public boolean getTerminationState() throws RemoteException;
}
