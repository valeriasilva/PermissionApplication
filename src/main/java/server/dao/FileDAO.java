package server.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.model.File;
import common.util.Caching;

public class FileDAO extends GenericDAO {

	private Caching<File> fileCache = new Caching<>();

	public FileDAO() throws ServerException {
	}

	public List<File> fetchFilesByNamePart(final String name) throws ServerException {
		List<File> files = new ArrayList<>();
		File file = new File();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM File_ WHERE name LIKE ?";

			stmt = getConnection().prepareStatement(sql);
			stmt.setString(1, '%' + name + '%');
			rs = stmt.executeQuery();
			while (rs.next()) {
				file = buildFile(rs);
				files.add(file);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ServerException("Não foi possível buscar arquivos" + e1.getStackTrace());
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return files;
	}

	public File fetchFileByExactName(final String name) throws ServerException {
		File file = new File();

		file = fetchFromCache(name);

		if (file != null) {
			System.out.println("Retornado da cache: " + file.getName());
			return file;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT * FROM File_ WHERE name = ?";

			stmt = getConnection().prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				file = buildFile(rs);
				fileCache.cacheElement(file);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new ServerException("Não foi possível buscar o arquivo" + e1.getStackTrace());
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return file;
	}

	private File fetchFromCache(String name) {
		return fileCache.getElement(name);
	}

	private File buildFile(final ResultSet rs) throws SQLException {
		final File file = new File();
		file.setId(rs.getLong("id"));
		file.setName(rs.getString("name"));
		file.setSize(rs.getLong("filesize"));

		return file;
	}

	public Caching<File> getFileCache() {
		return fileCache;
	}

	public void setFileCache(Caching<File> fileCache) {
		this.fileCache = fileCache;
	}
}
