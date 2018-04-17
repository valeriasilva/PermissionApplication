package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import client.controller.PluginController;
import common.model.Feature;
import common.model.Plugin;

public class FeatureDAO extends GenericDAO {

	public int saveFeature(final Feature feature)  throws ServerException {
		final String insert = "INSERT INTO Feature (name, description, creationDate, plugin_id) VALUES(?,?,?,?)";
		try {
			return save(insert, feature.getName(), feature.getDescription(), feature.getCreationDate(),
					feature.getPlugin().getId().intValue());
		} catch (SQLException e) {
			throw new ServerException("Não foi possível salvar funcionalidade "+ e.getMessage());
		}
	}

	public void updateFeature(final Feature feature) throws ServerException {
		final String update = "UPDATE Feature " +
				"SET name = ?, description = ? " +
				"WHERE id = ?";
		try {
			update(update, feature.getId(), feature.getName(), feature.getDescription());
		} catch (SQLException e) {
			throw new ServerException("Não foi possível atualizar funcionalidade "+ e.getMessage());
		}
	}

	public void deleteFeature(final long id) throws ServerException {
		final String delete = "DELETE FROM Feature WHERE id = ?";
		try {
			delete(delete, id);
		} catch (SQLException e) {
			throw new ServerException("Não foi possível excluir funcionalidade "+ e.getMessage());
		}
	}

	public List<Feature> findAllFeatures() throws ServerException {
		final List<Feature> features = new ArrayList<Feature>();

		final String select = "SELECT * FROM Feature";

		PreparedStatement stmt;

		try {
			stmt = getConnection().prepareStatement(select);
			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				final Feature feature = new Feature();
				Plugin plugin = new Plugin();
				if (rs.getLong("plugin_id") > 0) {
					plugin = new PluginDAO().findPluginById(rs.getLong("plugin_id"));
					feature.setPlugin(plugin);
				}
				feature.setId(rs.getLong("id"));
				feature.setName(rs.getString("name"));
				feature.setDescription(rs.getString("description"));
				feature.setCreationDate(rs.getTimestamp("creationDate"));
				features.add(feature);
			}

			rs.close();
			stmt.close();
			getConnection().close();
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades "+ e.getMessage());
		}

		return features;
	}

	public List<Feature> findFeatureByName(final String name) throws ServerException {
		final String select = "SELECT * FROM Feature WHERE name LIKE ?";
		Feature feature = null;
		final List<Feature> features = new ArrayList<Feature>();

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setString(1, '%' + name + '%');
			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				feature = new Feature();
				Plugin plugin = new Plugin();
				if (rs.getLong("plugin_id") > 0) {
					plugin = new PluginDAO().findPluginById(rs.getLong("plugin_id"));
					feature.setPlugin(plugin);
				}
				feature.setId(rs.getLong("id"));
				feature.setName(rs.getString("name"));
				feature.setDescription(rs.getString("description"));
				feature.setCreationDate(rs.getTimestamp("creationDate"));
				features.add(feature);
			}

			rs.close();
			stmt.close();
			getConnection().close();

		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades "+ e.getMessage());
		}

		return features;
	}

	public Feature findFeatureById(final Long featureId) throws ServerException {
		final Feature feature = new Feature();
		final String select = "SELECT * FROM Feature WHERE id = ?";

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, featureId);

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Plugin plugin = new Plugin();
				if (rs.getLong("plugin_id") > 0) {
					plugin = new PluginDAO().findPluginById(rs.getLong("plugin_id"));
					feature.setPlugin(plugin);
				}
				feature.setId(rs.getLong("id"));
				feature.setName(rs.getString("name"));
				feature.setDescription(rs.getString("description"));
				feature.setCreationDate(rs.getTimestamp("creationDate"));
			}

			rs.close();
			stmt.close();
			getConnection().close();
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades "+ e.getMessage());
		}

		return feature;
	}

	public List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServerException {
		final String select = "SELECT * FROM Feature WHERE plugin_id = ?";
		Feature feature = null;
		final List<Feature> features = new ArrayList<Feature>();

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, idPlugin);

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				feature = new Feature();
				Plugin plugin = new Plugin();
				if (rs.getLong("plugin_id") > 0) {
					plugin = new PluginDAO().findPluginById(rs.getLong("plugin_id"));
					feature.setPlugin(plugin);
				}
				feature.setId(rs.getLong("id"));
				feature.setName(rs.getString("name"));
				feature.setDescription(rs.getString("description"));
				feature.setCreationDate(rs.getTimestamp("creationDate"));
				features.add(feature);
			}

			rs.close();
			stmt.close();
			getConnection().close();

		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades pelo Plugin"+ e.getMessage());
		}

		return features;
	}
}