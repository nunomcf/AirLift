package common;

import java.util.Date;

/**
* Simulation parameters to be used.
*/
public class Parameters {

	/**
	 * Number of passengers
	 */
	public static int N_PASSENGERS = 21;
	
	/**
	 * Minimum number of passengers in a flight
	 */
	public static int FLIGHT_MIN_P = 5;
	
	/**
	 * Maximum number of passengers in a flight
	 */
	public static int FLIGHT_MAX_P = 10;
	
	/**
	 * Repository log filename
	 */
	public static String filename = "file_" + new Date().toString().replace(' ', '_') + ".txt";

	/**
   * Departure Airport hostname
	* @serial departureAirportHostName
   */
	public static final String departureAirportHostName = "l040101-ws04.ua.pt";
	//public static final String departureAirportHostName = "localhost";
	
	/**
	 * Departure Airport port
	 * @serial departureAirportPort
	 */
	public static final int departureAirportPort = 22344;
	
	/**
	 * Departure Airport name entry
	 * @serial departureAirportNameEntry;
	 */
	public static final String departureAirportNameEntry = "departureAirport";

	/**
	 * Plane hostname
	 * @serial planeHostName
	 */
	public static final String planeHostName = "l040101-ws06.ua.pt";
	//public static final String planeHostName = "localhost";

	/**
	 * Plane port
	 * @serial planePort
	 */
	public static final int planePort = 22345;
	
	/**
	 * Plane name entry
	 * @serial planeNameEntry;
	 */
	public static final String planeNameEntry = "plane";

	/**
	 * Repository hostname
	 * @serial repositoryHostName
	 */	
	public static final String repositoryHostName = "l040101-ws07.ua.pt";
	//public static final String repositoryHostName = "localhost";

	/**
	 * Repository port
	 * @serial repositoryPort
	 */	
	public static final int repositoryPort = 22346;
	
	/**
	 * Repository name entry
	 * @serial repositoryNameEntry;
	 */
	public static final String repositoryNameEntry = "repository";
	
	/**
	 * Destination Airport hostname
	 * @serial destinationAirportHostName
	 */
	public static final String destinationAirportHostName = "l040101-ws05.ua.pt";
	//public static final String destinationAirportHostName = "localhost";
	
	/**
	 * Destination Airport port
	 * @serial destinationAirportPort
	 */
	public static final int destinationAirportPort = 22347;
	
	/**
	 * Destination Airport name entry
	 * @serial destinationAirportNameEntry;
	 */
	public static final String destinationAirportNameEntry = "destinationAirport";
	
	/**
     * Server registry host name.
     */
    public final static String serverRegistryHostname = "l040101-ws08.ua.pt";
    
    /**
     * Server registry server port.
     */
    public final static int serverRegistryPort = 22348;
    
    /**
     * Server registry host name.
     */
    public final static String registryHostname = "l040101-ws08.ua.pt";
    
    /**
     * Server registry server port.
     */
    public final static int registryPort = 22349;
    
    /**
     * Registry name entry for the register.
     */
    public final static String registryNameEntry = "registry";
    
	/**
	 * Number of shared regions
	 */
	public final static int sharedRegions = 4;
}

