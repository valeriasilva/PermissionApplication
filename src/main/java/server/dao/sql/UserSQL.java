package server.dao.sql;

public class UserSQL {

	public static String findUsersSql() {
		return "SELECT * FROM User_";
	}

	public static String findUserByNameSql() {
		return findUsersSql() + " WHERE upper(fullname) = ?";
	}

	public static String saveUserSql() {
		return "INSERT INTO User_ (fullname, status, currentmanagement, login) VALUES(?,?,?,?)";
	}

	public static String getUserByIdSql() {
		StringBuilder sql = new StringBuilder();
		sql.append(findUsersSql());
		sql.append(" where id = ? ");
		return sql.toString();
	}

}
