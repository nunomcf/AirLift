package entities;
import java.rmi.RemoteException;

import common.States;
import interfaces.DepartureAirportInterface;
import interfaces.PlaneInterface;

/**
 *   Passenger thread.
 *
 *   It simulates the passenger life cycle.
 *   Dynamic solution.
 */

public class Passenger extends Thread implements Entity {
	/**
	 * Passenger's state
	 * @serialField state
	 */
	private States state;
	
	
	/**
	 * Passenger's identification
	 * @serialField id
	 */
	private int id;
	
	
	/**
	 * DepartureAirport 
	 * @serialField departure
	 */
	
	private DepartureAirportInterface departure;
	
	/**
	 * Plane 
	 * @serialField plane
	 */
	private PlaneInterface plane;
	
	
	/**
     * Passenger instantiation
     * 
     * @param id int
     * @param dep DepartureAirport
     * @param p Plane
     */
	public Passenger(int id, DepartureAirportInterface dep, PlaneInterface p) {
		this.state = States.GOING_TO_AIRPORT;
		this.id = id;
		this.departure = dep;
		this.plane = p;
	}
	
	/**
	 * Returns this passenger's id.
	 * @return passenger's id
	 */
	public int getPassengerId() {
		return this.id;
	}
	
	/**
	 * Passenger's lifecycle
	 */
	@Override
	public void run() {
		try {
			setState(departure.travelToAirport(id));
			setState(departure.waitInQueue(id));
			setState(departure.showDocuments(id));
			setState(plane.boardThePlane(id));
			setState(plane.waitForEndOfFlight(id));
			setState(plane.leaveThePlane(id));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
     * Returns this passenger's state.
     * @return passenger's current state
     */
	public States getPassengerState() {
		return state;
	}
	
	/**
     * Sets the passenger's state.
	 * @param s the state to be set
     */
	public void setState(States s) {
		this.state = s;
	}
}
