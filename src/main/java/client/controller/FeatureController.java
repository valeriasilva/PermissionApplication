package client.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.service.ServiceException;

import static client.util.Util.showMessage;

public class FeatureController {
	
	List<Feature> featureList = new ArrayList<Feature>();

	public void save(final String name, final String description, final Long idPlugin){
		final Feature feature = new Feature();
		feature.setName(name);
		feature.setDescription(description);
		feature.getPlugin().setId(idPlugin);

		try {
			try {
				ServiceLocator.getServer().saveFeature(feature);
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de adicionar funcionalidade. \n"+ e.getMessage());
		}
	}

	public void update(final long id, final String name, final String description) {

		final Feature feature = new Feature();
		feature.setId(id);
		feature.setName(name);
		feature.setDescription(description);

		try {
			try {
				ServiceLocator.getServer().updateFeature(feature);
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de atualizar funcionalidade. \n"+ e.getMessage());
		} 
	}

	public List<Feature> listFeatures() {
		featureList = new ArrayList<Feature>();
		try {
			try {
				featureList = ServiceLocator.getServer().findAllFeatures();
				return featureList;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de listar funcionalidades. \n"+ e.getMessage());
		} 
		return featureList;
	}

	public void delete(final long id) {
		try {
			try {
				ServiceLocator.getServer().deleteFeature(id);
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de excluir funcionalidade. \n"+ e.getMessage());
		} 
	}

	public List<Feature> searchFeatureByName(final String text) {
		featureList = new ArrayList<Feature>();
		try {
			try {
				featureList = ServiceLocator.getServer().findFeatureByName(text);
				return featureList;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de buscar funcionalidades pelo nome. \n"+ e.getMessage());
		} 
		return featureList;
	}

	public List<Feature> searchFeatureNotPermittedByName(final String name, final Long userId) {
		featureList = new ArrayList<Feature>();
		try {
			try {
				featureList = ServiceLocator.getServer().findFeatureNotPermittedByName(name, userId);
				return featureList;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de buscar funcionalidades permitidas. \n"+ e.getMessage());
		}
		return featureList;
	}

	public List<Feature> getFeaturesByPlugin(final Long idPlugin) {
		featureList = new ArrayList<Feature>();
		try {
			try {
				featureList = ServiceLocator.getServer().findFeaturesByPlugin(idPlugin);
				return featureList;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de buscar funcionalidades por plugin. \n"+ e.getMessage());
		}

		return featureList;
	}

	public List<Feature> listFeaturesSelectedUserHasNoPermission(final Long userId) {
		featureList = new ArrayList<Feature>();
		try {
			try {
				featureList = ServiceLocator.getServer().findFeatureUserHasNoPermission(userId);
				return featureList;
			} catch (RemoteException e) {
				showMessage("Problemas ao conectar com o servidor remoto. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de listar funcionalidades sem permissão atribuída. \n"+ e.getMessage());
		}
		return featureList;
	}
}