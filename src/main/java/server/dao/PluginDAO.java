package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.Plugin;

public class PluginDAO extends GenericDAO {

	public PluginDAO() throws ServerException {
	}

	public int savePlugin(final Plugin plugin) throws ServerException {
		final String insert = "INSERT INTO Plugin (name, description, creationDate) VALUES(?,?,?)";
		return save(insert, plugin.getName(), plugin.getDescription(), plugin.getCreationDate());
	}

	public void updatePlugin(final Plugin plugin) throws ServerException {
		final String update = "UPDATE Plugin " +
				"SET name = ?, description = ? " +
				"WHERE id = ?";
		update(update, plugin.getId(), plugin.getName(), plugin.getDescription());
	}

	public void deletePlugin(final long id) throws ServerException {
		final String delete = "DELETE FROM Plugin WHERE id = ?";
		delete(delete, id);
	}

	public List<Plugin> findPlugins() throws ServerException {
		final List<Plugin> plugins = new ArrayList<Plugin>();
		final String select = "SELECT * FROM Plugin";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			rs = stmt.executeQuery();

			while (rs.next()) {
				final Plugin plugin = new Plugin();
				plugin.setId(rs.getLong("id"));
				plugin.setName(rs.getString("name"));
				plugin.setDescription(rs.getString("description"));
				plugin.setCreationDate(rs.getTimestamp("creationDate"));
				plugins.add(plugin);
			}
			
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar plugins. "+ e.getStackTrace());
		}finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				//Log
				e.getStackTrace();
			}
		}
		return plugins;
	}

	public Plugin findPluginById(final Long idPlugin) throws ServerException {
		final String select = "SELECT * FROM Plugin WHERE id = ?";
		Plugin plugin = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			stmt.setLong(1, idPlugin);
			rs = stmt.executeQuery();

			while (rs.next()) {
				plugin = new Plugin();
				plugin.setId(rs.getLong("id"));
				plugin.setName(rs.getString("name"));
				plugin.setDescription(rs.getString("description"));
				plugin.setCreationDate(rs.getTimestamp("creationDate"));
			}
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar plugins pelo ID"+ e.getStackTrace());
		}finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				//Log
				e.getStackTrace();
			}
		}
		return plugin;
	}

	public List<Plugin> findByName(final String name) throws ServerException {
		final String select = "SELECT * FROM Plugin WHERE name LIKE ?";
		Plugin plugin = null;
		final List<Plugin> plugins = new ArrayList<Plugin>();
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		try {
			stmt = getConnection().prepareStatement(select);
			stmt.setString(1, '%' + name + '%');
			rs = stmt.executeQuery();

			while (rs.next()) {
				plugin = new Plugin();
				plugin.setId(rs.getLong("id"));
				plugin.setName(rs.getString("name"));
				plugin.setDescription(rs.getString("description"));
				plugin.setCreationDate(rs.getTimestamp("creationDate"));
				plugins.add(plugin);
			}
		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar plugins pelo nome"+ e.getStackTrace());
		}finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				//Log
				e.getStackTrace();
			}
		}
		return plugins;
	}
}