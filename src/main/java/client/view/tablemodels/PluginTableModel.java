package client.view.tablemodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.Plugin;

public class PluginTableModel extends AbstractTableModel {

	private static final int NAME = 0;
	private static final int DESCRIPTION = 1;
	private static final int CREATIONDATE = 2;
	private static final int[] SEARCHABLE_COLS = new int[] { 0, 1, 2 };

	private String[] columns = new String[] { "Nome", "Descrição", "Data de Criação" };
	private final List<Plugin> items;

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
			return String.class;
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Plugin plugin = items.get(rowIndex);
		switch (columnIndex) {
		case NAME:
			return plugin.getName();
		case DESCRIPTION:
			return plugin.getDescription();
		case CREATIONDATE:
			return new SimpleDateFormat("dd/MM/yyyy").format(plugin.getCreationDate());
		default:
			throw new IndexOutOfBoundsException("columnIndex out of bounds");
		}
	}

	public Plugin getPlugin(final int index) {
		return items.get(index);
	}

	public void removePlugin(final int index) {
		items.remove(index);
		fireTableRowsDeleted(index, index);
	}

	public void setItems(List<Plugin> plugins) {
		items.addAll(plugins);
		fireTableDataChanged();
	}

	public static int[] getSearchableColumns() {
		return SEARCHABLE_COLS;
	}
}
