package client.controller;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.swing.JOptionPane;

import client.service.ServiceLocator;
import common.model.Feature;
import common.service.ServiceException;

public class FeatureController {

	public void save(final String name, final String description, final Long idPlugin)
			throws SQLException, ParseException {
		final Feature feature = new Feature();
		feature.setName(name);
		feature.setDescription(description);
		feature.getPlugin().setId(idPlugin);

		try {
			try {
				ServiceLocator.getServer().saveFeature(feature);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(final long id, final String name, final String description)
			throws ParseException, SQLException {

		final Feature feature = new Feature();
		feature.setId(id);
		feature.setName(name);
		feature.setDescription(description);

		try {
			try {
				ServiceLocator.getServer().updateFeature(feature);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public List<Feature> listFeatures() {
		try {

			try {
				return ServiceLocator.getServer().findAllFeatures();
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

	public void delete(final long id) throws SQLException {
		try {
			try {
				ServiceLocator.getServer().deleteFeature(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public List<Feature> searchFeatureByName(final String text) {
		try {
			try {
				return ServiceLocator.getServer().findFeatureByName(text);
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

	public List<Feature> searchFeatureNotPermittedByName(final String name, final Long userId) {
		try {
			try {
				return ServiceLocator.getServer().findFeatureNotPermittedByName(name, userId);
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

	public List<Feature> getFeaturesByPlugin(final Long idPlugin) {
		try {
			try {
				return ServiceLocator.getServer().findFeaturesByPlugin(idPlugin);
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

	public List<Feature> listFeaturesSelectedUserHasNoPermission(final Long userId) {
		try {
			try {
				return ServiceLocator.getServer().findFeatureUserHasNoPermission(userId);
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
}