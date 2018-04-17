package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBAccess {

	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String USER = "candidato";
	private static final String PASS = "candidato";

	public static Connection createDBConnection() throws ServerException {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (final ClassNotFoundException e) {
			System.out.println("Erro! Driver Class oracle.jdbc.driver.OracleDriver não encontrada");
			System.exit(1);
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", USER, PASS);
			connection.setAutoCommit(false);
			return connection;
		} catch (final Exception e) {
			throw new ServerException("ERRO: Não foi possivel pegar a conexão:" + e.getMessage());
		}
	}

	public static Connection createDBConnection(final String driver_class, final String user, final String pass) throws ServerException {
		try {
			Class.forName(driver_class);
		} catch (final ClassNotFoundException e) {
			System.out.println("Erro! Driver Class oracle.jdbc.driver.OracleDriver não encontrada");
			System.exit(1);
		}
		try {
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", user, pass);
			connection.setAutoCommit(false);
			return connection;
		} catch (final Exception e) {
			throw new ServerException("ERRO: Não foi possivel pegar a conexão:" + e.getMessage());
		}
	}
}