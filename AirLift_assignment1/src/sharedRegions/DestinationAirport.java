package sharedRegions;

public class DestinationAirport {
	
	private Repository repo;
	
	public DestinationAirport(Repository repo) {
		this.repo = repo;
	}
	
	// passenger
	public synchronized void leaveThePlane() {
			
	}
}
