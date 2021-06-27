package sharedRegions;
import java.rmi.RemoteException;
import java.util.Random;

import common.States;
import interfaces.DestinationAirportInterface;
import interfaces.RepositoryInterface;

/**
 *    Destination Airport.
 *
 *    Implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 */
public class DestinationAirport implements DestinationAirportInterface, SharedRegion {
	
	private boolean hasTerminated;
	/**
	 * Repository 
	 * @serialField repo
	 */
	private RepositoryInterface repo;
	
	/**
     * DestinationAirport instantiation
     * 
     * @param repo Repository
     */
	public DestinationAirport(RepositoryInterface repo) {
		this.repo = repo;
	}
	
	/**
	   *  Operation fly to departure point.
	   *
	   *  It is called by a Pilot when he is flying back to the departure point.
	 * @throws RemoteException 
	   *  
	   */
	public synchronized States flyToDeparturePoint() throws RemoteException {
		repo.setPilotState(States.FLYING_BACK);
		
		System.out.printf("[PILOT]: Flying to departure point...\n");
		try {
			Thread.sleep((long) (new Random().nextInt(10)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return States.FLYING_BACK;
	}

	@Override
	public void terminate() throws RemoteException {
		hasTerminated = true;
		notifyAll();
		
	}

	@Override
	public boolean getTerminationState() throws RemoteException {
		return hasTerminated;
	}
}
