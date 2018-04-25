package client.controller;

import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.model.Plugin;
import common.service.ServiceException;

public class FeatureController extends Controller {

	private final Component parentComponent;

	public FeatureController(final Component parentComponent) {
		super();
		this.parentComponent = parentComponent;
	}

	public void save(final String name, final String description, Plugin plugin) {

		final Feature feature = new Feature();
		feature.setName(name);
		feature.setDescription(description);
		feature.getPlugin().setId(plugin.getId());

		try {
			ServiceLocator.getService().saveFeature(feature);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de adicionar funcionalidade. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
	}

	public void update(Feature feature) {
		try {
			ServiceLocator.getService().updateFeature(feature);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de atualizar funcionalidade. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
	}

	public List<Feature> getAllFeatures() {
		try {
			return ServiceLocator.getService().findAllFeatures();
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de listar funcionalidades. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public void delete(final long id) {
		try {
			ServiceLocator.getService().deleteFeature(id);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de excluir funcionalidade. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
	}

	public List<Feature> searchFeatureNotPermittedByName(final String name, final Long userId) {
		try {
			return ServiceLocator.getService().findFeatureNotPermittedByName(name, userId);
		} catch (final ServiceException e) {
			showError(parentComponent,
					"Problemas na tentativa de buscar funcionalidades permitidas. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public List<Feature> getFeaturesByPlugin(final Long idPlugin) {
		try {
			return ServiceLocator.getService().findFeaturesByPlugin(idPlugin);
		} catch (final ServiceException e) {
			showError(parentComponent,
					"Problemas na tentativa de buscar funcionalidades por plugin. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}

		return Collections.emptyList();
	}

	public List<Feature> listFeaturesSelectedUserHasNoPermission(final Long userId) {
		try {
			return ServiceLocator.getService().findFeatureUserHasNoPermission(userId);
		} catch (final ServiceException e) {
			showError(parentComponent,
					"Problemas na tentativa de listar funcionalidades sem permissão atribuída. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas ao conectar com o servidor remoto. \n" + e.getMessage());
		}
		return Collections.emptyList();
	}

	public void showInfo(String msg) {
		showInfo(parentComponent, msg);
	}

}