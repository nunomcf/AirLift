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
		repo.setPassengerState(p.getPassengerId(), States.GOING_TO_AIRPORT, true);
		
		
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
		repo.setPassengerState(p.getPassengerId(), States.IN_QUEUE, true);
		
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
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		repo.setPassengerState(p.getPassengerId(), States.IN_QUEUE, true);
		
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
		numberPassengersTransported++;
	}
	
	// pilot
	public synchronized boolean parkAtTransferGate() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.AT_TRANSFER_GATE);
		repo.setPilotState(States.AT_TRANSFER_GATE);
		
		
		if(AirLift.N_PASSENGERS - numberPassengersTransported > AirLift.FLIGHT_MIN_P) {
			lastFlight = false;
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = FALSE).\n");
			//notifyAll();
			return false;
		}
		else{
			lastFlight = true;
			//notifyAll();
			System.out.printf("[PILOT]: Parked at transfer gate (LAST FLIGHT = TRUE).\n");
			return true;
		}
	}
	
	// pilot
	public synchronized void informPlaneReadyForBoarding() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.READY_FOR_BOARDING);
		repo.setPilotState(States.READY_FOR_BOARDING);
		
		System.out.printf("[PILOT]: Inform plane ready for boarding.\n");
		planeReady = true;
		notifyAll();
	}
	
	
	
	
	// pilot
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
	
	// hostess - check again not sure
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
		if(lastFlight) return true;
		else return false;
	}
	
	
	// hostess
	public synchronized void prepareForPassBoarding() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		repo.setHostessState(States.WAIT_FOR_PASSENGER);
		
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
		repo.setHostessState(States.CHECK_PASSENGER);
		
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
	public synchronized boolean waitForNextPassenger(int currentPassengers, boolean lastF) {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		repo.setHostessState(States.WAIT_FOR_PASSENGER);
		
		
		System.out.printf("[HOSTESS]: Waiting for next passenger...\n");

		//canBoard = checkDocument_id; // authorize the last passengers that showed his documents
		canBoardPlane[askDocument_id] = true;
		askDocument_id = -1;
		checkDocument_id = -1;
		notifyAll();
		
		int numberPassengersLeft = AirLift.N_PASSENGERS - numberPassengersTransported;
		
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
			if(currentPassengers >= AirLift.FLIGHT_MIN_P) {
				if(passengersQueue.size() == 0) {
					return true;
				} 
				else {
					if(currentPassengers == AirLift.FLIGHT_MAX_P) {
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
