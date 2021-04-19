package entities;
import sharedRegions.*;
import java.util.*;

public class Passenger extends Thread {
	/**
	 * Passenger's state
	 * @serialField state
	 */
	private States state;
	
	private int id;
	private DepartureAirport departure;
	private DestinationAirport destination;
	private Plane plane;
	
	public Passenger(int id, DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.GOING_TO_AIRPORT;
		this.id = id;
		this.departure = dep;
		this.destination = dest;
		this.plane = p;
	}
	
	public void setState(States s) {
		this.state = s;
	}
	
	public int getPassengerId() {
		return this.id;
	}
	
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
}
