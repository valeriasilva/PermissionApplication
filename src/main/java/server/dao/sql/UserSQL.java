package server.dao.sql;

public class UserSQL {

	public static String findUsersSql() {
		return "SELECT * FROM User_";
	}

	public static String findUsersByNameSql() {
		return "SELECT * FROM User_ WHERE fullname LIKE %?%";
	}

	public static String saveUserSql() {
		return "INSERT INTO User_ (fullname, status, currentmanagement, login) VALUES(?,?,?,?)";
	}

}
