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

	public Plane(Repository repo) {
		this.repo = repo;
		passengersSeated = new LinkedList<>();
	}

	// passenger
	public synchronized void boardThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		flight_finished = false;
		passengersSeated.add(p.getPassengerId());
		System.out.printf("[PASSENGER %d]: Boarding the plane...\n", p.getPassengerId());
	}

	// passenger
	public synchronized void waitForEndOfFlight() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.IN_FLIGHT);
		System.out.printf("[PASSENGER %d]: Waiting for end of flight...\n", p.getPassengerId());
		while (!flight_finished) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}

	// passenger
	public synchronized void leaveThePlane() {
		Passenger p = (Passenger) Thread.currentThread();
		p.setState(States.AT_DESTINATION);
		passengersSeated.remove(p.getPassengerId());

		if (passengersSeated.size() == 0) {
			allPassengersLeft = true;
			notifyAll();
		}
		System.out.printf("[PASSENGER %d]: Leaving the plane...\n", p.getPassengerId());
	}

	// pilot
	public synchronized void announceArrival() {
		Pilot pilot = (Pilot) Thread.currentThread();
		pilot.setState(States.DEBOARDING);
		System.out.printf("[PILOT]: Announce arrival.\n");

		flight_finished = true;
		notifyAll();
		while (!allPassengersLeft) {
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
