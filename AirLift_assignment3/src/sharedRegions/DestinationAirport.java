package sharedRegions;
import java.util.Random;

import common.PilotInterface;
import common.ServiceProvider;
import common.States;
import stubs.RepositoryStub;

/**
 *    Destination Airport.
 *
 *    Implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 */
public class DestinationAirport implements SharedRegion {
	/**
	 * Repository 
	 * @serialField repo
	 */
	private RepositoryStub repo;
	
	/**
     * DestinationAirport instantiation
     * 
     * @param repo Repository
     */
	public DestinationAirport(RepositoryStub repo) {
		this.repo = repo;
	}
	
	/**
	   *  Operation fly to departure point.
	   *
	   *  It is called by a Pilot when he is flying back to the departure point.
	   *  
	   */
	public synchronized void flyToDeparturePoint() {
		PilotInterface pilot = (ServiceProvider) Thread.currentThread();
		pilot.setState(States.FLYING_BACK);
		repo.setPilotState(pilot.getPilotState());
		
		System.out.printf("[PILOT]: Flying to departure point...\n");
		try {
			Thread.sleep((long) (new Random().nextInt(10)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
