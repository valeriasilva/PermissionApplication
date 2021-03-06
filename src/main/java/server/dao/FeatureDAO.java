package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.Feature;
import common.model.Plugin;

public class FeatureDAO extends GenericDAO {

	public FeatureDAO() throws ServerException {
	}

	public int saveFeature(final Feature feature) throws ServerException {
		final String insert = "INSERT INTO Feature (name, description, creationDate, plugin_id) VALUES(?,?,?,?)";
		return save(insert, feature.getName(), feature.getDescription(), new java.sql.Timestamp(feature.getCreationDate().getTime()),
				feature.getPlugin().getId().intValue());
	}

	public void updateFeature(final Feature feature) throws ServerException {
		final String update = "UPDATE Feature " + "SET name = ?, description = ?, plugin_id = ? " + "WHERE id = ?";
		update(update, feature.getId(), feature.getName(), feature.getDescription(), feature.getPlugin().getId());
	}

	public void deleteFeature(final long id) throws ServerException {
		final String delete = "DELETE FROM Feature WHERE id = ?";
		delete(delete, id);
	}

	public List<Feature> findAllFeatures() throws ServerException {
		final List<Feature> features = new ArrayList<Feature>();

		final String select = "SELECT * FROM Feature";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			rs = stmt.executeQuery();

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
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades " + e.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				// Log
				e.printStackTrace();
			}
		}
		return features;
	}

	public Feature findFeatureById(final Long featureId) throws ServerException {
		final Feature feature = new Feature();
		final String select = "SELECT * FROM Feature WHERE id = ?";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			stmt.setLong(1, featureId);
			rs = stmt.executeQuery();

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
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades " + e.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				// Log
				e.printStackTrace();
			}
		}
		return feature;
	}

	public List<Feature> findFeaturesByPlugin(final Long idPlugin) throws ServerException {
		final String select = "SELECT * FROM Feature WHERE plugin_id = ?";
		Feature feature = null;
		final List<Feature> features = new ArrayList<Feature>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			stmt.setLong(1, idPlugin);
			rs = stmt.executeQuery();
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
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades pelo Plugin" + e.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				// Log
				e.printStackTrace();
			}
		}
		return features;
	}
}