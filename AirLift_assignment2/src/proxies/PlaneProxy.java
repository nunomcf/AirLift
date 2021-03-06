package proxies;

import common.Message;
import common.Parameters;
import common.ServiceProvider;
import sharedRegions.Plane;

/**
 * This data type implements the Plane, unwrapping
 * the received messages and forwarding them to the actual
 * Plane. Builds the message reply.
 */
public class PlaneProxy implements SharedRegionProxy {
	/**
	 * Plane 
	 * @serialField plane
	 */
	private final Plane plane;

	/**
	 * Simulation has finished 
	 * @serialField finished
	 */
	private int finished;

    /**
     * Plane proxy instantiation
	 * @param Plane plane
     */
    public PlaneProxy(Plane plane) {
		this.plane = plane;
		finished=0;
    }
    
    public Message processAndReply(Message msg) {
		Message nm = new Message();
		ServiceProvider sp = (ServiceProvider) Thread.currentThread(); 

		switch(msg.getMessageType()) {
		case BOARDTHEPLANE:
			sp.setID(msg.getEntityId());
			plane.boardThePlane();
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORENDOFFLIGHT:
			sp.setID(msg.getEntityId());
			plane.waitForEndOfFlight();
			nm.setEntityState(sp.getPassengerState());
			break;
		case LEAVETHEPLANE:
			sp.setID(msg.getEntityId());
			plane.leaveThePlane();
			synchronized(this){
				finished++;
			}
			nm.setEntityState(sp.getPassengerState());
			break;
		case INFORMPLANEREADYTOTAKEOFF:			
			plane.informPlaneReadyToTakeOff(msg.getIntVal1());
			nm.setEntityState(sp.getPassengerState());
			break;
		case WAITFORALLINBOARD:
			plane.waitForAllInBoard();
			nm.setEntityState(sp.getPassengerState());
			break;
		case ANNOUNCEARRIVAL:
			plane.announceArrival();
			nm.setEntityState(sp.getPassengerState());
			break;
		default:
			assert(false); 
			break;
		}
			
		return nm;
	}
    
	public synchronized boolean getSimStatus(){
		return finished!=Parameters.N_PASSENGERS;
	}
}


