package client.view.tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.Plugin;
import oracle.sql.TIMESTAMP;

public class PluginTableModel extends AbstractTableModel {

	private static final int NAME = 0;
	private static final int DESCRIPTION = 1;
	private static final int CREATIONDATE = 2;
	private static final int[] SEARCHABLE_COLS = new int[] { 0, 1, 2 };

	private List<Plugin> items;

	private String[] columns = new String[] { "Nome", "Descrição", "Data de Criação" };

	public PluginTableModel() {
		items = new ArrayList<>();
	}

	@Override
	public int getRowCount() {
		return items.size();
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
		case NAME:
			return String.class;
		case DESCRIPTION:
			return String.class;
		case CREATIONDATE:
			return TIMESTAMP.class;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Plugin Plugin = items.get(rowIndex);

		switch (columnIndex) {
		case NAME:
			return Plugin.getName();
		case DESCRIPTION:
			return Plugin.getDescription();
		case CREATIONDATE:
			return Plugin.getCreationDate().toString();
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	public Plugin getPlugin(final int indiceLinha) {
		return items.get(indiceLinha);
	}

	public void removePlugin(final int indiceLinha) {
		items.remove(indiceLinha);
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void setItems(List<Plugin> listPlugins) {
		items.addAll(listPlugins);
		fireTableDataChanged();
	}

	public static int[] getSearchableColumns() {
		return SEARCHABLE_COLS;
	}

}
