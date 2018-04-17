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
import common.model.Feature;
import common.model.Plugin;

public class PluginController {

	FeatureController featureController;

	public void save(final String name, final String description)
			throws SQLException, ParseException {
		final Plugin plugin = new Plugin();
		plugin.setName(name);
		plugin.setDescription(description);

		try {
			try {
				Client.getServer().savePlugin(plugin);
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

		final Plugin plugin = new Plugin();
		plugin.setId(id);
		plugin.setName(name);
		plugin.setDescription(description);

		try {
			try {
				Client.getServer().updatePlugin(plugin);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public List<Plugin> listPlugins() {

		try {
			try {
				return Client.getServer().findPlugins();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public void delete(final long id) throws SQLException {
		try {
			try {
				Client.getServer().deletePlugin(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Plugin> searchPluginByName(final String name) throws SQLException {
		try {
			try {
				return Client.getServer().findPluginByName(name);
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

	public static Plugin findPluginById(final Long id) {
		try {
			try {
				return Client.getServer().findPluginById(id);
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

	public List<Feature> getListOfFeaturesByPlugin(final Long idPlugin) {
		List<Feature> features = new ArrayList<Feature>();
		featureController = new FeatureController();
		features = featureController.getFeaturesByPlugin(idPlugin);

		return features;
	}

}