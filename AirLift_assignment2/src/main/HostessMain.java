package main;

import entities.Hostess;
import stubs.DepartureAirportStub;
import stubs.PlaneStub;

public class HostessMain {

	public static void main(String[] args) {
		DepartureAirportStub departure = new DepartureAirportStub();
		PlaneStub plane = new PlaneStub();
		Hostess hostess = new Hostess(departure, plane);
		hostess.start();
		try {
			hostess.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("Bye!\n");
	}
	
}
