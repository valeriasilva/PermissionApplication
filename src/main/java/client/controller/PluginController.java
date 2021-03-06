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
			ServiceLocator.getService().savePlugin(plugin);
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
			ServiceLocator.getService().updatePlugin(plugin);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de atualizar plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public List<Plugin> listPlugins() {
		try {
			return ServiceLocator.getService().findPlugins();
		} catch (final ServiceException e1) {
			showError(null, "Problemas na tentativa de listar plugins. \n" + e1.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public void delete(final long id) {
		try {
			ServiceLocator.getService().deletePlugin(id);
		} catch (final ServiceException e) {
			showError(null, "Problemas na tentativa de excluir plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(null, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public List<Feature> getFeaturesByPlugin(final Long pluginId) {
		return featureController.getFeaturesByPlugin(pluginId);
	}

}