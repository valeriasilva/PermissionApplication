package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.User;

public class UserDAO extends GenericDAO {
	PreparedStatement stmt;
	
	public UserDAO() throws ServerException {
	}

	public List<User> findUsers() throws SQLException {
		final List<User> users = new ArrayList<User>();
		final String select = "SELECT * FROM User_";
		ResultSet rs = null;
		try {
			stmt = getConnection().prepareStatement(select);
			rs = stmt.executeQuery();
			while (rs.next()) {
				final User user = new User();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setFullname(rs.getString("fullName"));
				user.setStatus(rs.getBoolean("status"));
				user.setCurrentManagement(rs.getString("currentManagement"));
				users.add(user);
			}
		}catch (SQLException e) {
			//Registry LOG (e.getStackTrace()) aqui no handle (????)
			throw new SQLException("Não foi possível buscar usuários"+ e.getMessage());
		}finally{
			rs.close();
			stmt.close();
			getConnection().close();
		}
		return users;
	}

	public List<User> findUsersByName(final String name) throws ServerException {
		final List<User> users = new ArrayList<User>();
		final String select = "SELECT * FROM User_ WHERE fullname LIKE ?";
		try {
			final PreparedStatement stmt = getConnection().prepareStatement(select);
			stmt.setString(1, '%' + name + '%');
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				final User user = new User();
				user.setId(rs.getLong("id"));
				user.setLogin(rs.getString("login"));
				user.setFullname(rs.getString("fullName"));
				user.setStatus(rs.getBoolean("status"));
				user.setCurrentManagement(rs.getString("currentManagement"));
				users.add(user);
			}
			rs.close();
			stmt.close();
			getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new ServerException("Não foi possível buscar usuários"+ e.getStackTrace());
		}
		return users;
	}

	public int saveUser(final User user) throws ServerException  {
		final String insert = "INSERT INTO User_ (fullname, status, currentmanagement, login) VALUES(?,?,?,?)";
		return save(insert, user.getFullname(), user.isStatus(), user.getCurrentManagement(), user.getLogin());
	}
}
