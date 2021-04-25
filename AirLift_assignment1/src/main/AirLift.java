package main;
import entities.*;
import sharedRegions.*;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Random;

/**
 *   Simulation of a AirLift.
 *   Dynamic solution.
 */
public class AirLift {	
	/**
	 * Number of passengers
	 */
	public static int N_PASSENGERS = 21;
	
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
	
	/**
	* AirLift main's thread
	* @param args unused main args
	* @throws FileNotFoundException When file not found
	*/
	public static void main(String args[]) throws FileNotFoundException {
		Repository repository = new Repository();
		DepartureAirport departure = new DepartureAirport(repository);
		DestinationAirport destination = new DestinationAirport(repository);
		Plane plane = new Plane(repository);
		
		Passenger[] passengers = new Passenger[N_PASSENGERS]; 
		Hostess hostess = new Hostess(departure, plane);
		Pilot pilot = new Pilot(departure, destination, plane);
		
		System.out.print("Simulation started...\n");
		hostess.start();
		pilot.start();
				
		for(int i = 0; i < N_PASSENGERS; i++) {
			passengers[i] = new Passenger(i, departure, plane);
			try {
				Thread.sleep((long) (new Random().nextInt(100)));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			passengers[i].start();
		}
		
		for(int i = 0; i < N_PASSENGERS; i++) {
			try {
				passengers[i].join();
				System.out.printf("Passenger %d terminated.\n", i);
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
		
		repository.closeWriter();
		System.out.print("Simulation finished...\n");
	}
}
