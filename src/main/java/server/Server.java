package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import common.ServerInterface;
import common.ServiceException;
import common.model.Feature;
import common.model.Plugin;
import common.model.User;
import server.dao.FeatureDAO;
import server.dao.PermissionDAO;
import server.dao.PluginDAO;
import server.dao.UserDAO;

public class Server implements ServerInterface {

	PluginDAO pluginDao;
	FeatureDAO featureDao;
	PermissionDAO permissionDao;

	public static void main(final String args[]) {
		try {
			final ServerInterface server = new Server();
			UnicastRemoteObject.exportObject(server, ServerInterface.RMI_PORT);

			final Registry registry = LocateRegistry.createRegistry(ServerInterface.RMI_PORT);
			registry.rebind(ServerInterface.REFERENCE_NAME, server);

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
		final UserDAO userDao = new UserDAO();
		List<User> users = null;
		try {
			users = userDao.findUsers();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao buscar usuários. " + e.getMessage());
		}

		return users;
	}

	@Override
	public List<User> getUsersByName(final String name) throws ServiceException {
		final UserDAO userDao = new UserDAO();
		try {
			return userDao.findUsersByName(name);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao buscar usuários. " + e.getMessage());
		}
	}

	/**
	 * Métodos referentes as Funcionalidades
	 * 
	 * @throws RemoteException
	 *             .
	 */
	@Override
	public void saveFeature(final Feature feature) throws ServiceException {
		featureDao = new FeatureDAO();
		try {
			featureDao.saveFeature(feature);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void updateFeature(final Feature feature) throws ServiceException {
		featureDao = new FeatureDAO();
		try {
			featureDao.updateFeature(feature);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void deleteFeature(final long id) throws ServiceException {
		featureDao = new FeatureDAO();
		try {
			featureDao.deleteFeature(id);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeatureByName(final String name) throws ServiceException {
		featureDao = new FeatureDAO();
		try {
			return featureDao.findFeatureByName(name);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findAllFeatures() throws ServiceException {
		featureDao = new FeatureDAO();
		try {
			return featureDao.findAllFeatures();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServiceException {
		featureDao = new FeatureDAO();
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
		pluginDao = new PluginDAO();
		try {
			pluginDao.savePlugin(plugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}

	}

	@Override
	public void updatePlugin(final Plugin plugin) throws ServiceException {
		pluginDao = new PluginDAO();
		try {
			pluginDao.updatePlugin(plugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}

	}

	@Override
	public void deletePlugin(final long id) throws ServiceException {
		pluginDao = new PluginDAO();
		try {
			pluginDao.deletePlugin(id);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Plugin> findPlugins() throws ServiceException {
		pluginDao = new PluginDAO();
		try {
			return pluginDao.findPlugins();
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public Plugin findPluginById(final Long idPlugin) throws ServiceException {
		pluginDao = new PluginDAO();
		try {
			return pluginDao.findPluginById(idPlugin);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Plugin> findPluginByName(final String name) throws ServiceException {
		pluginDao = new PluginDAO();
		try {
			return pluginDao.findByName(name);
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
	public List<Feature> findFeaturesPermittedFor(final Long userId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			return permissionDao.findFeaturesPermittedForUser(userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeatureUserHasNoPermission(final Long userId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			return permissionDao.findFeaturesNotPermittedFor(userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public List<Feature> findFeatureNotPermittedByName(final String name, final Long userId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			return permissionDao.findFeaturesNotPermittedByName(name, userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void deletePermission(final long featureid, final long userId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			permissionDao.deletePermission(featureid, userId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao excluir permissão" + e.getMessage());
		}

	}

	@Override
	public boolean verifyExistingPermission(final Long userId, final Long featureId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			return permissionDao.IsTherePermissionByFeatureAndUser(userId, featureId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao pesquisar usuários. " + e.getMessage());
		}
	}

	@Override
	public void savePermission(final Long userId, final Long featureId) throws ServiceException {
		permissionDao = new PermissionDAO();
		try {
			permissionDao.savePermission(userId, featureId);
		} catch (ServerException e) {
			throw new ServiceException("Falha ao salvar permissão. " + e.getMessage());
		}
	}

}
