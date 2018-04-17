package client.controller;

import static client.util.Util.showMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.model.Plugin;
import common.service.ServiceException;

public class PluginController {

	FeatureController featureController;
	List<Plugin> plugins = new ArrayList<Plugin>();		

	public void save(final String name, final String description) {
		final Plugin plugin = new Plugin();
		plugin.setName(name);
		plugin.setDescription(description);

		try {
			try {
				ServiceLocator.getServer().savePlugin(plugin);
			} catch (RemoteException e) {
				showMessage("Problemas na conexão remota com o servidor. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de salvar plugin. \n"+ e.getMessage());
		}
	}

	public void update(final long id, final String name, final String description) {

		final Plugin plugin = new Plugin();
		plugin.setId(id);
		plugin.setName(name);
		plugin.setDescription(description);

		try {
			try {
				ServiceLocator.getServer().updatePlugin(plugin);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de atualizar plugin. \n"+ e.getMessage());
		} 
	}

	public List<Plugin> listPlugins() {
		plugins = new ArrayList<Plugin>();		
		try {
			try {
				plugins = ServiceLocator.getServer().findPlugins();
				return plugins;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e1) {
			showMessage("Problemas na tentativa de listar plugins. \n"+ e1.getMessage());
		}
		return plugins;
	}

	public void delete(final long id) {
		try {
			try {
				ServiceLocator.getServer().deletePlugin(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de excluir plugin. \n"+ e.getMessage());
		}
	}

	public List<Plugin> searchPluginByName(final String name) {
		plugins = new ArrayList<Plugin>();		

		try {
			try {
				plugins = ServiceLocator.getServer().findPluginByName(name);
				return plugins;
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de buscar plugin por nome. \n"+ e.getMessage());
		} 
		return plugins;
	}

	public static Plugin findPluginById(final Long id) {
		try {
			try {
				return ServiceLocator.getServer().findPluginById(id);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de buscar plugin. \n"+ e.getMessage());
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