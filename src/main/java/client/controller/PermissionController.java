package client.controller;


import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.service.ServiceException;
import static client.util.Util.showMessage;;

public class PermissionController {

	public void save(final Long userId, final Long featureId)
			throws ParseException {

		try {
			try {
				// Verificar se o usuário já tem permissão para a funcionalidade selecionada
				if (ServiceLocator.getServer().verifyExistingPermission(userId, featureId)) {
					showMessage("O usuário já tem permissão para usar esta Funcionalidade");
				} else {
					ServiceLocator.getServer().savePermission(userId, featureId);
				}
			} catch (RemoteException e) {
				showMessage("Problemas na conexão remota com o servidor. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de atribuir permissão.\n"+ e.getMessage());
		} 
	}

	public void delete(final long FeatureId, final Long UserId){
		try {
			try {
				ServiceLocator.getServer().deletePermission(FeatureId, UserId);
			} catch (RemoteException e) {
				showMessage("Problemas na conexão remota com o servidor. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas na tentativa de excluir permissão. \n"+ e.getMessage());
		} 
	}

	public List<Feature> listFeaturesPermittedFor(final Long userId) {
		try {
			try {
				return ServiceLocator.getServer().findFeaturesPermittedFor(userId);
			} catch (RemoteException e) {
				showMessage("Problemas na conexão remota com o servidor. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas ao listar funcionalidades permitidas para o usuário selecionado.\n"+ e.getMessage());
		} 
		return null;
	}

	public List<Feature> listFeaturesNotPermittedFor(final Long userId) {
		try {
			try {
				return ServiceLocator.getServer().findFeatureUserHasNoPermission(userId);
			} catch (RemoteException e) {
				showMessage("Problemas na conexão remota com o servidor. \n"+ e.getMessage());
			}
		} catch (final ServiceException e) {
			showMessage("Problemas ao listar funcionalidades não permitidas para o usuário selecionado. \n"+ e.getMessage());
		}
		return null;
	}
}
