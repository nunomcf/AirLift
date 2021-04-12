package entities;
import sharedRegions.*;


public class Pilot extends Thread {
	/**
	 * Pilot's state
	 * @serialField state
	 */
	private States state;
	
	private DepartureAirport dep;
	private DestinationAirport dest;
	private Plane p;
	
	public Pilot(DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.AT_TRANSFER_GATE;
		this.dep = dep;
		this.dest = dest;
		this.p = p;
	}
	
	@Override
	public void run() {
		
	}
}
