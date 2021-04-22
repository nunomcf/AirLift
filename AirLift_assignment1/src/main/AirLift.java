package main;

import entities.*;
import sharedRegions.*;
import java.util.Date;

public class AirLift {	
	/**
	 * Number of passengers
	 */
	public static int N_PASSENGERS = 10;
	
	/**
	 * Minimum number of passengers in a flight
	 */
	public static int FLIGHT_MIN_P = 5;
	
	/**
	 * Maximum number of passengers in a flight
	 */
	public static int FLIGHT_MAX_P = 10;
	
	/**
	 * Repository log filename
	 */
	public static String filename = "file_" + new Date().toString().replace(' ', '_') + ".txt";
	
	public static void main(String args[]) {
		
		Repository repository = new Repository();
		DepartureAirport departure = new DepartureAirport(repository);
		DestinationAirport destination = new DestinationAirport(repository);
		Plane plane = new Plane(repository);
		
		// Active entities
		Passenger[] passengers = new Passenger[N_PASSENGERS]; 
		Hostess hostess = new Hostess(departure, destination, plane);
		Pilot pilot = new Pilot(departure, destination, plane);
		
		System.out.print("Simulation started...\n");
		
		
		hostess.start();
		pilot.start();
				
		for(int i = 0; i < N_PASSENGERS; i++) {
			passengers[i] = new Passenger(i, departure, destination, plane);
			passengers[i].start();
		}
		// Wait for termination
		for(int i = 0; i < N_PASSENGERS; i++) {
			try {
				passengers[i].join();
				//System.out.printf("Passenger %d terminated.\n", i);
			} catch(InterruptedException e) {}
		}
		try {
			hostess.join();
			System.out.printf("Hostess terminated.\n");
		} catch(InterruptedException e) {}
		
		try {
			pilot.join();
			System.out.printf("Pilot terminated.\n");
		} catch(InterruptedException e) {}
		
		System.out.print("Simulation finished...\n");
	}
}
