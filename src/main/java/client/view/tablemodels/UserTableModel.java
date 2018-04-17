package client.view.tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.User;

public class UserTableModel extends AbstractTableModel {

	private static final int FULLNAME = 0;
	private static final int LOGIN = 1;
	private static final int STATUS = 2;
	private static final int CURRENTMANAGEMENT = 3;;

	private List<User> rows;

	private String[] columns = new String[] { "Nome", "Login", "Status", "Gerência Atual" };

	public UserTableModel() {
		rows = new ArrayList<User>();
	}

	public UserTableModel(final List<User> listaDeUsers) {
		rows = new ArrayList<User>(listaDeUsers);
	}

	@Override
	public int getRowCount() {
		return rows.size();
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
		final User User = rows.get(rowIndex);

		switch (columnIndex) {
		case FULLNAME:
			return User.getFullname();
		case STATUS:
			return User.isStatus();
		case CURRENTMANAGEMENT:
			return User.getCurrentManagement();
		case LOGIN:
			return User.getLogin();
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	// @Override
	// public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
	// final User User = rows.get(rowIndex);
	//
	// switch (columnIndex) {
	// case FULLNAME:
	// User.setName((String) aValue);
	// break;
	// case STATUS:
	// User.setDescription((String) aValue);
	// break;
	// case CURRENTMANAGEMENT:
	// User.setCreationDate((Date) aValue);
	// break;
	// case LOGIN:
	// User.setPlugin((Plugin) aValue);
	// break;
	// default:
	// throw new IndexOutOfBoundsException("columnIndex out of bounds");
	// }
	//
	// fireTableCellUpdated(rowIndex, columnIndex);
	// }

	public User getUser(final int indiceLinha) {
		return rows.get(indiceLinha);
	}

	public void addUser(final User User) {
		rows.add(User);
		final int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeUser(final int indiceLinha) {
		rows.remove(indiceLinha);
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeUsers(final List<User> Users) {

		final int indice = getRowCount();
		rows.addAll(Users);
		fireTableRowsInserted(indice, indice + Users.size());
	}

	public void limpar() {
		rows.clear();
		fireTableDataChanged();
	}

}
