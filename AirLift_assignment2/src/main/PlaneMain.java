package main;

import java.net.SocketTimeoutException;

import common.Parameters;
import common.ServerComm;
import common.ServiceProvider;
import proxies.PlaneProxy;
import sharedRegions.Plane;
import stubs.RepositoryStub;

public class PlaneMain {

	public static void main(String[] args) {
		ServerComm serverComm, serverConn;
		ServiceProvider serviceProvider;
		
		Plane p = new Plane(new RepositoryStub());
		PlaneProxy proxy = new PlaneProxy(p);
		
		serverComm = new ServerComm(Parameters.planePort, 1000);
		serverComm.start();
		
		while(proxy.getSimStatus()) {
			try {
				serverConn = serverComm.accept();
				serviceProvider = new ServiceProvider(proxy, serverConn);
				serviceProvider.start();
			} catch(SocketTimeoutException e) {
			}
			
		}
		System.out.printf("Bye!\n");
	}

}
