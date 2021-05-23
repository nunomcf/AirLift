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
}

