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
		repo.setPilotState(States.FLYING_BACK);
		
		System.out.printf("[PILOT]: Flying to departure point...\n");
		try {
			Thread.sleep((long) (new Random().nextInt(10)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
