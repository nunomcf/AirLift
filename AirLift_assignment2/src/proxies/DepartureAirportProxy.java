package proxies;

import common.Message;
import common.Parameters;
import common.ServiceProvider;
import sharedRegions.DepartureAirport;

/**
 * This data type implements the DepartureAirport, unwrapping
 * the received messages and forwarding them to the actual
 * DepartureAirport. Builds the message reply.
 */
public class DepartureAirportProxy implements SharedRegionProxy {
	/**
	 * DepartureAirport 
	 * @serialField departureAirport
	 */
	private final DepartureAirport departureAirport;

	/**
	 * Simulation has finished 
	 * @serialField finished
	 */
	private boolean finished;

    /**
     * DepartureAirport proxy instantiation
	 * @param DepartureAirport departureAirport
     */
    public DepartureAirportProxy(DepartureAirport departureAirport) {
		this.departureAirport = departureAirport;
		finished=false;
    }
    
    public Message processAndReply(Message msg) {
		Message nm = new Message();
		ServiceProvider sp = (ServiceProvider) Thread.currentThread(); 

		switch(msg.getMessageType()) {
			
		}
			
		return nm;
	}
    
	public synchronized boolean getSimStatus(){
		return !finished;
	}
}
