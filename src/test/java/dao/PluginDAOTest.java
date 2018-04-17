package dao;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import common.model.Feature;
import common.model.Plugin;
import server.dao.FeatureDAO;
import server.dao.PluginDAO;
import server.dao.ServerException;

public class PluginDAOTest {

	int pluginIdGenarated = 0;
	int featureIdGenerated = 0;

	@Before
	public void createsTestScenario() throws SQLException {
		insertPluginInDataBase();
		insertFeatureByPlugin();
	}

	@Test
	public void shouldInsertPluginInDataBase() {
		try {
			insertPluginInDataBase();
		} catch (SQLException e) {
			System.out.println("Não foi possível inserir plugin "+ e.getStackTrace());
		}

		Assert.assertTrue(pluginIdGenarated > 0);
	}

	@Test
	public void shouldSelectPluginsFromDataBase(){
		try {
			insertPluginInDataBase();
		} catch (SQLException e1) {
			System.out.println("Não foi possível retornar plugins "+ e1.getStackTrace());
		}

		try {
			Assert.assertNotNull(new PluginDAO().findPlugins().get(0));
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertPluginInDataBase() throws SQLException {
		final Plugin plugin = new Plugin();

		plugin.setName("Plugin test");
		plugin.setDescription("Description Plugin Teste");
		plugin.setCreationDate((getCurrentDateFormated()));

		// Retorna o id gerado ao inserir o plugin no banco
		try {
			pluginIdGenarated = new PluginDAO().savePlugin(plugin);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(pluginIdGenarated > 0);
	}

	public void insertFeatureByPlugin() throws SQLException {
		final Feature feature = new Feature();

		feature.setName("Feature Teste");
		feature.setDescription("Description Feature Teste");
		feature.getPlugin().setId((long) pluginIdGenarated);

		// Retorna o id gerado ao inserir a feature no banco
		try {
			featureIdGenerated = new FeatureDAO().saveFeature(feature);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Timestamp getCurrentDateFormated() {
		return new Timestamp(System.currentTimeMillis());
	}
}
