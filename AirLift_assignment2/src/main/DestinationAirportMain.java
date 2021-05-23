package main;

import java.net.SocketTimeoutException;

import common.Parameters;
import common.ServerComm;
import common.ServiceProvider;
import proxies.DestinationAirportProxy;
import sharedRegions.DestinationAirport;
import stubs.RepositoryStub;

public class DestinationAirportMain {

	public static void main(String[] args) {
		ServerComm serverComm, serverConn;
		ServiceProvider serviceProvider;
		
		DestinationAirport destination = new DestinationAirport(new RepositoryStub());
		DestinationAirportProxy proxy = new DestinationAirportProxy(destination);
		
		serverComm = new ServerComm(Parameters.destinationAirportPort, 1000);
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
