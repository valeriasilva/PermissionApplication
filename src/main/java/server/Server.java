package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import common.model.Feature;
import common.model.File;
import common.model.Plugin;
import common.model.User;
import common.service.PermissionServiceInterface;
import common.service.ServiceException;
import server.dao.FeatureDAO;
import server.dao.PermissionDAO;
import server.dao.PluginDAO;
import server.dao.ServerException;
import server.dao.UserDAO;

public class Server implements PermissionServiceInterface {

	PluginDAO pluginDao;
	FeatureDAO featureDao;
	PermissionDAO permissionDao;

	public static void main(final String args[]) {
		try {
			final PermissionServiceInterface server = new Server();
			UnicastRemoteObject.exportObject(server, PermissionServiceInterface.RMI_PORT);

			final Registry registry = LocateRegistry.createRegistry(PermissionServiceInterface.RMI_PORT);
			registry.rebind(PermissionServiceInterface.REFERENCE_NAME, server);

			System.out.println("Server ready");
		} catch (final Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Métodos referentes aos Usuários
	 */
	@Override
	public List<User> getUsers() throws ServiceException {
		try {
			return new UserDAO().findUsers();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao buscar usuários. " + e.getMessage());
		}
	}

	@Override
	public User getUserByName(final String name) throws ServiceException {
		UserDAO userDao;
		try {
			userDao = new UserDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return userDao.findUsersByName(name);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao buscar usuários. " + e.getMessage());
		}
	}

	/**
	 * Métodos referentes às Funcionalidades
	 * 
	 * @throws RemoteException
	 *             .
	 */
	@Override
	public void saveFeature(final Feature feature) throws ServiceException {
		try {
			featureDao = new FeatureDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			featureDao.saveFeature(feature);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void updateFeature(final Feature feature) throws ServiceException {
		try {
			featureDao = new FeatureDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			featureDao.updateFeature(feature);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void deleteFeature(final long id) throws ServiceException {
		try {
			featureDao = new FeatureDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			featureDao.deleteFeature(id);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findAllFeatures() throws ServiceException {
		try {
			featureDao = new FeatureDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return featureDao.findAllFeatures();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServiceException {
		try {
			featureDao = new FeatureDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return featureDao.findFeaturesByPlugin(idPlugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	/**
	 * Métodos referentes aos Plugins
	 * 
	 * @throws RemoteException
	 *             .
	 */
	@Override
	public void savePlugin(final Plugin plugin) throws ServiceException {
		try {
			pluginDao = new PluginDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			pluginDao.savePlugin(plugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}

	}

	@Override
	public void updatePlugin(final Plugin plugin) throws ServiceException {
		try {
			pluginDao = new PluginDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			pluginDao.updatePlugin(plugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}

	}

	@Override
	public void deletePlugin(final long id) throws ServiceException {
		try {
			pluginDao = new PluginDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			pluginDao.deletePlugin(id);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Plugin> findPlugins() throws ServiceException {
		try {
			pluginDao = new PluginDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return pluginDao.findPlugins();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public Plugin findPluginById(final Long idPlugin) throws ServiceException {
		try {
			pluginDao = new PluginDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return pluginDao.findPluginById(idPlugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	/**
	 * Métodos referentes às Permissões
	 * 
	 * @throws RemoteException
	 *             .
	 */
	@Override
	public List<Feature> getPermissionsByUser(final User user) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return permissionDao.findFeaturesPermittedForUser(user);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeatureUserHasNoPermission(final Long userId) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return permissionDao.findFeaturesNotPermittedFor(userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeatureNotPermittedByName(final String name, final Long userId) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return permissionDao.findFeaturesNotPermittedByName(name, userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void deletePermission(final long featureid, final long userId) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			permissionDao.deletePermission(featureid, userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao excluir permissão" + e.getMessage());
		}

	}

	@Override
	public boolean verifyExistingPermission(final Long userId, final Long featureId) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			return permissionDao.IsTherePermissionByFeatureAndUser(userId, featureId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void savePermission(final Long userId, final Long featureId) throws ServiceException {
		try {
			permissionDao = new PermissionDAO();
		} catch (ServerException e1) {
			throw new ServiceException(
					"Não foi possível estabelecer conexão com a base de dados. \n" + e1.getMessage());
		}
		try {
			permissionDao.savePermission(userId, featureId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao salvar permissão. " + e.getMessage());
		}
	}

	@Override
	public void generateReport() throws ServiceException, RemoteException {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			throw new ServiceException("Falha na geração do relatório");
		}
	}

	@Override
	public List<File> getFileByNamePart(String fileNamePart) throws ServiceException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getFileByName(String fileName) throws ServiceException, RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
