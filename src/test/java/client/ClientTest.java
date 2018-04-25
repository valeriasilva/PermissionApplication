package client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.Assert;
import org.junit.Test;

import client.service.ServiceLocator;

public class ClientTest {

	@Test
	public void testConnectionToRMIServer() throws NotBoundException {
		System.out.println("Teste: conexão Servidor RMI. Retorna falha caso o servidor não esteja executando.");
		
		try {
			Assert.assertNotNull(ServiceLocator.getService());
		} catch (RemoteException e) {
			System.out.println("Não foi possível estabelecer conexão com o servidor RMI "+ e.getStackTrace());
		}
	}
}
