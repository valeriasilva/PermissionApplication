package client.controller;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import client.service.ServiceLocator;
import common.model.User;
import common.service.ServiceException;

public class UserController extends Controller {

	private Component parentComponent;

	public UserController(final Component parentComponent) {
		super();
		this.parentComponent = parentComponent;
	}

	public List<User> listUsers() {

		List<User> listOfUsers = new ArrayList<User>();

		try {
			listOfUsers = ServiceLocator.getService().getUsers();
			return listOfUsers;
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor." + e.getMessage());
			System.exit(0);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas ao listar usuários." + e.getMessage());
		}
		return listOfUsers;
	}

	public List<User> listUsersByName(final String name) {

		List<User> listOfUsers = new ArrayList<User>();

		try {
			listOfUsers = ServiceLocator.getService().getUsersByName(name);
			return listOfUsers;
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas ao listar usuários pelo nome" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor." + e.getMessage());
		}
		return listOfUsers;
	}
}