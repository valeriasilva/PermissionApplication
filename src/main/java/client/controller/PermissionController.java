package client.controller;

import java.awt.HeadlessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import client.Client;
import common.ServiceException;
import common.model.Feature;

public class PermissionController {

	public void save(final Long userId, final Long featureId)
			throws ParseException {

		// Verificar se o usuário já tem permissão para a funcionalidade selecionada
		try {
			try {
				if (Client.getServer().verifyExistingPermission(userId, featureId)) {
					JOptionPane.showMessageDialog(null,
							"O usuário já tem permissão para usar esta Funcionalidade");
				} else {
					Client.getServer().savePermission(userId, featureId);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public void delete(final long FeatureId, final Long UserId){
		try {
			try {
				Client.getServer().deletePermission(FeatureId, UserId);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public List<Feature> listFeaturesPermittedFor(final Long userId) {
		try {
			try {
				return Client.getServer().findFeaturesPermittedFor(userId);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public List<Feature> listFeaturesNotPermittedFor(final Long userId) {
		try {
			try {
				return Client.getServer().findFeatureUserHasNoPermission(userId);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void showMessage(final String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
}
