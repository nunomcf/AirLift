package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.States;

public interface PlaneInterface extends Remote {
	/**
	   *  Operation board the plane.
	   *
	   *  It is called by a Passenger after his documents have been checked by the hostess.
	   */
	public States boardThePlane(int id) throws RemoteException;
	
	/**
	   *  Operation wait for end of flight.
	   *
	   *  It is called by a Passenger, blocking until it is waken up by the pilot when the flight reaches the destination airport.
	   */
	public States waitForEndOfFlight(int id) throws RemoteException;
	
	/**
	   *  Operation leave the plane.
	   *
	   *  It is called by a Passenger.
	   */
	public States leaveThePlane(int id) throws RemoteException;
	
	/**
	   *  Operation inform plane ready to takeoff.
	   *
	   *  It is called by the Hostess. This operation waits until all passengers of a certain flight have boarded the plane.
	   *  As soon as that happens, wakes up the pilot, allowing him to take off.
	   *  @param n_passengers number of passengers waiting for boarding
	   */
	public States informPlaneReadyToTakeOff(int n_passengers) throws RemoteException;
	
	/**
	   *  Operation wait for all in board.
	   *
	   *  It is called by the Pilot. This operation waits until the hostess informs him that all passengers have boarded the plane.
	   */
	public States waitForAllInBoard() throws RemoteException;

	/**
	   *  Operation announce arrival.
	   *
	   *  It is called by Pilot. Notifies the passengers that the flight has reached the destination airport and waits until all passengers left the plane.
	   */
	public States announceArrival() throws RemoteException;
	
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
