package main;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;

import common.Parameters;
import common.ServerComm;
import common.ServiceProvider;
import proxies.RepositoryProxy;
import sharedRegions.Repository;

public class RepositoryMain {

	public static void main(String[] args) throws FileNotFoundException, SocketTimeoutException {
		ServerComm serverComm, serverConn;
		ServiceProvider serviceProvider;
		
		Repository repo = new Repository();
		RepositoryProxy repoProxy = new RepositoryProxy(repo);
		
		serverComm = new ServerComm(Parameters.repositoryPort, 1000);
		serverComm.start();
		
		while(repoProxy.getSimStatus()) {
			try {
				serverConn = serverComm.accept();
				serviceProvider = new ServiceProvider(repoProxy, serverConn);
				serviceProvider.start();
			} catch(SocketTimeoutException e) {
			}
		}
		System.out.printf("Bye!\n");
	}

}
