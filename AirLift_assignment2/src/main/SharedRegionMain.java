package main;
import java.lang.Class;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketTimeoutException;

import common.ServerComm;

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
		// TODO Auto-generated method stub
		ServerComm serverCom, serverConn;
		ServiceProvider serviceProvider;
		String regionName = args[0].substring(0, 1).toUpperCase() + args[0].substring(1);
		
		

	}

}
