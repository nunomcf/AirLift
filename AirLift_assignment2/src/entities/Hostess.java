package entities;
import common.Parameters;
import common.States;
import sharedRegions.*;

/**
 *   Hostess thread.
 *
 *   It simulates the hostess life cycle.
 *   Dynamic solution.
 */
public class Hostess extends Thread {
	/**
	 * Hostess' state
	 * @serialField state
	 */
	private States state;
	
	/**
	 * DepartureAirport 
	 * @serialField departure
	 */
	
	private DepartureAirport departure;
	
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
     * Hostess instantiation
     * 
     * @param dep DepartureAirport
     * @param p Plane
     */
	public Hostess(DepartureAirport dep, Plane p) {
		this.state = States.WAIT_FOR_NEXT_FLIGHT;
		this.departure = dep;
		this.plane = p;
	}
	
	/**
     * Hostess's lifecycle
     */
	@Override
	public void run() {
		int currentNumberPassengers;
		int totalNumberPassengersTransported = 0;
		boolean canTakeOff = false;
		while(true) {
			currentNumberPassengers = 0;
			if(totalNumberPassengersTransported == Parameters.N_PASSENGERS) break; // no more passengers to transport, end simulation
			lastFlight = departure.waitForNextFlight();
			departure.prepareForPassBoarding();	
			while(currentNumberPassengers < Parameters.FLIGHT_MAX_P) {
				currentNumberPassengers = departure.checkDocuments();
				canTakeOff = departure.waitForNextPassenger(currentNumberPassengers,lastFlight);
				if(canTakeOff) {
					System.out.printf("\n[CAN TAKE OFF] -> %d\n\n", currentNumberPassengers);
					break;
				}
			}
			totalNumberPassengersTransported += currentNumberPassengers;
			plane.informPlaneReadyToTakeOff(currentNumberPassengers);
		}
	}
	
	/**
     * Returns the hostess' state.
     * @return hostess's current state
     */
	public States getHostessState() {
		return state;
	}
	
	/**
     * Sets the hostess' state.
	 * @param s the state to be set
     */
	public void setState(States s) {
		this.state = s;
	}
}


