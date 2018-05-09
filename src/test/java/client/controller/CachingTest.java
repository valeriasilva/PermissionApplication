package client.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import client.service.ServiceLocator;
import common.model.User;
import common.service.ServiceException;
import common.util.Caching;
import server.dao.ServerException;
import server.dao.UserDAO;

public class CachingTest {

	private Caching<User> c;
	private UserDAO uDao;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() throws ServerException {
		setuDao(new UserDAO());
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void restoreStreams() {
	    System.setOut(System.out);
	    System.setErr(System.err);
	}

	@Ignore
	public void insertElements() throws InterruptedException{
		c = new Caching<>();
		User u = new User();
		u.setFullname("Valéria");
		
		Thread t1 = new Thread() {
			public void run() {
				for(int i = 0; i<1000; i++) {
					c.cacheElement(u);
					System.out.println("Algum nome"+ i);
				}
			}
		};
	}

	@Ignore
	public void shouldExistElements() throws NotBoundException {
		User u = new User();
		u.setFullname("Valéria");
		
		Thread t3 = new Thread() {
			public void run() {
				for(int i = 0; i<3000; i++) {
					Assert.assertNotNull(c.getElement("Algum nome"+ i));
				}
			}
		};
	}
	
	@Test
	public void shouldNotAllowAsyncAccess() {
		
		Thread t1 = new Thread() {
			public void run() {
				try {
					shouldExistOneElement();
					sleep(5000);
				} catch (RemoteException | ServiceException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t2 = new Thread() {
			public void run() {
				try {
					shouldExistOneElement();
				} catch (RemoteException | ServiceException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread t3 = new Thread() {
			public void run() {
				try {
					shouldExistOneElement();
				} catch (RemoteException | ServiceException e) {
					e.printStackTrace();
				}
			}
		};
		
		t1.start();
		t2.start();
		t3.start();
	}
	
	@Test
	public void shouldExistOneElement() throws RemoteException, ServiceException {
		System.out.println("Teste: Verificar se há o elemento na cache e Inserir caso negativo");
		User u = ServiceLocator.getService().getUserByName("Valeria Martins");

		Assert.assertEquals(u.getFullname(), "Valeria Martins");
	}
	

	public UserDAO getuDao() {
		return uDao;
	}

	public void setuDao(UserDAO uDao) {
		this.uDao = uDao;
	}
}
