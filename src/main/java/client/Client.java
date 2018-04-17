package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import common.ServerInterface;

public class Client {

	private static ServerInterface server;

	public static ServerInterface getServer() throws RemoteException{
		if (Client.server == null) {
			Registry registry;
			try {
				registry = LocateRegistry.getRegistry("localhost", ServerInterface.RMI_PORT);
				Client.server = (ServerInterface) registry.lookup(ServerInterface.REFERENCE_NAME);

			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Client.server;
	}

}
