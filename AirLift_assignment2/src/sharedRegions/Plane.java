package sharedRegions;
import java.util.LinkedList;
import java.util.Queue;

import common.States;
import entities.Hostess;
import entities.Passenger;
import entities.Pilot;

/**
 *    Plane.
 *
 *    It is responsible to keep a continuously updated account of the passengers inside the plane and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 */
public class Plane implements SharedRegion{
	/**
	 * Repository 
	 * @serialField repo
	 */
	private Repository repo;

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
	public Plane(Repository repo) {
		this.repo = repo;
		passengersSeated = new LinkedList<>();
	}
	
	/**
	   *  Operation board the plane.
	   *
	   *  It is called by a Passenger after his documents have been checked by the hostess.
	   */
	public synchronized void boardThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		repo.setPassengerState(p.getPassengerId(), States.IN_FLIGHT, true);
		
		flight_finished = false;
		passengersSeated.add(p.getPassengerId());
		n_passengersBoarded++;
		repo.incPassengersPlane();
		notifyAll();
		System.out.printf("[PASSENGER %d]: Boarding the plane...\n", p.getPassengerId());
	}
	
	/**
	   *  Operation wait for end of flight.
	   *
	   *  It is called by a Passenger, blocking until it is waken up by the pilot when the flight reaches the destination airport.
	   */
	public synchronized void waitForEndOfFlight() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		repo.setPassengerState(p.getPassengerId(), States.IN_FLIGHT, false);
		
		System.out.printf("[PASSENGER %d]: Waiting for end of flight...\n", p.getPassengerId());
		while(!flight_finished) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	   *  Operation leave the plane.
	   *
	   *  It is called by a Passenger.
	   */
	public synchronized void leaveThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.AT_DESTINATION);
		repo.setPassengerState(p.getPassengerId(), States.AT_DESTINATION, true);
		passengersSeated.remove(p.getPassengerId());
		
		repo.decPassengersPlane();
		repo.incTotalNumberPassengersTransported();
		
		if(passengersSeated.size() == 0) {
			allPassengersLeft = true;
			notifyAll();
		}		
		System.out.printf("[PASSENGER %d]: Leaving the plane...\n", p.getPassengerId());
	}
	
	/**
	   *  Operation inform plane ready to takeoff.
	   *
	   *  It is called by the Hostess. This operation waits until all passengers of a certain flight have boarded the plane.
	   *  As soon as that happens, wakes up the pilot, allowing him to take off.
	   *  @param n_passengers number of passengers waiting for boarding
	   */
	public synchronized void informPlaneReadyToTakeOff(int n_passengers) {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.READY_TO_FLY);
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
	}
	
	/**
	   *  Operation wait for all in board.
	   *
	   *  It is called by the Pilot. This operation waits until the hostess informs him that all passengers have boarded the plane.
	   */
	public synchronized void waitForAllInBoard() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.WAITING_FOR_BOARDING);
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
	}

	/**
	   *  Operation announce arrival.
	   *
	   *  It is called by Pilot. Notifies the passengers that the flight has reached the destination airport and waits until all passengers left the plane.
	   */
	public synchronized void announceArrival() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.DEBOARDING);
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
	}
}
