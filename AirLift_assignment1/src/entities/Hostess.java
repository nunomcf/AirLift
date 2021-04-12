package entities;
import sharedRegions.*;


public class Hostess extends Thread {
	/**
	 * Hostess' state
	 * @serialField state
	 */
	private States state;
	
	private DepartureAirport dep;
	private DestinationAirport dest;
	private Plane p;
	
	public Hostess(DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.WAIT_FOR_NEXT_FLIGHT;
		this.dep = dep;
		this.dest = dest;
		this.p = p;
	}
	
	@Override
	public void run() {
		
	}
	
	
}


