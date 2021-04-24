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
	private boolean allPassengersLeft = false;
	private Queue<Integer> passengersSeated;
	private boolean boardingCompleted = false;
	private int n_passengersBoarded = 0;
	
	
	public Plane(Repository repo) {
		this.repo = repo;
		passengersSeated = new LinkedList<>();
	}
	
	// passenger
	public synchronized void boardThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		repo.setPassengerState(p.getPassengerId(), States.IN_FLIGHT, true);
		
		flight_finished = false;
		passengersSeated.add(p.getPassengerId());
		n_passengersBoarded++;
		notifyAll();
		System.out.printf("[PASSENGER %d]: Boarding the plane...\n", p.getPassengerId());
	}
	
	// passenger
	public synchronized void waitForEndOfFlight() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		repo.setPassengerState(p.getPassengerId(), States.IN_FLIGHT, true);
		
		System.out.printf("[PASSENGER %d]: Waiting for end of flight...\n", p.getPassengerId());
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
		repo.setPassengerState(p.getPassengerId(), States.AT_DESTINATION, true);
		passengersSeated.remove(p.getPassengerId());
		if(passengersSeated.size() == 0) {
			allPassengersLeft = true;
			notifyAll();
		}		
		System.out.printf("[PASSENGER %d]: Leaving the plane...\n", p.getPassengerId());
	}
	
	// hostess
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
	
	// pilot
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


	// pilot
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
