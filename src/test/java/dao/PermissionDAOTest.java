package dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.model.Feature;
import common.model.Plugin;
import common.model.User;
import server.dao.DBAccess;
import server.dao.FeatureDAO;
import server.dao.PermissionDAO;
import server.dao.PluginDAO;
import server.dao.ServerException;
import server.dao.UserDAO;

public class PermissionDAOTest {
	int userIdGenerated = 0;
	int featureIdGenerated = 0;
	int pluginIdGenarated = 0;
	int permissionIdGenerated = 0;

	@BeforeClass
	public static void testGettingConnection() {
		try {
			Assert.assertNotNull(DBAccess.createDBConnection());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void createsTestScenario() throws SQLException, ServerException {
		final User user = new User();
		final Feature feature = new Feature();
		final Plugin plugin = new Plugin();

		plugin.setName("Plugin test");
		plugin.setDescription("Description Plugin Teste");

		pluginIdGenarated = new PluginDAO().savePlugin(plugin);

		feature.setName("Feature Teste");
		feature.setDescription("Description Feature Teste");
		feature.getPlugin().setId((long) pluginIdGenarated);

		featureIdGenerated = new FeatureDAO().saveFeature(feature);

		user.setFullname("Users' Fullname teste");
		user.setCurrentManagement("Gerencia");
		user.setStatus(true);
		user.setLogin("user");

		userIdGenerated = new UserDAO().saveUser(user);
	}

	@Test
	public void shouldInsertPermissionInDataBase() throws SQLException {
		System.out.println("Teste: Insert de Permissão");

		insertPermissionInDataBase();

		Assert.assertTrue(permissionIdGenerated > 0);
	}

	@Test
	public void shouldSelectPermissionsFromUser() throws SQLException {
		System.out.println("Teste: Select de Permissões de determinado usuário");

		// Adiciona permissão na base de dados
		insertPermissionInDataBase();

		try {
			Assert.assertNotNull(new PermissionDAO()
					.findFeaturesPermittedForUser(new UserDAO().getUserById((long) userIdGenerated)).get(0));
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertPermissionInDataBase() throws SQLException {
		System.out.println("Teste: Insert de Permissão");

		try {
			permissionIdGenerated = new PermissionDAO().savePermission((long) userIdGenerated,
					(long) featureIdGenerated);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
