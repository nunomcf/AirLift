package main;

import entities.Pilot;
import stubs.DepartureAirportStub;
import stubs.DestinationAirportStub;
import stubs.PlaneStub;

public class PilotMain {

	public static void main(String[] args) {
		DepartureAirportStub departure = new DepartureAirportStub();
		DestinationAirportStub destination = new DestinationAirportStub();
		PlaneStub plane = new PlaneStub();
		Pilot pilot = new Pilot(departure, destination, plane);
		pilot.start();
		try {
			pilot.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
