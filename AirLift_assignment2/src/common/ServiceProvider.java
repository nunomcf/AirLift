package common;

import proxies.SharedRegionProxy;

/**
 * Receives messages and redirects them to the shared region
 * to be unwrapped and processed. Local impersonation of the
 * pilot, hostess and passenger.
 */
public class ServiceProvider extends Thread implements PilotInterface, HostessInterface,  PassengerInterface {
	/**
	 * Shared region proxy
	 * @serialField sharedRegion
	 */
	private SharedRegionProxy sharedRegion;

	/**
	 * Server communication
	 * @serialField serverCom
	 */
	private ServerComm serverCom;

	/**
	 * Pilot, Hostess or Passenger state
	 * @serialField state
	 */
	private States state;
	
	/**
	 * Passenger identification
	 * @serialField id
	 */
    private int id;
	
	/**
	 * Pilot and Hostess LastFlight flag
	 * @serialField lastFlight
	 */
	private boolean lastFlight = false;


	/**
	 * Service provider instantiation
	 * @param sharedRegion shared region in this server
	 * @param serverCom server communication channel
	 */
	public ServiceProvider(SharedRegionProxy sharedRegion, ServerComm serverCom) {
		this.sharedRegion = sharedRegion;
		this.serverCom = serverCom;
	}

	/**
	 * Waits for message and sends it to shared region to be processed
	 */
	@Override
	public void run() {
		Message msg = (Message) serverCom.readObject();
		msg = sharedRegion.processAndReply(msg);
		serverCom.writeObject(msg);
		serverCom.close();
	}

    /**
     * Returns this pilot's state.
     * @return pilot's current state
     */
	public States getPilotState() {
		return state;
	}

    /**
     * Returns this hostess's state.
     * @return hostess's current state
     */
	public States getHostessState() {
		return state;
	}

	/**
	 * Returns this passenger's state.
	 * @return passenger's current state
	 */
	public States getPassengerState() {
		return state;
	}

    /**
     * Sets this customer's state.
     * @param s desired state
     */
	public void setState(States s) {
		state = s;
	}

    /**
     * Returns this passenger's id.
     * @return passenger's id
     */
	public int getPassengerId() {
		return id;
	}

	/**
	 * Returns this mechanic's id.
	 * @return mechanic's id
	 */
	public int getMechanicId() {
		return id;
	}

	/**
     * Sets the Passenger's id.
     * @param id desired id
    */
	public void setID(int id) {
		this.id = id;
	}

    /**
     * Returns if is the last Flight.
     * @return pilot's decision 
     */
	public boolean getLastFlight() {
		return lastFlight;
	}

	/**
     * Sets if is the last Flight by pilot.
     * @param lastFlight if is the last Flight 
	*/
	public void setLastFlight(boolean lastFlight) {
		this.lastFlight = lastFlight;
	}

}
