package mainProgram;

import entities.Hostess;
import interfaces.DepartureAirportInterface;
import interfaces.PlaneInterface;

import java.rmi.registry.Registry;

import common.Parameters;

import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HostessMain {
	
	
	 /**
     * Hostess main's thread
     * @param args unused main args
	 */ 
	public static void main(String args[]) {

        String rmiRegHostName = Parameters.registryHostname;
        int rmiRegPortNumb = Parameters.registryPort;

		//Interface Shared memory regions
        DepartureAirportInterface departure=null;
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
        	departure = (DepartureAirportInterface) registry.lookup (Parameters.departureAirportNameEntry);
            plane = (PlaneInterface) registry.lookup (Parameters.planeNameEntry);
        }
        catch (RemoteException e)
        { System.out.println("Hostess look up exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { System.out.println("Hostess not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        Hostess hostess = new Hostess(departure, plane);
        hostess.start();
		try{
			hostess.join();
		} catch(InterruptedException e){}

	}
	
}
