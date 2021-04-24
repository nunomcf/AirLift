package entities;
import sharedRegions.*;
import java.util.*;

public class Passenger extends Thread {
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
	
	public Passenger(int id, DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.GOING_TO_AIRPORT;
		this.id = id;
		this.departure = dep;
		this.destination = dest;
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
		departure.travelToAirport();
		departure.waitInQueue();
		departure.showDocuments();
		plane.boardThePlane();
		plane.waitForEndOfFlight();
		plane.leaveThePlane();
	}
	
	/*
	public void travelToAirport() {
		this.state = States.GOING_TO_AIRPORT;
		try {
			sleep((long) (new Random().nextInt(5)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	*/
	
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
