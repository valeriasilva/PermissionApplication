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

	private List<Plugin> rows;

	private String[] columns = new String[] { "Nome", "Descrição", "Data de Criação" };

	public PluginTableModel() {
		rows = new ArrayList<Plugin>();
	}

	public PluginTableModel(final List<Plugin> listaDePlugins) {
		rows = new ArrayList<Plugin>(listaDePlugins);
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
		final Plugin Plugin = rows.get(rowIndex);

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

	// @Override
	// public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
	// final Plugin Plugin = rows.get(rowIndex);
	//
	// switch (columnIndex) {
	// case NAME:
	// Plugin.setName((String) aValue);
	// break;
	// case DESCRIPTION:
	// Plugin.setDescription((String) aValue);
	// break;
	// case CREATIONDATE:
	// Plugin.setCreationDate((Date) aValue);
	// break;
	// case PLUGIN:
	// Plugin.setPlugin((Plugin) aValue);
	// break;
	// default:
	// throw new IndexOutOfBoundsException("columnIndex out of bounds");
	// }
	//
	// fireTableCellUpdated(rowIndex, columnIndex);
	// }

	public Plugin getPlugin(final int indiceLinha) {
		return rows.get(indiceLinha);
	}

	public void addPlugin(final Plugin Plugin) {
		rows.add(Plugin);
		final int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removePlugin(final int indiceLinha) {
		rows.remove(indiceLinha);
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDePlugins(final List<Plugin> Plugins) {

		final int indice = getRowCount();
		rows.addAll(Plugins);
		fireTableRowsInserted(indice, indice + Plugins.size());
	}

	public void limpar() {
		rows.clear();
		fireTableDataChanged();
	}

}
