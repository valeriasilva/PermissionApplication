package dao;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import client.util.Util;
import common.model.Feature;
import common.model.Plugin;
import server.dao.FeatureDAO;
import server.dao.PluginDAO;
import server.dao.ServerException;

public class PluginDAOTest {

	int pluginIdGenarated = 0;
	int featureIdGenerated = 0;

	@Before
	public void createsTestScenario() {
		insertPluginInDataBase();
		insertFeatureByPlugin();
	}

	@Test
	public void shouldInsertPluginInDataBase() {
		insertPluginInDataBase();

		Assert.assertTrue(pluginIdGenarated > 0);
	}

	@Test
	public void shouldSelectPluginsFromDataBase(){
		try {
			insertPluginInDataBase();
			Assert.assertNotNull(new PluginDAO().findPlugins().get(0));
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertPluginInDataBase() {
		final Plugin plugin = new Plugin();

		plugin.setName("Plugin test");
		plugin.setDescription("Description Plugin Teste");

		// Retorna o id gerado ao inserir o plugin no banco
		try {
			pluginIdGenarated = new PluginDAO().savePlugin(plugin);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertTrue(pluginIdGenarated > 0);
	}

	public void insertFeatureByPlugin() {
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
}
