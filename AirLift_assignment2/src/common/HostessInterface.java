package common;


/**
 * This interface represents the Hostess as it is needed in the 
 * server side, meant to be implemented by the service provider.
 */
public interface HostessInterface {
	
	/**
     * Returns the hostess' state.
     * @return hostess's current state
     */
	public States getHostessState();
	
	/**
     * Sets the hostess' state.
	 * @param s the state to be set
     */
	public void setState(States s);
}
