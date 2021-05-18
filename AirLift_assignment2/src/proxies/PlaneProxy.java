package proxies;

import common.Message;
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
	private boolean finished;

    /**
     * Plane proxy instantiation
	 * @param Plane plane
     */
    public PlaneProxy(Plane plane) {
		this.plane = plane;
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


