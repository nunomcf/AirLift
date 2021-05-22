package main;

import java.net.SocketTimeoutException;

import common.Parameters;
import common.ServerComm;
import common.ServiceProvider;
import proxies.DepartureAirportProxy;
import sharedRegions.DepartureAirport;
import stubs.RepositoryStub;

public class DepartureAirportMain {

	public static void main(String[] args) {
		ServerComm serverComm, serverConn;
		ServiceProvider serviceProvider;
		
		DepartureAirport departure = new DepartureAirport(new RepositoryStub());
		DepartureAirportProxy proxy = new DepartureAirportProxy(departure);
		
		serverComm = new ServerComm(Parameters.departureAirportPort, 1000);
		serverComm.start();
		
		while(proxy.getSimStatus()) {
			try {
				serverConn = serverComm.accept();
				serviceProvider = new ServiceProvider(proxy, serverConn);
				serviceProvider.start();
			} catch(SocketTimeoutException e) {
			}
			
		}
		System.out.printf("%s: Bye!\n");
	}

}
