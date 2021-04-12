package sharedRegions;

public class Plane {
	
	private Repository repo;
	
	public Plane(Repository repo) {
		this.repo = repo;
	}
	
	// passenger
	public synchronized void boardThePlane() {
			
	}
	
	// passenger
	public synchronized void waitForEndOfFlight() {
			
	}
}
