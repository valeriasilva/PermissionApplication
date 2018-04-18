package client.controller;

import java.awt.Component;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import client.service.ServiceLocator;
import common.model.Feature;
import common.service.ServiceException;;

public class PermissionController extends Controller {

	private final Component parentComponent;

	public PermissionController(Component parentComponent) {
		this.parentComponent = parentComponent;
	}

	public void save(final Long userId, final Long featureId) throws ParseException {

		try {
			// Verificar se o usuário já tem permissão para a funcionalidade selecionada
			if (ServiceLocator.getServer().verifyExistingPermission(userId, featureId)) {
				showInfo(parentComponent, "O usuário já tem permissão para usar esta Funcionalidade");
			} else {
				ServiceLocator.getServer().savePermission(userId, featureId);
			}
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de atribuir permissão.\n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public void delete(final long FeatureId, final Long UserId) {
		try {
			ServiceLocator.getServer().deletePermission(FeatureId, UserId);
		} catch (final ServiceException e) {
			showError(parentComponent, "Problemas na tentativa de excluir permissão. \n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
	}

	public List<Feature> listFeaturesPermittedFor(final Long userId) {
		try {
			return ServiceLocator.getServer().findFeaturesPermittedFor(userId);
		} catch (final ServiceException e) {
			showError(parentComponent,
					"Problemas ao listar funcionalidades permitidas para o usuário selecionado.\n" + e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return null;
	}

	public List<Feature> listFeaturesNotPermittedFor(final Long userId) {
		try {
			return ServiceLocator.getServer().findFeatureUserHasNoPermission(userId);
		} catch (final ServiceException e) {
			showError(parentComponent,
					"Problemas ao listar funcionalidades não permitidas para o usuário selecionado. \n"
							+ e.getMessage());
		} catch (RemoteException e) {
			showError(parentComponent, "Problemas na conexão remota com o servidor. \n" + e.getMessage());
		}
		return null;
	}

	public void showInfo(String msg) {
		showInfo(parentComponent, msg);
	}
}
