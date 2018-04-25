package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.User;
import server.dao.sql.UserSQL;

public class UserDAO extends GenericDAO {

	public UserDAO() throws ServerException {
		super();
	}

	public List<User> findUsers() throws ServerException {
		final List<User> users = new ArrayList<>();
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			stmt = getConnection().prepareStatement(UserSQL.findUsersSql());
			rs = stmt.executeQuery();
			while (rs.next()) {
				users.add(buildUser(rs));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ServerException("Não foi possível buscar usuários" + e1.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return users;
	}

	public List<User> findUsersByName(final String name) throws ServerException {

		final List<User> users = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = getConnection().prepareStatement(UserSQL.findUsersByNameSql());
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			while (rs.next()) {
				users.add(buildUser(rs));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ServerException("Não foi possível buscar usuários" + e1.getStackTrace());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return users;
	}

	public int saveUser(final User user) throws ServerException {
		return save(UserSQL.saveUserSql(), user.getFullname(), user.isStatus(), user.getCurrentManagement(),
				user.getLogin());
	}

	public User getUserById(Long id) throws ServerException {

		ResultSet rs = null;
		PreparedStatement stmt = null;

		try {
			stmt = getConnection().prepareStatement(UserSQL.getUserByIdSql());
			stmt.setLong(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return buildUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServerException("Não foi possível buscar usuários" + e.getMessage());
		} finally {
			try {
				rs.close();
				stmt.close();
				getConnection().close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	private User buildUser(final ResultSet rs) throws SQLException {
		final User user = new User();
		user.setId(rs.getLong("id"));
		user.setLogin(rs.getString("login"));
		user.setFullname(rs.getString("fullName"));
		user.setStatus(rs.getBoolean("status"));
		user.setCurrentManagement(rs.getString("currentManagement"));
		return user;
	}

}
