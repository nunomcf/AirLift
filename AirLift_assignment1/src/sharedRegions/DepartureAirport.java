package sharedRegions;

import java.util.*;
import entities.*;
import main.AirLift;

public class DepartureAirport {
	
	private Repository repo;
	
	private Queue<Integer> passengersQueue;
	
	// variable to store the number of passengers transported
	private int numberPassengersTransported = 0;
	
	private int currentFlightPassengers = 0;
	// variable to terminate hostess life cycle (set by the pilot after returning from the last flight)
	private boolean lastFlight = false;
	
	private boolean planeReady = false;
	
	private boolean boardingCompleted = false;
	
	
	private int askDocument_id = -1;
	private int checkDocument_id = -1;
	private int canBoard = -1;
	
	
	public DepartureAirport(Repository repo) {
		this.repo = repo;
		passengersQueue = new LinkedList<>();
		
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
		try {
			Thread.sleep((long) (new Random().nextInt(5)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// passenger
	public synchronized void waitInQueue() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		passengersQueue.add(p.getPassengerId());
		notifyAll();
		while(askDocument_id != p.getPassengerId()) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	// passenger
	public synchronized void showDocuments() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		checkDocument_id = p.getPassengerId();
		notifyAll();
		while(canBoard != p.getPassengerId()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// pilot
	public synchronized boolean parkAtTransferGate() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.AT_TRANSFER_GATE);	
		if(numberPassengersTransported < AirLift.N_PASSENGERS) {
			lastFlight = false;
			// notifyAll();
			return false;
		}
		else{
			lastFlight = true;
			notifyAll();
			return true;
		}
	}
	
	// pilot
	public synchronized void informPlaneReadyForBoarding() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.READY_FOR_BOARDING);	
		planeReady = true;
		notifyAll();
	}
	
	// pilot
	public synchronized void waitForAllInBoard() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.WAITING_FOR_BOARDING);
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
		try {
			Thread.sleep((long) (new Random().nextInt(5)));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// hostess - check again not sure
	public synchronized boolean waitForNextFlight() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_NEXT_FLIGHT);
		
		while(!planeReady && !lastFlight) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(lastFlight) return true;
		else return false;
	}
	
	
	// hostess
	public synchronized void prepareForPassBoarding() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		
		currentFlightPassengers = 0; // reset variable
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
		askDocument_id = passengersQueue.poll(); // get the passenger at the head of the queue
		notifyAll();
		while(askDocument_id != checkDocument_id) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		currentFlightPassengers += 1;
		return currentFlightPassengers;
	}
	
	
	// hostess
	public synchronized boolean waitForNextPassenger() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.WAIT_FOR_PASSENGER);
		
		canBoard = checkDocument_id; // authorize the last passengers that showed his documents
		askDocument_id = -1;
		checkDocument_id = -1;
		notifyAll();
		
		while(passengersQueue.size() == 0) { // waits for the next passenger
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		if (passengersQueue.size() - 1 == 0) 
			return true;
		else 
			return false;
	}
	
	// hostess
	public synchronized void informPlaneReadyToTakeOff() {
		Hostess h = (Hostess) Thread.currentThread();
		h.setState(States.READY_TO_FLY);
		boardingCompleted = true;
		notifyAll();
	}
}
