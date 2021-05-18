package proxies;

import common.Message;
import common.ServiceProvider;
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
		ServiceProvider sp = (ServiceProvider) Thread.currentThread(); 

		switch(msg.getMessageType()) {
			
		}
			
		return nm;
	}
    
	public synchronized boolean getSimStatus(){
		return !finished;
	}
}
