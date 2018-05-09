package client.controller;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.service.ServiceLocator;
import common.model.User;
import common.service.ServiceException;
import common.util.Caching;

public class UserController extends Controller {

	private Component parentComponent;

	public UserController(final Component parentComponent) {
		super();
		this.parentComponent = parentComponent;
	}

	public UserController() {
		this(null);
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

	public User listUserByName(final String name) {
		try {
			return ServiceLocator.getService().getUserByName(name);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas ao listar usuários pelo nome" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor." + e.getMessage());
		}
		return null;
	}

}