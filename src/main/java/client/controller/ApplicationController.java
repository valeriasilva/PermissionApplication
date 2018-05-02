package client.controller;

import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import client.service.ServiceLocator;
import common.service.ServiceException;

public class ApplicationController extends Controller {

	public ApplicationController() {
		super();
	}

	public void generateReport() throws RemoteException, ServiceException {
		ServiceLocator.getService().generateReport();

	}
}
