package entities;
import common.States;
import sharedRegions.*;
import stubs.DepartureAirportStub;
import stubs.DestinationAirportStub;
import stubs.PlaneStub;

/**
 *   Pilot thread.
 *
 *   It simulates the pilot life cycle.
 *   Dynamic solution.
 */
public class Pilot extends Thread {
	/**
	 * Pilot's state
	 * @serialField state
	 */
	private States state;
	
	/**
	 * DepartureAirport 
	 * @serialField departure
	 */
	
	private DepartureAirportStub departure;
	
	/**
	 * DestinationAirport 
	 * @serialField destination
	 */
	private DestinationAirportStub destination;
	
	/**
	 * Plane 
	 * @serialField plane
	 */
	private PlaneStub plane;
	
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
	public Pilot(DepartureAirportStub dep, DestinationAirportStub dest, PlaneStub p) {
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
			lastFlight = departure.parkAtTransferGate();
			departure.informPlaneReadyForBoarding();
			plane.waitForAllInBoard();
			departure.flyToDestinationPoint();
			plane.announceArrival();
			destination.flyToDeparturePoint();
			if(lastFlight) {
				break;
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
