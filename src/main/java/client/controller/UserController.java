package client.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import client.Client;
import common.ServiceException;
import common.model.User;

public class UserController {

	public List<User> listUsers() {

		List<User> listOfUsers = new ArrayList<User>();
		
		try {
			listOfUsers = Client.getServer().getUsers();
			return listOfUsers;
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null,
					"Problemas ao conectar com o servidor." +
							e.getMessage());
			System.exit(0);
		}catch (final ServiceException e) {
			JOptionPane.showMessageDialog(null,
					"Problemas ao localizar usuários na base de dados" +
							e.getMessage());
		}
		return listOfUsers;
	}

	public List<User> listUsersByName(final String name) {

		List<User> listOfUsers = new ArrayList<User>();
		
		try {
			try {
				listOfUsers =  Client.getServer().getUsersByName(name);
				return listOfUsers;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			JOptionPane.showMessageDialog(null,
					"Problemas ao localizar usuários" +
							e.getLocalizedMessage());
		}
		return null;
	}
}