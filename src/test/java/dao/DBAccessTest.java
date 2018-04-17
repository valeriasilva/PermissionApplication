package dao;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import common.service.ServiceException;
import server.dao.DBAccess;
import server.dao.ServerException;

public class DBAccessTest {

	@Test
	public void deveConectarComSucessoAoBanco() {
		System.out.println("Teste: Sucesso na conexão com o banco");
		Connection connection = null;
		try {
			connection = DBAccess.createDBConnection();
		} catch (ServerException e) {
			System.out.println("Não foi possível estabelecer conexão com a base de dados. \n" + e.getMessage());
		}

		Assert.assertNotNull(connection);
	}

	@Ignore
	public void testDeConexaoAoBancoComSenhaErrada() {
		System.out.println("Teste de senha incorreta: Deve gerar um erro de (senha errada) na conexão com o banco");
		Connection connection = null;
		try {
			connection = DBAccess.createDBConnection("oracle.jdbc.driver.OracleDriver", "valeria","senhaerrada");
		} catch (ServerException e) {
			System.out.println("Não foi possível estabelecer conexão com a base de dados. \n" + e.getMessage());
		}

		Assert.assertNull(connection);
	}
}
