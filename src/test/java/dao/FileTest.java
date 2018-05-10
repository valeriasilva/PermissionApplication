package dao;

import org.junit.Before;
import org.junit.Test;

import common.model.File;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import server.dao.FileDAO;
import server.dao.ServerException;

public class FileTest {

	private FileDAO dao;

	@Before
	public void prepareTestScenario() throws ServerException {
		dao = new FileDAO();
	}

	@Test
	public void shouldSelectFilesByNamePart() throws ServerException {
		List<File> files =  new ArrayList<>();

		files.addAll(dao.fetchFilesByNamePart("File"));
		Assert.assertFalse(files.isEmpty());
	}

	@Test
	public void shouldSelectNoFiles() throws ServerException {
		List<File> files =  new ArrayList<>();

		files.addAll(dao.fetchFilesByNamePart("anInexistentFileName"));
		Assert.assertTrue(files.isEmpty());
	}

	@Test
	public void shouldSelectFileByExactName() throws ServerException {
		Assert.assertEquals(dao.fetchFileByExactName("FileXPTO").getName(), "FileXPTO");
	}

	@Test
	public void shouldSelectNoSpecificFile() throws ServerException {
		Assert.assertNull(dao.fetchFileByExactName("anInexistentFileName"));
	}

	@Test
	public void shouldFindFileInCache() throws ServerException {
		shouldSelectFileByExactName(); //Garantindo que o nome FileXPTO será inserido na cache.
		Assert.assertNotNull(dao.getFileCache().getElement("FileXPTO"));
	}
}
