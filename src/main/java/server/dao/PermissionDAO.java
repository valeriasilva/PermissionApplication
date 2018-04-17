package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.Feature;

public class PermissionDAO extends GenericDAO {

	public int savePermission(final Long userId, final Long featureId) throws ServerException {
		final String insert = "INSERT INTO Permission_ (UserId, FeatureId) VALUES(?,?)";
		try {
			return save(insert, userId, featureId);
		} catch (SQLException e) {
			//Log StackeTrace, Query onde ocorreu o erro
			throw new ServerException("Não foi possível salvar permissão "+ e.getMessage());
		}
	}

	public void deletePermission(final long FeatureId, final long UserId) throws ServerException {
		final String delete = "DELETE FROM Permission_ WHERE FeatureId = ? AND UserId = ?";
		try {
			delete(delete, FeatureId, UserId);
		} catch (SQLException e) {
			throw new ServerException("Não foi possível excluir permissão "+ e.getMessage());
		}
	}

	/**
	 * Método para verificar se determinado usuário já
	 * tem permissão para usar determinada Feature.
	 * 
	 * @return false
	 */
	public boolean IsTherePermissionByFeatureAndUser(final Long userId, final Long featureId) throws ServerException {
		final String select = "SELECT * FROM Permission_ WHERE UserId = ? AND FeatureId = ?";

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, userId);
			stmt.setLong(2, featureId);

			final ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			throw new ServerException("Não foi possível verificar se há permissão "+ e.getMessage());
		}

		return false;
	}

	public List<Feature> findFeaturesNotPermittedFor(final Long UserId) throws ServerException{
		final String select = "SELECT * FROM Permission_ WHERE UserId != ?";
		final List<Feature> features = new ArrayList<Feature>();
		Feature feature = null;

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, UserId);

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (rs.getLong("FeatureId") > 0) {
					try {
						feature = new FeatureDAO().findFeatureById(rs.getLong("FeatureId"));
					} catch (ServerException e) {
						throw new ServerException("Não foi possível buscar funcionalidades"+ e.getMessage());
					}
					features.add(feature);
				}
			}

			rs.close();
			stmt.close();
			getConnection().close();

		} catch (SQLException e1) {
			throw new ServerException("Não foi possível buscar funcionalidades não permitidas "+ e1.getMessage());
		}


		return features;
	}

	public List<Feature> findFeaturesPermittedForUser(final Long UserId) throws ServerException {
		final String select = "SELECT * FROM Permission_ WHERE UserId = ?";
		final List<Feature> features = new ArrayList<Feature>();
		Feature feature = null;

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, UserId);

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (rs.getLong("FeatureId") > 0) {
					feature = new FeatureDAO().findFeatureById(rs.getLong("FeatureId"));
					features.add(feature);
				}
			}

			rs.close();
			stmt.close();
			getConnection().close();

		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades permitidas "+ e.getMessage());
		}

		return features;
	}

	public List<Feature> findFeaturesNotPermittedByName(final String name, final Long userId) throws ServerException {
		final String select = "SELECT * FROM Permission_ WHERE UserId <> ?";
		final List<Feature> features = new ArrayList<Feature>();
		Feature feature = null;

		PreparedStatement stmt;
		try {
			stmt = getConnection().prepareStatement(select);

			stmt.setLong(1, userId);

			final ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				if (rs.getLong("FeatureId") > 0) {
					feature = new FeatureDAO().findFeatureById(rs.getLong("FeatureId"));
					features.add(feature);
				}
			}

			rs.close();
			stmt.close();
			getConnection().close();

		} catch (SQLException e) {
			throw new ServerException("Não foi possível buscar funcionalidades não permitidas "+ e.getMessage());
		}
		return features;
	}
}