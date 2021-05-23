package main;

import entities.Pilot;
import stubs.DepartureAirportStub;
import stubs.DestinationAirportStub;
import stubs.PlaneStub;
import stubs.RepositoryStub;

public class PilotMain {

	public static void main(String[] args) {
		DepartureAirportStub departure = new DepartureAirportStub();
		DestinationAirportStub destination = new DestinationAirportStub();
		RepositoryStub repository = new RepositoryStub();
		PlaneStub plane = new PlaneStub();
		Pilot pilot = new Pilot(departure, destination, plane);
		pilot.start();
		try {
			pilot.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		destination.dStopped();
		repository.iStopped();
		System.out.printf("Bye!\n");
	}

}
