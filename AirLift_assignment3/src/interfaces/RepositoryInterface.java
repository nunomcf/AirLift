package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import common.States;

public interface RepositoryInterface extends Remote {
	/**
     * Increment QueueCount.
     */
    public void inQueue() throws RemoteException;

    /**
     * Decrement QueueCount.
     */
    public void outQueue() throws RemoteException;
    
    /**
     * Increment passengersPlane.
     */
    public void incPassengersPlane() throws RemoteException;

    /**
     * Decrement passengersPlane.
     */
    public void decPassengersPlane() throws RemoteException;
    
    /**
     * Increment totalNumberPassengersTransported.
     */
    public void incTotalNumberPassengersTransported() throws RemoteException;

    /**
     * Decrement totalNumberPassengersTransported.
     */
    public void decTotalNumberPassengersTransported() throws RemoteException;
    
    /**
    * Get the Total Number of Passengers Transported
    * @return Total Number of Passengers Transported
    */
    public int getTotalNumberPassengersTransported() throws RemoteException;

   /**
    * Get the Total Number of Passengers in Queue
    * @return Total Number of Passengers in Queue
    */
    public int getQueueCount() throws RemoteException;
    
    /**
    *
    * @param pilotState state of the pilot
    */
    public void setPilotState(States pilotState) throws RemoteException;
   
    /**
    *
    * @param hostessState state of the hostess
    */
    public void setHostessState(States hostessState) throws RemoteException;

    /**
    *
    * @param id if of the passenger
    * @param passengerState state of the passenger
    * @param export if we want to print the state change in file
    */
    public void setPassengerState(int id, States passengerState, boolean export) throws RemoteException;
    
    /**
	 * Sets the state of hasTerminated if it's time to stop.
     * @throws java.rmi.RemoteException RemoteException
	 */
	public void terminate() throws RemoteException;

	/**
	 * Gets this shared region's termination status.
	 * @return if this shared region has terminated
     * @throws java.rmi.RemoteException RemoteException
	 */
    public boolean getTerminationState() throws RemoteException;
   
}
