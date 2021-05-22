package main;

import entities.Passenger;
import stubs.DepartureAirportStub;
import stubs.PlaneStub;

public class PassengerMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DepartureAirportStub departure = new DepartureAirportStub();
		PlaneStub plane = new PlaneStub();
		Passenger passenger = new Passenger(Integer.parseInt(args[0]),departure,plane);
		passenger.start();
		try {
			passenger.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
