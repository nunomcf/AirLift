package common;

/**
 * This interface represents the Pilot as it is needed in the 
 * server side, meant to be implemented by the service provider.
 */
public interface PilotInterface {
	
	/**
     * Returns this pilot's state.
     * @return pilot's current state
     */
	public States getPilotState();
	
	/**
     * Sets the pilot's state.
	 * @param s the state to be set
     */
	public void setState(States s);

}
