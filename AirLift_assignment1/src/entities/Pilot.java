package entities;
import sharedRegions.*;
import main.*;


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
	
	private DepartureAirport departure;
	
	/**
	 * DestinationAirport 
	 * @serialField destination
	 */
	private DestinationAirport destination;
	
	/**
	 * Plane 
	 * @serialField plane
	 */
	private Plane plane;
	
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
	public Pilot(DepartureAirport dep, DestinationAirport dest, Plane p) {
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
				System.out.println("last flight break");
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
