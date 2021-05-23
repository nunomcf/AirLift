package stubs;

import common.ClientComm;
import common.Message;
import common.MessageType;
import common.Parameters;
import common.States;

public class RepositoryStub {
	/**
	 * Repository server hostname
	 * @serialField serverHostName
	 */
	private String serverHostName;

	/**
	 * Repository server port
	 * @serialField serverPort
	 */
	private int serverPort;

    /**
     * Repository stub instantiation
     */
    public RepositoryStub() {
			serverHostName = Parameters.repositoryHostName;
			serverPort = Parameters.repositoryPort;
    }
    
    public void inQueue() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.INQUEUE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void outQueue() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.OUTQUEUE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void incPassengersPlane() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.INCPASSENGERSPLANE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void decPassengersPlane() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.DECPASSENGERSPLANE);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void incTotalNumberPassengersTransported() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.INCTOTALNUMBERPASSENGERSTRANSPORTED);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void decTotalNumberPassengersTransported() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.DECTOTALNUMBERPASSENGERSTRANSPORTED);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public int getTotalNumberPassengersTransported() {
    	Message nm = new Message();
		nm.setMessageType(MessageType.GETTOTALNUMBERPASSENGERSTRANSPORTED);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);    	
    	nm = (Message) cc.readObject();

		return nm.getIntVal1();
    }
    
    public int getQueueCount() {
    	return 1;
    }
    
    public void setPilotState(States pilotState) {
    	Message nm = new Message();
		nm.setMessageType(MessageType.SETPILOTSTATE);
		nm.setEntityState(pilotState);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void setHostessState(States hostessState) {
    	Message nm = new Message();
		nm.setMessageType(MessageType.SETHOSTESSSTATE);
		nm.setEntityState(hostessState);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    public void setPassengerState(int id, States passengerState, boolean export) {
    	Message nm = new Message();
		nm.setMessageType(MessageType.SETPASSENGERSTATE);
		nm.setIntVal1(id);
		nm.setEntityState(passengerState);
		nm.setBoolVal1(export);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);
		cc.readObject();
    }
    
    /**
	 * Informs the Repository will exited.
     */
	public void iStopped(){
		Message nm = new Message();

		nm.setMessageType(MessageType.ISTOPPED);

		ClientComm cc = new ClientComm(serverHostName,serverPort);		
		cc.open(); 
		cc.writeObject(nm);

		nm = (Message) cc.readObject(); 	
	}
}
