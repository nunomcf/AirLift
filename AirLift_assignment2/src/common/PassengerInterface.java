package common;

/**
 * This interface represents the Passenger as it is needed in the 
 * server side, meant to be implemented by the service provider.
 */
public interface PassengerInterface {
	/**
	 * Returns this passenger's id.
	 * @return passenger's id
	 */
	public int getPassengerId();
	
	/**
    * Returns this passenger's state.
    * @return passenger's current state
    */
	public States getPassengerState();
	
	/**
    * Sets the passenger's state.
	 * @param s the state to be set
    */
	public void setState(States s);
}
