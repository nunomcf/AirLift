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
		case TRAVELTOAIRPORT:
			sp.setID(msg.getEntityId());
			departureAirport.travelToAirport();
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITINQUEUE:
			sp.setID(msg.getEntityId());
			departureAirport.waitInQueue();
			nm.setEntityState(sp.getPassengerState());
			break;
		case SHOWDOCUMENTS:
			sp.setID(msg.getEntityId());
			departureAirport.showDocuments();
			nm.setEntityState(sp.getPassengerState());
			break;
		case PARKATTRANSFERGATE:
			sp.setID(msg.getEntityId());
			departureAirport.parkAtTransferGate();
			nm.setEntityState(sp.getPassengerState());
			break;
		case INFORMPLANEREADYFORBOARDING:
			sp.setID(msg.getEntityId());
			departureAirport.informPlaneReadyForBoarding();
			nm.setEntityState(sp.getPassengerState());
			break;
		case FLYTODESTINATIONPOINT:
			sp.setID(msg.getEntityId());
			departureAirport.flyToDestinationPoint();
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORNEXTFLIGHT:
			sp.setID(msg.getEntityId());
			departureAirport.waitForNextFlight();
			nm.setEntityState(sp.getPassengerState());
			break;
		case PREPAREFORPASSBOARDING:
			sp.setID(msg.getEntityId());
			departureAirport.prepareForPassBoarding();
			nm.setEntityState(sp.getPassengerState());
			break;
		case CHECKDOCUMENTS:
			sp.setID(msg.getEntityId());
			departureAirport.checkDocuments();
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORNEXTPASSENGER:
			sp.setID(msg.getEntityId());
			departureAirport.waitForNextPassenger(1, false); // ????????????????????????????????
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
