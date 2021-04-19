package entities;
import sharedRegions.*;
import main.*;


public class Pilot extends Thread {
	/**
	 * Pilot's state
	 * @serialField state
	 */
	private States state;
	
	private DepartureAirport departure;
	private DestinationAirport destination;
	private Plane plane;
	
	public Pilot(DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.AT_TRANSFER_GATE;
		this.departure = dep;
		this.destination = dest;
		this.plane = p;
	}
	
	@Override
	public void run() {
		while(true) {
			if(departure.parkAtTransferGate()) break;
			departure.informPlaneReadyForBoarding();
			departure.waitForAllInBoard();
			departure.flyToDestinationPoint();
			plane.announceArrival();
			destination.flyToDeparturePoint();
		}
	}
	
	public void setState(States s) {
		this.state = state;
	}
}
