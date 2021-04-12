package sharedRegions;

import java.util.*;
import entities.*;

public class DepartureAirport {
	
	private Repository repo;
	
	private Queue<Integer> passengersQueue;
	
	public DepartureAirport(Repository repo) {
		this.repo = repo;
		passengersQueue = new LinkedList<>();
		
	}
	
	// passenger
	public synchronized void waitInQueue() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_QUEUE);
		passengersQueue.add(p.getPassengerId());
		notifyAll();
		
	}
	
	// passenger
	public synchronized void showDocuments() {
			
	}
}
