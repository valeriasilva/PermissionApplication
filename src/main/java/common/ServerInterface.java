package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

import common.model.Feature;
import common.model.Plugin;
import common.model.User;

/**
 * Define a interface remota do servidor
 */
public interface ServerInterface extends Remote {

	/** nome para referenciar remotamente o servidor */
	String REFERENCE_NAME = "Server";

	int RMI_PORT = 1099;

	/**
	 * Obtém todos os usuários do sistema.
	 * 
	 * @return lista com os usuários
	 *             .
	 */
	List<User> getUsers() throws ServiceException, RemoteException;

	/**
	 * Métodos referentes às Features
	 * 
	 * @throws RemoteException
	 *             .
	 */
	void saveFeature(final Feature feature) throws ServiceException, RemoteException;

	void updateFeature(final Feature feature) throws ServiceException, RemoteException;

	void deleteFeature(final long id) throws ServiceException, RemoteException;

	List<Feature> findFeatureByName(final String name) throws ServiceException, RemoteException;

	List<Feature> findAllFeatures() throws ServiceException, RemoteException;

	List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServiceException, RemoteException;

	/**
	 * Métodos referentes aos Plugins
	 * 
	 * @throws RemoteException
	 *             .
	 */
	void savePlugin(final Plugin plugin) throws ServiceException, RemoteException;

	void updatePlugin(final Plugin plugin) throws ServiceException, RemoteException;

	void deletePlugin(final long id) throws ServiceException, RemoteException;

	List<Plugin> findPlugins() throws ServiceException, RemoteException;

	Plugin findPluginById(final Long idPlugin) throws ServiceException, RemoteException;

	List<Plugin> findPluginByName(final String name) throws ServiceException, RemoteException;

	List<User> getUsersByName(final String name) throws ServiceException, RemoteException;

	/**
	 * Métodos referentes às Permissões
	 * 
	 * @throws RemoteException
	 *             .
	 */

	List<Feature> findFeatureUserHasNoPermission(Long userId) throws ServiceException, RemoteException;

	List<Feature> findFeaturesPermittedFor(Long userId) throws ServiceException, RemoteException;

	List<Feature> findFeatureNotPermittedByName(String name, Long userId) throws ServiceException, RemoteException;

	void deletePermission(long Featureid, long UserId) throws ServiceException, RemoteException;

	boolean verifyExistingPermission(Long userId, Long featureId) throws ServiceException, RemoteException;

	void savePermission(Long userId, Long featureId) throws ServiceException, RemoteException;
}