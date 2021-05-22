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
			nm.setBoolVal1(departureAirport.parkAtTransferGate());
			nm.setEntityState(sp.getPassengerState());
			break;
		case INFORMPLANEREADYFORBOARDING:
			departureAirport.informPlaneReadyForBoarding();
			nm.setEntityState(sp.getPassengerState());
			break;
		case FLYTODESTINATIONPOINT:
			departureAirport.flyToDestinationPoint();
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORNEXTFLIGHT:
			nm.setBoolVal1(departureAirport.waitForNextFlight());
			nm.setEntityState(sp.getPassengerState());
			break;
		case PREPAREFORPASSBOARDING:
			departureAirport.prepareForPassBoarding();
			nm.setEntityState(sp.getPassengerState());
			break;
		case CHECKDOCUMENTS:
			nm.setIntVal1(departureAirport.checkDocuments());
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORNEXTPASSENGER:
			nm.setBoolVal1(departureAirport.waitForNextPassenger(msg.getIntVal1(), msg.getBoolVal1()));
			System.out.println("ULTIMO PASSAGEIRO?????");
			System.out.println(nm.getBoolVal1());
			nm.setEntityState(sp.getHostessState());
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
