package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDAO {
	private Connection connection;

	protected GenericDAO() {
		this.connection = DBAccess.createDBConnection();
	}

	protected Connection getConnection() {
		return connection;
	}

	protected int save(final String insertSql, final Object... params) throws SQLException {
		int idGenerated = 0;
		final PreparedStatement pstmt = getConnection().prepareStatement(insertSql, new String[] { "id" });

		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}

		pstmt.execute();
		final ResultSet rs = pstmt.getGeneratedKeys();

		while (rs.next()) {
			idGenerated = rs.getInt(1);
		}
		pstmt.close();
		connection.close();

		return idGenerated;

	}

	protected void update(final String updateSql, final Object id, final Object... params) throws SQLException {
		final PreparedStatement pstmt = getConnection().prepareStatement(updateSql);

		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		pstmt.setObject(params.length + 1, id);
		pstmt.execute();
		pstmt.close();
		connection.close();
	}

	protected void delete(final String deleteSql, final Object... params) throws SQLException {
		final PreparedStatement pstmt = getConnection().prepareStatement(deleteSql);

		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}

		pstmt.execute();
		pstmt.close();
		connection.close();
	}
}