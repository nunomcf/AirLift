package sharedRegions;

import java.util.Random;

import entities.Passenger;
import entities.Pilot;
import entities.States;

public class DestinationAirport {
	
	private Repository repo;
	
	public DestinationAirport(Repository repo) {
		this.repo = repo;
	}
	
	//pilot
	public synchronized void flyToDeparturePoint() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.FLYING_BACK);
		try {
			Thread.sleep((long) (new Random().nextInt(5)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
