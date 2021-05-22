package proxies;

import common.Message;
import sharedRegions.Repository;

/**
 * This data type implements the Repository, unwrapping
 * the received messages and forwarding them to the actual
 * Repository. Builds the message reply.
 */
public class RepositoryProxy implements SharedRegionProxy {
	/**
	 * Repository 
	 * @serialField repository
	 */
	private final Repository repository;

	/**
	 * Simulation has finished 
	 * @serialField finished
	 */
	private boolean finished;

    /**
     * Repository proxy instantiation
	 * @param Repository repository
     */
    public RepositoryProxy(Repository repository) {
		this.repository = repository;
		finished=false;
    }
    
    public Message processAndReply(Message msg) {
		Message nm = new Message();
		
		switch(msg.getMessageType()) {
		case INQUEUE:
			repository.inQueue();
			break;
		case OUTQUEUE:
			repository.outQueue();
			break;
		case GETTOTALNUMBERPASSENGERSTRANSPORTED:
			nm.setIntVal1(repository.getTotalNumberPassengersTransported());
			break;
		case INCPASSENGERSPLANE:
			repository.incPassengersPlane();
			break;
		case DECPASSENGERSPLANE:
			repository.decPassengersPlane();
			break;
		case INCTOTALNUMBERPASSENGERSTRANSPORTED:
			repository.incTotalNumberPassengersTransported();
			break;
		case DECTOTALNUMBERPASSENGERSTRANSPORTED:
			repository.decTotalNumberPassengersTransported();
			break;
		case SETPASSENGERSTATE:
			repository.setPassengerState(msg.getIntVal1(), msg.getEntityState(), msg.getBoolVal1());
			break;
		case SETPILOTSTATE:
			repository.setPilotState(msg.getEntityState());
			break;
		case SETHOSTESSSTATE:
			repository.setHostessState(msg.getEntityState());
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
