package sharedRegions;

import java.util.*;

import common.Parameters;
import common.States;
import entities.*;

/**
 *    Departure Airport.
 *
 *    It is responsible to keep a continuously updated account of the passengers inside the departure airport
 *    and is implemented as an implicit monitor.
 *    All public methods are executed in mutual exclusion.
 */
public class DepartureAirport implements SharedRegion {
	/**
	 * Repository 
	 * @serialField repo
	 */
	private Repository repo;
	
	/**
	 * Queue where where passengers arrive at random times to check in for the transfer flight
	 * 
	 * @serialField passengersQueue
	 */
	private Queue<Integer> passengersQueue;

	/**
	 * Boolean array where the Hostess to ask the documents of the passengers.
	 * @serialField documents
	 */
	private boolean[] documents;

	/**
	 * Boolean array where the Passengers show that they have handed their documents to the Hostess.
	 * @serialField handedDocs
	 */
	private boolean[] handedDocs;

	/**
	 * Boolean array where the Hostess gives permission to the passengers to board the plane.
	 * @serialField canBoardPlane
	 */
	private boolean[] canBoardPlane;
	
	/**
	 * Variable to store the number of passengers that have already showed the documents to the Hostess. 
	 * It is reseted before the hostess starts the boarding procedure.
	 * @serialField currentFlightPassengers
	 */
	private int currentFlightPassengers = 0;
	
	/**
	 * Last Flight flag, that is set to true when the next flight is the last one.
	 * @serialField lastFlight
	 */
	private boolean lastFlight = false;
	
	/**
	 * Plane Ready for Boarding flag, that is used to signal when the plane is ready for boarding.
	 * @serialField planeReady
	 */
	private boolean planeReady = false;
	
	/**
	 * Temporary variable used to store the id of the passenger that is currently showing his documents to the Hostess.
	 * @serialField askDocument_id
	 */
	private int askDocument_id = -1;
	
	/**
     * DepartureAirport instantiation
     * 
     * @param repo Repository
     */
	public DepartureAirport(Repository repo) {
		this.repo = repo;
		passengersQueue = new LinkedList<>();
		documents = new boolean[Parameters.N_PASSENGERS];
		canBoardPlane = new boolean[Parameters.N_PASSENGERS];
		handedDocs = new boolean[Parameters.N_PASSENGERS];
		Arrays.fill(documents, false);
		Arrays.fill(canBoardPlane, false);
		Arrays.fill(handedDocs, false);
	}
	
