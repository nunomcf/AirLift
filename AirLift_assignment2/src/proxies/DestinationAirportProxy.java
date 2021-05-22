package proxies;

import common.Message;
import common.ServiceProvider;
import sharedRegions.DestinationAirport;

/**
 * This data type implements the DestinationAirport, unwrapping
 * the received messages and forwarding them to the actual
 * DestinationAirport. Builds the message reply.
 */
public class DestinationAirportProxy implements SharedRegionProxy {
	/**
	 * DestinationAirport 
	 * @serialField destinationAirport
	 */
	private final DestinationAirport destinationAirport;

	/**
	 * Simulation has finished 
	 * @serialField finished
	 */
	private boolean finished;

    /**
     * DestinationAirport proxy instantiation
	 * @param DestinationAirport destinationAirport
     */
    public DestinationAirportProxy(DestinationAirport destinationAirport) {
		this.destinationAirport = destinationAirport;
		finished=false;
    }
    
    public Message processAndReply(Message msg) {
		Message nm = new Message();
		ServiceProvider sp = (ServiceProvider) Thread.currentThread(); 

		switch(msg.getMessageType()) {
		case FLYTODEPARTUREPOINT:
			destinationAirport.flyToDeparturePoint();
			nm.setEntityState(sp.getPassengerState());
			break;
		default:
			assert(false);
			break;
		}
		return nm;
	}
    
	public synchronized boolean getSimStatus(){
		return !finished;
	}
}