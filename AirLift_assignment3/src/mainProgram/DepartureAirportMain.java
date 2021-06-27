package mainProgram;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.Parameters;
import interfaces.Register;
import interfaces.RepositoryInterface;
import sharedRegions.DepartureAirport;

public class DepartureAirportMain {

	public static void main(String[] args) {
		/*
		 * ServerComm serverComm, serverConn; ServiceProvider serviceProvider;
		 * 
		 * DepartureAirport departure = new DepartureAirport(new RepositoryStub());
		 * DepartureAirportProxy proxy = new DepartureAirportProxy(departure);
		 * 
		 * serverComm = new ServerComm(Parameters.departureAirportPort, 1000);
		 * serverComm.start();
		 * 
		 * while(proxy.getSimStatus()) { try { serverConn = serverComm.accept();
		 * serviceProvider = new ServiceProvider(proxy, serverConn);
		 * serviceProvider.start(); } catch(SocketTimeoutException e) { }
		 * 
		 * } System.out.printf("Bye!\n");
		 */
		String regionName = Parameters.departureAirportNameEntry;
		
		
		/* get location of the generic registry service */
		String rmiRegHostName =  Parameters.registryHostname;
		int rmiRegPortNumb = Parameters.registryPort;
		
		/* name entries */
		String nameEntryBase = Parameters.registryNameEntry;
		String nameEntryObject = regionName;
		
		/* Registry and register */
		Registry registry = null;
		Register reg = null;
		
		/* create and install the security manager */

		if (System.getSecurityManager () == null)
			System.setSecurityManager (new SecurityManager ());
		System.out.println("Security manager was installed!");
		
		/* Get registry */
		try
		{ registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
		}
		catch (RemoteException e)
		{ System.out.println("RMI registry creation exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		System.out.println("RMI registry was created!");
		
		/* Get repository from RMI server */
		RepositoryInterface repo = null;
		
		try {
			repo = (RepositoryInterface) registry.lookup(Parameters.repositoryNameEntry);
		} catch (RemoteException e) { 
            System.out.println("Repository lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { 
            System.out.println("Repository not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit(1);
		}
		
		/* Initialize shared region */
		DepartureAirport sharedRegion = new DepartureAirport(repo);
		Remote sharedRegionInt = null;
		
		try
		{ sharedRegionInt = UnicastRemoteObject.exportObject((Remote) sharedRegion, Parameters.departureAirportPort);
		}
		catch (RemoteException e)
		{ System.out.println(regionName + " stub generation exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		System.out.println("Stub was generated!");
		
		/* register it with the general registry service */
		try
		{ reg = (Register) registry.lookup (nameEntryBase);
		}
		catch (RemoteException e)
		{ System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		catch (NotBoundException e)
		{ System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		
		try
		{ reg.bind (nameEntryObject, sharedRegionInt);
		}
		catch (RemoteException e)
		{ System.out.println(regionName + " registration exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		catch (AlreadyBoundException e)
		{ System.out.println(regionName + " already bound exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		System.out.printf("Binded object %s\n",nameEntryObject);
		System.out.println(regionName + " object was registered!");
		
		/* Wait for shared region to terminate */
		try {
			while(!sharedRegion.getTerminationState()) {
				synchronized(sharedRegion) {
					sharedRegion.wait();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Main thread of " + regionName + " was interrupted.");
			System.exit(1);
		}
		catch (RemoteException e)
		{ 
			System.out.println(regionName + " remote exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		}

		System.out.println(regionName + " has finished");

		/* Unregister this shared region */
		try {
			reg.unbind(nameEntryObject);
		}
        catch (RemoteException e)
        { System.out.println(regionName + " unregistration exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        } catch (NotBoundException ex) {
          System.out.println(regionName + " unregistration exception: " + ex.getMessage ());
          ex.printStackTrace ();
          System.exit (1);
        }
        System.out.println(regionName + " was unregistered!");
		
		/* Unexport this shared region */
		try
        { UnicastRemoteObject.unexportObject ((Remote) sharedRegion, false);
        }
        catch (RemoteException e)
        { System.out.println(regionName + " unexport exception: " + e.getMessage ());
          e.printStackTrace ();
          System.exit (1);
        }
		
		System.out.printf("%s: Bye!\n",regionName);
	}
}
