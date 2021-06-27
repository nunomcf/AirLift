package mainProgram;

import java.io.FileNotFoundException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import common.Parameters;
import interfaces.Register;
import interfaces.RepositoryInterface;
import sharedRegions.Repository;


/**
 * Launch repository server
 */
public class RepositoryMain {

	/**
	* Shared regions main's thread
	* @param args shared region to be launched
	* @throws java.io.FileNotFoundException if the log file isn't found 
	*/
	public static void main(String args[]) throws FileNotFoundException {
		/* get location of the generic registry service */
		String rmiRegHostName =  Parameters.registryHostname;
		int rmiRegPortNumb = Parameters.registryPort;
		
		/* name entries */
		String nameEntryBase = Parameters.registryNameEntry;
		String nameEntryObject = Parameters.repositoryNameEntry;

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

		/* Initialize shared region */
		Repository repo = new Repository();
		RepositoryInterface repoInt = null;

		try
		{ repoInt = (RepositoryInterface) UnicastRemoteObject.exportObject(repo, Parameters.repositoryPort);
		}
		catch (RemoteException e)
		{ System.out.println("Repository  stub generation exception: " + e.getMessage ());
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
		{ reg.bind (nameEntryObject, repoInt);
		}
		catch (RemoteException e)
		{ System.out.println("Repository  registration exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		catch (AlreadyBoundException e)
		{ System.out.println("Repository  already bound exception: " + e.getMessage ());
		e.printStackTrace ();
		System.exit (1);
		}
		System.out.println("Repository  object was registered!");

		/* Wait for shared region to terminate */
		try {
			while(!repo.getTerminationState()) {
				synchronized(repo) {
					repo.wait();
				}
			}
		} catch (InterruptedException e) {
			System.out.println("Main thread of Repository was interrupted.");
			System.exit(1);
		}
		catch (RemoteException e)
		{ 
			System.out.println("Repository remote exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		}

		System.out.println("Repository has finished");

		/* Unregister this shared region */
		try {
			reg.unbind(nameEntryObject);
		}
		catch (RemoteException e)
		{ System.out.println("Repository unregistration exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		} catch (NotBoundException ex) {
			System.out.println("Repository unregistration exception: " + ex.getMessage ());
			ex.printStackTrace ();
			System.exit (1);
		}
		System.out.println("Repository was unregistered!");
		
		/* Unexport this shared region */
		try
		{ UnicastRemoteObject.unexportObject (repo, false);
		}
		catch (RemoteException e)
		{ System.out.println("Repository unexport exception: " + e.getMessage ());
			e.printStackTrace ();
			System.exit (1);
		}
		System.out.printf("Repository: Bye!\n");
	}
}
