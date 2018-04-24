package client.view.tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.Plugin;
import common.model.User;

public class UserTableModel extends AbstractTableModel {

	private static final int FULLNAME = 0;
	private static final int LOGIN = 1;
	private static final int STATUS = 2;
	private static final int CURRENTMANAGEMENT = 3;
	private static final int[] SEARCHABLE_COLS = new int[] { 0, 1, 3 };


	private String[] columns = new String[] { "Nome", "Login", "Ativo", "Gerência Atual" };
	private List<User> data;

	public UserTableModel(List<User> users) {
		setItems(users);
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public String getColumnName(final int columnIndex) {
		return columns[columnIndex];
	};

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		switch (columnIndex) {
		case FULLNAME:
			return String.class;
		case STATUS:
			return Boolean.class;
		case CURRENTMANAGEMENT:
			return String.class;
		case LOGIN:
			return String.class;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final User user = data.get(rowIndex);
		switch (columnIndex) {
		case FULLNAME:
			return user.getFullname();
		case STATUS:
			return user.isStatus();
		case CURRENTMANAGEMENT:
			return user.getCurrentManagement();
		case LOGIN:
			return user.getLogin();
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	public User getUser(final int indiceLinha) {
		return data.get(indiceLinha);
	}

	public void setItems(List<User> users) {
		data = new ArrayList<>(users);
		fireTableDataChanged();
	}
	
	public static int[] getSearchableColumns() {
		return SEARCHABLE_COLS;
	}
}
