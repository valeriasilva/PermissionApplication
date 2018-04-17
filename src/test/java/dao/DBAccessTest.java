package dao;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

import server.dao.DBAccess;

public class DBAccessTest {

	@Test
	public void deveConectarComSucessoAoBanco() {
		System.out.println("Teste: Sucesso na conexão com o banco");
		final Connection connection = DBAccess.createDBConnection();

		Assert.assertNotNull(connection);
	}

	@Test
	public void testDeConexaoAoBancoComSenhaErrada() {
		System.out.println("Teste: Problemas (senha errada) na conexão com o banco");
		final Connection connection = DBAccess.createDBConnection("oracle.jdbc.driver.OracleDriver", "valeria",
				"senhaerrada");

		Assert.assertNull(connection);
	}

}
