package client.service;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.service.PermissionServiceInterface;

public class ServiceLocator {

	private static PermissionServiceInterface server;

	public static PermissionServiceInterface getService() throws RemoteException{
		if (ServiceLocator.server == null) {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", PermissionServiceInterface.RMI_PORT);
				ServiceLocator.server = (PermissionServiceInterface) registry.lookup(PermissionServiceInterface.REFERENCE_NAME);

			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ServiceLocator.server;
	}

}
