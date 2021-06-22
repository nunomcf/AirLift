package mainProgram;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Parameters;
import entities.Passenger;
import interfaces.DepartureAirportInterface;
import interfaces.PlaneInterface;

public class PassengerMain {
	
	
	  /**
     * Passenger main's thread
     * @param args unused main args
	 */ 
	public static void main(String args[]) {

        String rmiRegHostName = Parameters.registryHostname;
        int rmiRegPortNumb = Parameters.registryPort;

        /* look for the remote object by name in the remote host registry */
		//Interface Shared memory regions
        DepartureAirportInterface departureAirport=null;
		PlaneInterface plane=null;

        Registry registry=null;
        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        {
        	departureAirport = (DepartureAirportInterface) registry.lookup (Parameters.departureAirportNameEntry);
            plane = (PlaneInterface) registry.lookup (Parameters.planeNameEntry);
        }
        catch (RemoteException e)
        { System.out.println("Passenger look up exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { System.out.println("Passenger not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
		
		
		Passenger[] passengers = new Passenger[Parameters.N_PASSENGERS];
        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            passengers[i] = new Passenger(i, departureAirport, plane);
        }


        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            passengers[i].start();
        }


        for(int i = 0; i < Parameters.N_PASSENGERS; i++){
            try{
                passengers[i].join();
            } catch (InterruptedException e){}
        }

	}

}