	/**
	   *  Operation travel to Airport.
	   *
	   *  It is called by the Passengers when they travel to the airport in the beginning of the simulation.
	   *  
	   */
	public synchronized void travelToAirport() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.GOING_TO_AIRPORT);
		repo.setPassengerState(p.getPassengerId(), States.GOING_TO_AIRPORT, true);
		
		System.out.printf("[PASSENGER %d]: Going to airport...\n", p.getPassengerId());
		try {
			Thread.sleep((long) (new Random().nextInt(10)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	   *  Operation wait in queue.
	   *
	   *  It is called by the Passengers to go to the queue waiting for boarding in the plane.
	   *  
	   */
	public synchronized void waitInQueue() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		repo.setPassengerState(p.getPassengerId(), States.IN_QUEUE, true);
		
		passengersQueue.add(p.getPassengerId());
		repo.inQueue();
		notifyAll();
		System.out.printf("[PASSENGER %d]: Waiting in queue...\n", p.getPassengerId());
		while(!documents[p.getPassengerId()]) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	/**
	   *  Operation show documents.
	   *
	   *  It is called by the Passengers to show the documents to the hostess check them.
	   *  
	   */
	public synchronized void showDocuments() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		repo.setPassengerState(p.getPassengerId(), States.IN_QUEUE, false);
		
		handedDocs[p.getPassengerId()] = true;
		notifyAll();
		while(!canBoardPlane[p.getPassengerId()]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("[PASSENGER %d]: Showed his documents.\n", p.getPassengerId());
	}
	
	/**
	   *  Operation park at transfer gate.
	   *
	   *  It is called by the Pilot when he park the plane at the transfer gate.
	   *
	   *  @return true, if is the last flight of the simulation -
	   *          false, otherwise
	   */
	public synchronized boolean parkAtTransferGate() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.AT_TRANSFER_GATE);
		repo.setPilotState(States.AT_TRANSFER_GATE);
		
		if(Parameters.N_PASSENGERS - repo.getTotalNumberPassengersTransported() <= Parameters.FLIGHT_MAX_P) {
			lastFlight = true;
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = TRUE).\n");
		}
		else{
			lastFlight = false;
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = FALSE).\n");
			
		}
		return lastFlight;
	}
	
	/**
	   *  Operation inform plane ready for boarding.
	   *
	   *  It is called by the Pilot to signal that the plane is ready for boarding.
	   *  
	   */
	public synchronized void informPlaneReadyForBoarding() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.READY_FOR_BOARDING);
		repo.setPilotState(States.READY_FOR_BOARDING);
		
		System.out.printf("[PILOT]: Inform plane ready for boarding.\n");
		planeReady = true;
		notifyAll();
	}
	
	/**
	   *  Operation fly to the destination point.
	   *
	   *  It is called by the Pilot when the plane is flying to the destination airport.
	   *  
	   */
	public synchronized void flyToDestinationPoint() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.FLYING_FORWARD);
		repo.setPilotState(States.FLYING_FORWARD);
		
		System.out.printf("[PILOT]: Flying forward...\n");
		try {
			Thread.sleep((long) (new Random().nextInt(15)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  Operation wait for next flight.
	 *
	 *  It is called by the Hostess when she is waiting for the next flight.
	 *
	 *  @return true, if is the last flight of the simulation -
	 *          false, otherwise
	 */
	public synchronized boolean waitForNextFlight() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_NEXT_FLIGHT);
		repo.setHostessState(States.WAIT_FOR_NEXT_FLIGHT);
		
		System.out.printf("[HOSTESS]: Waiting for next flight...\n");
		while(!planeReady) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		planeReady = false;
		return lastFlight;
	}
	
	/**
	   *  Operation prepare for pass boarding.
	   *
	   *  It is called by the Hostess when she is ready to receive passengers from the queue in the plane.
	   *  
	   */
	public synchronized void prepareForPassBoarding() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		repo.setHostessState(States.WAIT_FOR_PASSENGER);
		
		System.out.printf("[HOSTESS]: Prepare for pass boarding...\n");
		currentFlightPassengers = 0; 
		askDocument_id = -1;
		
		while(passengersQueue.size() == 0) { // waits for the first passenger
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *  Operation check documents.
	 *
	 *  It is called by the Hostess when she checks the documents of the passengers that showed the documents previously.
	 *
	 *  @return currentFlightPassengers in the plane
	 */
	public synchronized int checkDocuments() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.CHECK_PASSENGER);
		repo.setHostessState(States.CHECK_PASSENGER);
		
		askDocument_id = passengersQueue.remove(); // get the passenger at the head of the queue
		repo.outQueue();
		
		System.out.printf("[HOSTESS]: Ask documents of passenger %d...\n", askDocument_id);
		documents[askDocument_id] = true;
		notifyAll();

		while(!handedDocs[askDocument_id]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("[HOSTESS]: Passenger %d handed this documents...\n", askDocument_id);
		currentFlightPassengers += 1;
		return currentFlightPassengers;
	}
	
	/**
	 *  Operation wait for next passenger.
	 *
	 * It is called by the Hostess to calling the next passenger to enter in the plane.
	 *	  @param  currentPassengers number of passengers that are already in the plane.
	 *	  @param  lastF last Flight Flag
	 *    @return true, if is the last passenger -
	 *            false, otherwise
	 */
	public synchronized boolean waitForNextPassenger(int currentPassengers,boolean lastF) {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		repo.setHostessState(States.WAIT_FOR_PASSENGER);
		
		System.out.printf("[HOSTESS]: Waiting for next passenger...\n");
		canBoardPlane[askDocument_id] = true;
		askDocument_id = -1;
		notifyAll();
		int numberPassengersLeft = Parameters.N_PASSENGERS - repo.getTotalNumberPassengersTransported();
		
		if(lastF) {
			if(currentPassengers == numberPassengersLeft) {
				// if it is the last flight and there are no more passengers, can take off
				System.out.printf("[HOSTESS]: LAST FLIGHT, can take off with %d passengers\n\n", currentPassengers);
				return true;
			}
			else {
				// otherwise, wait for another passenger
				while(passengersQueue.size() == 0) { // waits for the next passenger
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.printf("[HOSTESS]: LAST FLIGHT, wait for more passengers\n\n", currentPassengers);
				return false;
			}
		} 
		else {
			if(currentPassengers >= Parameters.FLIGHT_MIN_P) {
				if(passengersQueue.size() == 0) {
					return true;
				} 
				else {
					if(currentPassengers == Parameters.FLIGHT_MAX_P) {
						return true;
					}
					return false;
				}
			}
			else {
				while(passengersQueue.size() == 0) { // waits for the next passenger
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return false;
			}
		}
	}
}
