package entities;
import main.AirLift;
import sharedRegions.*;


public class Hostess extends Thread {
	/**
	 * Hostess' state
	 * @serialField state
	 */
	private States state;
	
	private DepartureAirport departure;
	private DestinationAirport destination;
	private Plane plane;
	
	public Hostess(DepartureAirport dep, DestinationAirport dest, Plane p) {
		this.state = States.WAIT_FOR_NEXT_FLIGHT;
		this.departure = dep;
		this.destination = dest;
		this.plane = p;
	}
	
	public void setState(States s) {
		this.state = s;
	}
	
	@Override
	public void run() {
		
		int currentPassengers = 0;
		boolean queueEmpty = false;

		while(true) {
			if(departure.waitForNextFlight()) break;
			departure.prepareForPassBoarding();			
			while(currentPassengers <= AirLift.FLIGHT_MAX_P) {
				currentPassengers = departure.checkDocuments();
				queueEmpty = departure.waitForNextPassenger();
				if(queueEmpty && currentPassengers >= AirLift.FLIGHT_MIN_P) break;
			}
			departure.informPlaneReadyToTakeOff();
		}
	}
	
	
}


