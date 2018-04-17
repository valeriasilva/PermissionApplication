package client.service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.service.ServerInterface;

public class ServiceLocator {

	private static ServerInterface server;

	public static ServerInterface getServer() throws RemoteException{
		if (ServiceLocator.server == null) {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", ServerInterface.RMI_PORT);
				ServiceLocator.server = (ServerInterface) registry.lookup(ServerInterface.REFERENCE_NAME);

			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ServiceLocator.server;
	}

}
