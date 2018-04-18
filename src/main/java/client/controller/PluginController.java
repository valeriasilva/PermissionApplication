package client.controller;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.model.Plugin;
import common.service.ServiceException;

public class PluginController extends Controller {

	private final FeatureController featureController;

	public PluginController() {
		super();
		featureController = new FeatureController(null);
	}

	public void save(final String name, final String description) {
		final Plugin plugin = new Plugin();
		plugin.setName(name);
		plugin.setDescription(description);
		try {
			ServiceLocator.getServer().savePlugin(plugin);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de salvar plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public void update(final long id, final String name, final String description) {

		final Plugin plugin = new Plugin();
		plugin.setId(id);
		plugin.setName(name);
		plugin.setDescription(description);

		try {
			ServiceLocator.getServer().updatePlugin(plugin);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de atualizar plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public List<Plugin> listPlugins() {
		try {
			return ServiceLocator.getServer().findPlugins();
		} catch (final ServiceException e1) {
			showError(null, "Problemas na tentativa de listar plugins. \n" + e1.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public void delete(final long id) {
		try {
			ServiceLocator.getServer().deletePlugin(id);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de excluir plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public List<Plugin> searchPluginByName(final String name) {
		try {
			return ServiceLocator.getServer().findPluginByName(name);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de buscar plugin por nome. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public Plugin findPluginById(final Long id) {
		try {
			return ServiceLocator.getServer().findPluginById(id);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de buscar plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return null;
	}

	public List<Feature> getFeaturesByPlugin(final Long pluginId) {
		return featureController.getFeaturesByPlugin(pluginId);
	}

}