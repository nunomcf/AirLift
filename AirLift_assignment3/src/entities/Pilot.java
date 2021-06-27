package entities;
import java.rmi.RemoteException;

import common.States;
import interfaces.DepartureAirportInterface;
import interfaces.DestinationAirportInterface;
import interfaces.PlaneInterface;

/**
 *   Pilot thread.
 *
 *   It simulates the pilot life cycle.
 *   Dynamic solution.
 */
public class Pilot extends Thread implements Entity {
	/**
	 * Pilot's state
	 * @serialField state
	 */
	private States state;
	
	/**
	 * DepartureAirport 
	 * @serialField departure
	 */
	
	private DepartureAirportInterface departure;
	
	/**
	 * DestinationAirport 
	 * @serialField destination
	 */
	private DestinationAirportInterface destination;
	
	/**
	 * Plane 
	 * @serialField plane
	 */
	private PlaneInterface plane;
	
	/**
	 * LastFlight flag
	 * @serialField lastFlight
	 */
	private boolean lastFlight = false;
	
	/**
     * Pilot instantiation
     * 
     * @param dep DepartureAirport
     * @param dest DestinationAirport 
     * @param p Plane
     */
	public Pilot(DepartureAirportInterface dep, DestinationAirportInterface dest, PlaneInterface p) {
		this.state = States.AT_TRANSFER_GATE;
		this.departure = dep;
		this.destination = dest;
		this.plane = p;
	}
	
	/**
     * Pilot's lifecycle
     */
	@Override
	public void run() {
		while(true) {
			try {
				lastFlight = departure.parkAtTransferGate().unwrap();
				setState(departure.informPlaneReadyForBoarding());
				setState(plane.waitForAllInBoard());
				setState(departure.flyToDestinationPoint());
				setState(plane.announceArrival());
				setState(destination.flyToDeparturePoint());
				if(lastFlight) {
					break;
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
     * Returns this pilot's state.
     * @return pilot's current state
     */
	public States getPilotState() {
		return state;
	}
	
	/**
     * Sets the pilot's state.
	 * @param s the state to be set
     */
	public void setState(States s) {
		this.state = s;
	}
}
