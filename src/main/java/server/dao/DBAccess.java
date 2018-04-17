package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAccess {

	private Connection conn;
	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String USER = "valeria";
	private static final String PASS = "12345678";

	public DBAccess() {
	}

	public static Connection createDBConnection() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (final ClassNotFoundException e) {
			System.out.println("Erro! Driver Class oracle.jdbc.driver.OracleDriver não encontrada");
			System.exit(1);
		}
		try {
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", USER, PASS);
		} catch (final Exception e) {
			System.out.println("ERRO " + e.getMessage());
		}
		return null;
	}

	public static Connection createDBConnection(final String driver_class, final String user, final String pass) {
		try {
			Class.forName(driver_class);
		} catch (final ClassNotFoundException e) {
			System.out.println("Erro! Driver Class oracle.jdbc.driver.OracleDriver não encontrada");
			System.exit(1);
		}
		try {
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", user, pass);
		} catch (final Exception e) {
			System.out.println("ERRO " + e.getMessage());
		}
		return null;
	}
}