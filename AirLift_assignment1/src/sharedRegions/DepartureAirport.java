package sharedRegions;

import java.util.*;
import entities.*;
import main.AirLift;

public class DepartureAirport {
	
	private Repository repo;
	
	private Queue<Integer> passengersQueue;
	private boolean[] documents;
	private boolean[] handedDocs;
	private boolean[] canBoardPlane;
	
	// variable to store the number of passengers transported
	private int numberPassengersTransported = 0;
	
	private int currentFlightPassengers = 0;
	// variable to terminate hostess life cycle (set by the pilot after returning from the last flight)
	private boolean lastFlight = false;
	
	private boolean planeReady = false;
	
	private boolean boardingCompleted = false;
	
	private boolean showedDocuments=false;
	
	private int countTestShowDocuments=0;
	
	
	private int askDocument_id = -1;
	private int checkDocument_id = -1;
	private int canBoard = -1;
	
	
	public DepartureAirport(Repository repo) {
		this.repo = repo;
		passengersQueue = new LinkedList<>();
		documents = new boolean[AirLift.N_PASSENGERS];
		canBoardPlane = new boolean[AirLift.N_PASSENGERS];
		handedDocs = new boolean[AirLift.N_PASSENGERS];
		Arrays.fill(documents, false);
		Arrays.fill(canBoardPlane, false);
		Arrays.fill(handedDocs, false);
	}
	
	public int getPassengersTransported() {
		return this.numberPassengersTransported;
	}
	
	public boolean getLastFlight() {
		return this.lastFlight;
	}
	
	// passenger
	public synchronized void travelToAirport() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.GOING_TO_AIRPORT);
		System.out.printf("[PASSENGER %d]: Going to airport...\n", p.getPassengerId());
		try {
			Thread.sleep((long) (new Random().nextInt(10)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// passenger
	public synchronized void waitInQueue() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		passengersQueue.add(p.getPassengerId());
		System.out.printf("queue size ---> %d...\n", passengersQueue.size());
		notifyAll();
		System.out.printf("[PASSENGER %d]: Waiting in queue...\n", p.getPassengerId());
		while(!documents[p.getPassengerId()]) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
	}
	
	// passenger
	public synchronized void showDocuments() {
		showedDocuments=false;
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		handedDocs[p.getPassengerId()] = true;
		notifyAll();
		
		while(!canBoardPlane[p.getPassengerId()]) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("showCanBoardPlane: "+canBoardPlane[p.getPassengerId()]);
		System.out.printf("[PASSENGER %d]: Showed his documents.\n", p.getPassengerId());
		numberPassengersTransported++;
		showedDocuments=true;
		countTestShowDocuments++;
	}
	
	// pilot
	public synchronized boolean parkAtTransferGate() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.AT_TRANSFER_GATE);
		
		
		if(AirLift.N_PASSENGERS - numberPassengersTransported > AirLift.FLIGHT_MIN_P) {
			lastFlight = false;
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = FALSE).\n");
			
		}
		else{
			lastFlight = true;
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = TRUE).\n");
		}
		notifyAll();
		return lastFlight;
	}
	
	// pilot
	public synchronized void informPlaneReadyForBoarding() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.READY_FOR_BOARDING);	
		System.out.printf("[PILOT]: Inform plane ready for boarding.\n");
		planeReady = true;
		notifyAll();
	}
	
	// pilot
	public synchronized void waitForAllInBoard() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.WAITING_FOR_BOARDING);
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
	
	
	// pilot
	public synchronized void flyToDestinationPoint() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.FLYING_FORWARD);
		System.out.printf("[PILOT]: Flying forward...\n");
		try {
			Thread.sleep((long) (new Random().nextInt(15)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// hostess - check again not sure
	public synchronized void waitForNextFlight() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_NEXT_FLIGHT);
		System.out.printf("[HOSTESS]: Waiting for next flight...\n");
		while(!planeReady) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	// hostess
	public synchronized void prepareForPassBoarding() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		System.out.printf("[HOSTESS]: Prepare for pass boarding...\n");
		
		// reset variables
		currentFlightPassengers = 0; 
		askDocument_id = -1;
		checkDocument_id = -1;
		canBoard = -1;
		
		System.out.print("QUEUE: ");
		for (int element : passengersQueue) {
			  System.out.printf("%d  ", element);
			}
		System.out.println();
		
		while(passengersQueue.size() == 0) { // waits for the first passenger
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	// hostess
	public synchronized int checkDocuments() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.CHECK_PASSENGER);
		
		askDocument_id = passengersQueue.remove(); // get the passenger at the head of the queue
		
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
	
	
	// hostess
	public synchronized boolean waitForNextPassenger(int currentPassengers) {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		System.out.printf("[HOSTESS]: Waiting for next passenger...\n");

		//canBoard = checkDocument_id; // authorize the last passengers that showed his documents
		System.out.println("askDocument_id: "+askDocument_id);
		canBoardPlane[askDocument_id] = true;
		askDocument_id = -1;
		checkDocument_id = -1;
		notifyAll();
		
		int numberPassengersLeft = AirLift.N_PASSENGERS  - numberPassengersTransported;
		
		if(lastFlight) {
			if(currentPassengers == numberPassengersLeft && passengersQueue.size() == 0 ) {
				// if it is the last flight and there are no more passengers, can take off
				System.out.printf("[HOSTESS]: LAST FLIGHT, can take off with %d passengers\n\n", currentPassengers);
				return true;
			}
			else {
				System.out.printf("[HOSTESS]: Estive aqui\n");
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
		} else {
			if(currentPassengers >= AirLift.FLIGHT_MIN_P) { 
				return true;
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
	
	// hostess
	public synchronized void informPlaneReadyToTakeOff() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.READY_TO_FLY);
		System.out.println("countTestShowDocuments= "+countTestShowDocuments);
		if(showedDocuments) {
			boardingCompleted = true;
			planeReady = false;
			System.out.printf("[HOSTESS]: Inform plane ready to takeoff...\n");
			notifyAll();
		}
		
	}
}
