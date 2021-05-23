package main;

import common.Parameters;
import entities.Passenger;
import stubs.DepartureAirportStub;
import stubs.PlaneStub;

public class PassengerMain {

	public static void main(String[] args) {
		DepartureAirportStub departure = new DepartureAirportStub();
		PlaneStub plane = new PlaneStub();
		
		Passenger[] passengers = new Passenger[Parameters.N_PASSENGERS];
        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            passengers[i] = new Passenger(i, departure, plane);
        }


        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            passengers[i].start();
        }


        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            try{
                passengers[i].join();
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.printf("Bye!\n");
	}

}
