package client.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import client.service.ServiceLocator;
import static client.util.Util.showMessage;
import common.model.User;
import common.service.ServiceException;

public class UserController {

	public List<User> listUsers() {

		List<User> listOfUsers = new ArrayList<User>();
		
		try {
			listOfUsers = ServiceLocator.getServer().getUsers();
			return listOfUsers;
		} catch (RemoteException e) {
			showMessage("Problemas ao conectar com o servidor." +e.getMessage());
			System.exit(0);
		}catch (final ServiceException e) {
			showMessage("Problemas ao listar usuários." +e.getMessage());
		}
		return listOfUsers;
	}

	public List<User> listUsersByName(final String name) {

		List<User> listOfUsers = new ArrayList<User>();
		
		try {
			try {
				listOfUsers =  ServiceLocator.getServer().getUsersByName(name);
				return listOfUsers;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor." +e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas ao listar usuários pelo nome" +e.getMessage());
		}
		return listOfUsers;
	}
}