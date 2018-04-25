package common.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.model.Feature;
import common.model.Plugin;
import common.model.User;

public interface PermissionServiceInterface extends Remote {

	/** nome para referenciar remotamente o servidor */
	String REFERENCE_NAME = "Server";

	int RMI_PORT = 1099;

	/**
	 * Obtém todos os usuários do sistema.
	 * 
	 * @return lista com os usuários .
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

	List<User> getUsersByName(final String name) throws ServiceException, RemoteException;

	/**
	 * Métodos referentes às Permissões
	 */
	List<Feature> findFeatureUserHasNoPermission(Long userId) throws ServiceException, RemoteException;

	List<Feature> getPermissionsByUser(User user) throws ServiceException, RemoteException;

	List<Feature> findFeatureNotPermittedByName(String name, Long userId) throws ServiceException, RemoteException;

	void deletePermission(long Featureid, long UserId) throws ServiceException, RemoteException;

	boolean verifyExistingPermission(Long userId, Long featureId) throws ServiceException, RemoteException;

	void savePermission(Long userId, Long featureId) throws ServiceException, RemoteException;

	/**
	 * Método que simula a geração de um relatório que demanda muito tempo para
	 * processamento.
	 * 
	 * @throws ServiceException
	 *             Exceção que pode ser levantada pelo servidor em caso de problemas
	 *             na execução da tarefa.
	 * @throws RemoteException
	 *             Exceção ocorrida por causa de algum problema de comunicação ou
	 *             situação não tratada adequadamente.
	 */
	void generateReport() throws ServiceException, RemoteException;
}