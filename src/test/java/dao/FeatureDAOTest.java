package dao;

import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.util.Util;
import common.model.Feature;
import common.model.Plugin;
import server.dao.DBAccess;
import server.dao.FeatureDAO;
import server.dao.PluginDAO;
import server.dao.ServerException;

public class FeatureDAOTest {
	int pluginIdGenarated = 0;
	int featureIdGenerated = 0;

	@BeforeClass
	public static void testGettingConnection() {
		try {
			System.out.println("Testando conexão com a base de dados ...");
			Assert.assertNotNull(DBAccess.createDBConnection());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Before
	public void createsTestScenario() {
		System.out.println("Inserindo Plugin...");
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
	}

	@Test
	public void shouldInsertFeatureInDataBase() {
		System.out.println("Teste: Insert de Funcionalidade");

		insertFeatureInDataBase();

		Assert.assertTrue(featureIdGenerated > 0);
	}

	@Test
	public void shouldSelectFeatureFromDataBase() {
		System.out.println("Teste: Select de Funcionalidade");

		insertFeatureInDataBase();

		Assert.assertTrue(featureIdGenerated > 0);
		try {
			Assert.assertTrue(new FeatureDAO().findFeatureById((long) featureIdGenerated).getId() == featureIdGenerated);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shouldDeleteFeatureFromDataBase() {
		System.out.println("Teste: Delete de Funcionalidade");

		try {
			new FeatureDAO().deleteFeature(featureIdGenerated);
			// Deve retornar null indicando que a feature foi deletada
			Assert.assertNull(new FeatureDAO().findFeatureById((long) featureIdGenerated).getId());
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void shoulSelectFeaturesOfPlugins() {
		insertFeatureInDataBase();

		try {
			Assert.assertNotNull(new FeatureDAO().findFeaturesByPlugin((long) pluginIdGenarated).get(0));
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertFeatureInDataBase(){
		final Feature feature = new Feature();

		feature.setName("Feature Teste");
		feature.setDescription("Description Feature Teste");
		feature.getPlugin().setId((long) pluginIdGenarated);
		feature.setCreationDate(Util.getCurrentDateFormated());

		// Retorna o id gerado ao inserir a feature no banco
		try {
			featureIdGenerated = new FeatureDAO().saveFeature(feature);
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
