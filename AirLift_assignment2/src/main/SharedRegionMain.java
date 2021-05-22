package main;
import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;

import common.*;
import proxies.*;
import sharedRegions.*;
import stubs.RepositoryStub;

/**
 * Launch shared region server
 */
public class SharedRegionMain {
	
	/**
	* Shared regions main's thread
	* @param args shared region to be launched
 	* @throws SocketTimeoutException if the socket timeouts
 	* @throws ClassNotFoundException if the class isn't found
 	* @throws NoSuchMethodException if the method doesn't exist
	* @throws NoSuchFieldException if the field doesn't exist
	* @throws InstantiationException instantiation exception
	* @throws IllegalAccessException illegal access exception
	* @throws InvocationTargetException invocation target exception
	*/
	public static void main(String[] args) throws SocketTimeoutException, ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, InvocationTargetException {		
		ServerComm serverCom, serverConn;
		ServiceProvider serviceProvider; // service provider agent to address the request of service
		String regionName = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
		
		Class<?> shRegClass = Class.forName("sharedRegions." + regionName);
		Constructor<?> shRegConstructor = shRegClass.getConstructor(RepositoryStub.class);
		SharedRegion sharedRegion = (SharedRegion) shRegConstructor.newInstance(new RepositoryStub());
		
		Class<?> proxyClass = Class.forName("proxies." + regionName + "Proxy");
		Constructor<?> proxyConstructor = proxyClass.getConstructor(shRegClass);
		SharedRegionProxy proxy = (SharedRegionProxy) proxyConstructor.newInstance(sharedRegion);

		serverCom = new ServerComm(Parameters.class.getField(args[0].substring(0,1).toLowerCase()+args[0].substring(1) + "Port").getInt(null),1000);
		serverCom.start();

		while(proxy.getSimStatus()) {
			try {
				serverConn = serverCom.accept();
				serviceProvider = new ServiceProvider(proxy, serverConn);
				serviceProvider.start();
				serviceProvider.join();
			} catch (SocketTimeoutException e) {} 
			  catch (InterruptedException e) {}
		}
		System.out.printf("%s: Bye!\n",regionName);
	}

}
