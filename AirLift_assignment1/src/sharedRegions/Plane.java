package sharedRegions;

import java.util.LinkedList;
import java.util.Queue;

import entities.Hostess;
import entities.Passenger;
import entities.Pilot;
import entities.States;

public class Plane {
	
	private Repository repo;
	private boolean flight_finished = false;
	private Queue<Integer> passengersSeated;
	
	public Plane(Repository repo) {
		this.repo = repo;
		passengersSeated = new LinkedList<>();
	}
	
	// passenger
	public synchronized void boardThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		flight_finished = false;
	}
	
	// passenger
	public synchronized void waitForEndOfFlight() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		while(!flight_finished) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
	}
	
	// passenger
	public synchronized void leaveThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.AT_DESTINATION);
	}


	// pilot
	public synchronized void announceArrival() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.DEBOARDING);
		flight_finished = true;
		notifyAll();
	}
	

	
}
