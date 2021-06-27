package sharedRegions;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Queue;
import common.States;
import interfaces.PlaneInterface;
import interfaces.RepositoryInterface;

/**
 *    Plane.
 *
 *    It is responsible to keep a continuously updated account of the passengers inside the plane and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 */
public class Plane implements PlaneInterface, SharedRegion{
	/**
	 * Repository 
	 * @serialField repo
	 */
	private RepositoryInterface repo;

	/**
	 * Flight finished flag
	 * @serialField flight_finished
	 */
	private boolean flight_finished = false;

	/**
	 * Flag to signal that all passengers have left the plane.
	 * @serialField allPassengersLeft
	 */
	private boolean allPassengersLeft = false;

	/**
	 * Queue to store the passengers inside the plane for each flight
	 * @serialField passengersSeated
	 */
	private Queue<Integer> passengersSeated;

	/**
	 * Flag to signal that all passengers have boarded the plane.
	 * @serialField boardingCompleted
	 */
	private boolean boardingCompleted = false;

	/**
	 * Variable that stores the number of passengers that have boarded the plane.
	 * @serialField n_passengersBoarded
	 */
	private int n_passengersBoarded = 0;
	
	/**
     * Plane instantiation
     * 
     * @param repo Repository
     */
	public Plane(RepositoryInterface repo) {
		this.repo = repo;
		passengersSeated = new LinkedList<>();
	}
	
	/**
	   *  Operation board the plane.
	   *
	   *  It is called by a Passenger after his documents have been checked by the hostess.
	 * @throws RemoteException 
	   */
	public synchronized States boardThePlane(int id) throws RemoteException {
		repo.setPassengerState(id, States.IN_FLIGHT, true);
		
		flight_finished = false;
		passengersSeated.add(id);
		n_passengersBoarded++;
		repo.incPassengersPlane();
		notifyAll();
		System.out.printf("[PASSENGER %d]: Boarding the plane...\n", id);
		return States.IN_FLIGHT;
	}
	
	/**
	   *  Operation wait for end of flight.
	   *
	   *  It is called by a Passenger, blocking until it is waken up by the pilot when the flight reaches the destination airport.
	 * @throws RemoteException 
	   */
	public synchronized States waitForEndOfFlight(int id) throws RemoteException {
		repo.setPassengerState(id, States.IN_FLIGHT, false);
		
		System.out.printf("[PASSENGER %d]: Waiting for end of flight...\n", id);
		while(!flight_finished) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		return States.IN_FLIGHT;
	}
	
	/**
	   *  Operation leave the plane.
	   *
	   *  It is called by a Passenger.
	 * @throws RemoteException 
	   */
	public synchronized States leaveThePlane(int id) throws RemoteException {
		repo.setPassengerState(id, States.AT_DESTINATION, true);
		passengersSeated.remove(id);
		
		repo.decPassengersPlane();
		repo.incTotalNumberPassengersTransported();
		
		if(passengersSeated.size() == 0) {
			allPassengersLeft = true;
			notifyAll();
		}		
		System.out.printf("[PASSENGER %d]: Leaving the plane...\n", id);
		return States.AT_DESTINATION;
	}
	
	/**
	   *  Operation inform plane ready to takeoff.
	   *
	   *  It is called by the Hostess. This operation waits until all passengers of a certain flight have boarded the plane.
	   *  As soon as that happens, wakes up the pilot, allowing him to take off.
	   *  @param n_passengers number of passengers waiting for boarding
	 * @throws RemoteException 
	   */
	public synchronized States informPlaneReadyToTakeOff(int n_passengers) throws RemoteException {
		repo.setHostessState(States.READY_TO_FLY);

		while(n_passengers != n_passengersBoarded) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		n_passengersBoarded = 0;
		boardingCompleted = true;
		System.out.printf("[HOSTESS]: Inform plane ready to takeoff...\n");
		notifyAll();
		return States.READY_TO_FLY;
	}
	
	/**
	   *  Operation wait for all in board.
	   *
	   *  It is called by the Pilot. This operation waits until the hostess informs him that all passengers have boarded the plane.
	 * @throws RemoteException 
	   */
	public synchronized States waitForAllInBoard() throws RemoteException {
		repo.setPilotState(States.WAITING_FOR_BOARDING);
		
		System.out.printf("[PILOT]: Waiting for boarding...\n");
		while(!boardingCompleted) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		boardingCompleted = false; // reset condition variable
		return States.WAITING_FOR_BOARDING;
	}

	/**
	   *  Operation announce arrival.
	   *
	   *  It is called by Pilot. Notifies the passengers that the flight has reached the destination airport and waits until all passengers left the plane.
	 * @throws RemoteException 
	   */
	public synchronized States announceArrival() throws RemoteException {
		repo.setPilotState(States.DEBOARDING);
		
		System.out.printf("[PILOT]: Announce arrival.\n");
		
		flight_finished = true;
		notifyAll();
		while(!allPassengersLeft) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		allPassengersLeft = false;
		System.out.printf("[PILOT]: All passengers have left the plane.\n");
		return States.DEBOARDING;
	}

	@Override
	public void terminate() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getTerminationState() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
