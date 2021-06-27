package mainProgram;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.Parameters;
import entities.Pilot;
import interfaces.DepartureAirportInterface;
import interfaces.DestinationAirportInterface;
import interfaces.PlaneInterface;

public class PilotMain {
	
	
	/**
     * Pilot main's thread
     * @param args unused main args
	 */ 
	public static void main(String args[]) {

		String rmiRegHostName = Parameters.registryHostname;
		int rmiRegPortNumb = Parameters.registryPort;

		/* look for the remote object by name in the remote host registry */
		//Interface Shared memory regions
		DepartureAirportInterface departure=null;
		DestinationAirportInterface destination=null;
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
			destination = (DestinationAirportInterface) registry.lookup (Parameters.destinationAirportNameEntry);
			plane = (PlaneInterface) registry.lookup (Parameters.planeNameEntry);
			departure = (DepartureAirportInterface) registry.lookup (Parameters.departureAirportNameEntry);
		}
		catch (RemoteException e)
		{ System.out.println("Pilot look up exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		}
		catch (NotBoundException e)
		{ System.out.println("Pilot not bound exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		}

		Pilot pilot=new Pilot(departure, destination, plane);
		pilot.start();
		try{
			pilot.join();
		} catch(InterruptedException e){}
		
		/*destination.dStopped();
		repository.iStopped();
		System.out.printf("Bye!\n");*/

	}

}
