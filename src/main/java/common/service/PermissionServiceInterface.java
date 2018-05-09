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

	int RMI_PORT = 1098;


	/**
	 * Método que retorna todos os Usuários existentes.
	 * 
	 * @return
	 * 			Retorna uma lista com todos os Usuários existentes.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<User> getUsers() throws ServiceException, RemoteException;

	/**
	 * Método que salva uma Funcionalidade.
	 * 
	 * @param feature
	 * 			Objeto do tipo Funcionalidade
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void saveFeature(final Feature feature) throws ServiceException, RemoteException;
	
	/**
	 * Método que atualiza dados de uma Funcionalidade específica.
	 * 
	 * @param feature
	 * 			Objeto do tipo Funcionalidade
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void updateFeature(final Feature feature) throws ServiceException, RemoteException;

	/**
	 * Método que remove uma Funcionalidade específica.
	 * 
	 * @param id
	 * 			ID da funcionalidade.
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void deleteFeature(final long id) throws ServiceException, RemoteException;

	/**
	 * Método que busca por todas as Funcionalidades existentes.
	 * 
	 * @return
	 * 			Retorna uma lista de todas as funcionalidades.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<Feature> findAllFeatures() throws ServiceException, RemoteException;

	/**
	 * Método que busca Funcionalidades a partir do Plugin as quais são pertencentes.
	 * 
	 * @param idPlugin
	 * 			ID do Plugin
	 * @return
	 * 			Retorna uma lista de Funcionalidades pertencentes a determinado Plugin.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServiceException, RemoteException;


	/**
	 * Método que salva um Plugin.
	 * 
	 * @param plugin
	 * 			Objeto do tipo Plugin.
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void savePlugin(final Plugin plugin) throws ServiceException, RemoteException;

	/**
	 * Método que atualiza os dados de um Plugin.
	 * 
	 * @param plugin
	 * 			Objeto do tipo Plugin
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void updatePlugin(final Plugin plugin) throws ServiceException, RemoteException;

	/**
	 * Método que remove um Plugin.
	 * 
	 * @param id
	 * 			ID do Plugin
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void deletePlugin(final long id) throws ServiceException, RemoteException;

	/**
	 * Método que busca por todos os Plugins existentes.
	 * 
	 * @return
	 * 			Retorna uma lista contendo todos os Plugins existentes.
	 * 
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	List<Plugin> findPlugins() throws ServiceException, RemoteException;

	/**
	 * Método que faz a busca de determinado Plugin pelo seu ID.
	 * 
	 * @param idPlugin
	 * 			ID do Plugin
	 * @return
	 * 			Retorna um objeto Plugin.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	Plugin findPluginById(final Long idPlugin) throws ServiceException, RemoteException;

	/**
	 * Método que retorna Usuários a partir do nome.
	 * 
	 * @param name
	 * 			Nome do usuário
	 * @return
	 * 			Retorna uma lista de usuários 
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	User getUserByName(final String name) throws ServiceException, RemoteException;


	/**
	 * Método que faz a busca de todas as Funcionalidades que 
	 * determinado Usuário não possui Permissão.
	 * 
	 * @param userId
	 * 			ID do usuário
	 * @return
	 * 			Retorna uma lista de funcionalidades as quais o usuário não possui permissão.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<Feature> findFeatureUserHasNoPermission(Long userId) throws ServiceException, RemoteException;

	/**
	 * Método que retorna as Permissões de um Usuário específico.
	 * 
	 * @param user
	 * 			Objeto do tipo usuário
	 * @return
	 * 			Retorna uma lista de funcionalidades permitidas para o usuário declarado.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<Feature> getPermissionsByUser(User user) throws ServiceException, RemoteException;

	/**
	 * Método que faz a busca pelo nome de Funcionalidades 
	 * as quais determinado Usuário não possui Permissão.
	 * 
	 * @param name
	 * 			Nome da funcionalidade
	 * @param userId
	 * 			ID do usuário
	 * @return
	 * 			Retorna uma lista de funcionalidades as quais o usuário não possui permissão.
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	List<Feature> findFeatureNotPermittedByName(String name, Long userId) throws ServiceException, RemoteException;

	/**
	 * Método que remove a Permissão de um Usuário 
	 * referente a uma Funcionalidade específica.
	 * 
	 * @param Featureid
	 * 			ID da funcionalidade
	 * @param UserId
	 * 			ID do usuário
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	void deletePermission(long Featureid, long UserId) throws ServiceException, RemoteException;

	/**
	 * Método que verifica se determinado Usuário já possui Permissão 
	 * em relação a uma funcionalidade específica.
	 * 
	 * @param userId
	 * 			ID do usuário
	 * @param featureId
	 * 			ID da funcionalidade
	 * @return
	 * 			Retorna um boolean indicando a existência (ou não) da permissão de uma funcionalidade para um usuário específico
	 * 
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
	boolean verifyExistingPermission(Long userId, Long featureId) throws ServiceException, RemoteException;

	/**
	 * Método que salva a Permissão de um Usuário referente 
	 * a uma Funcionalidade específica.
	 * 
	 * @param userId
	 * 			ID do usuário
	 * @param featureId
	 * 			ID da funcionalidade
	 * @throws ServiceException
	 * 			Exceção que pode ser levantada pelo servidor em caso de problemas
	 *          na execução da tarefa.
	 * @throws RemoteException
	 * 			Exceção ocorrida por causa de algum problema de comunicação ou
	 *          situação não tratada adequadamente.
	 */
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