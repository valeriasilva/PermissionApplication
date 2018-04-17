package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class GenericDAO {
	private Connection connection;
	private PreparedStatement pstmt;

	protected GenericDAO() throws ServerException{
		this.connection = DBAccess.createDBConnection();
	}

	protected Connection getConnection() {
		return connection;
	}

	protected int save(final String insertSql, final Object... params) throws ServerException {
		int idGenerated = 0;
		try {
			pstmt = getConnection().prepareStatement(insertSql, new String[] { "id" });
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			pstmt.execute();
			connection.commit();
			final ResultSet rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				idGenerated = rs.getInt(1);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ServerException("Problema ao executar o INSERT na base de dados: \n"+ e.getStackTrace());
		}finally {
			try {
				pstmt.close();
				connection.close();
			}catch(SQLException e) {
				//Log	
				e.printStackTrace();
			}
		}
		return idGenerated;
	}

	protected void update(final String updateSql, final Object id, final Object... params) throws ServerException{
		try {
			pstmt = getConnection().prepareStatement(updateSql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			pstmt.setObject(params.length + 1, id);
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ServerException("Problemas ao executar o comando de Update na base de dados.\n"+ e.getStackTrace());
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				//Log
				e.printStackTrace();
			}
		}
	}

	protected void delete(final String deleteSql, final Object... params) throws ServerException{
		try {
			pstmt = getConnection().prepareStatement(deleteSql);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			pstmt.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new ServerException("Problemas ao executar o comando de Delete na base de dados.\n"+ e.getStackTrace());
		}finally {
			try {
				pstmt.close();
				connection.close();
			} catch (SQLException e) {
				//Log
				e.printStackTrace();
			}
		}
	}
}