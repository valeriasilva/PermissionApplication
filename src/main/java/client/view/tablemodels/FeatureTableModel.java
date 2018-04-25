package client.view.tablemodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.Feature;

public class FeatureTableModel extends AbstractTableModel {

	private static final int NAME = 0;
	private static final int DESCRIPTION = 1;
	private static final int CREATIONDATE = 2;
	private static final int PLUGIN = 3;
	private static final int[] SEARCHABLE_COLS = new int[] { 0, 1, 2, 3 };

	private List<Feature> data;

	private String[] columns = new String[] { "Nome", "Descrição", "Data de Criação", "Plugin" };

	public FeatureTableModel() {
		data = new ArrayList<Feature>();
	}

	public FeatureTableModel(final List<Feature> listaDeFeatures) {
		data = new ArrayList<Feature>(listaDeFeatures);
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
		case NAME:
			return String.class;
		case DESCRIPTION:
			return String.class;
		case CREATIONDATE:
			return String.class;
		case PLUGIN:
			return String.class;
		default:
			throw new IndexOutOfBoundsException(
					"Coluna da tabela de Funcionalidades fora do limite de colunas. \n columnIndex out of bounds");
		}
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Feature feature = data.get(rowIndex);

		switch (columnIndex) {
		case NAME:
			return feature.getName();
		case DESCRIPTION:
			return feature.getDescription();
		case CREATIONDATE:
			return new SimpleDateFormat("dd/MM/yyyy").format(feature.getCreationDate());
		case PLUGIN:
			if (feature.getPlugin() != null)
				return feature.getPlugin().getName();
			else
				return "";
		default:
			throw new IndexOutOfBoundsException(
					"Coluna da tabela de Funcionalidades fora do limite de colunas. \ncolumnIndex out of bounds");
		}
	}

	public Feature getFeature(final int idx) {
		return data.get(idx);
	}

	public void addFeature(final Feature feature) {
		data.add(feature);
		final int lastIdx = getRowCount() - 1;
		fireTableRowsInserted(lastIdx, lastIdx);
	}

	public void removeFeature(final int idx) {
		data.remove(idx);
		fireTableRowsDeleted(idx, idx);
	}

	public void addFeatures(final List<Feature> features) {
		final int idx = getRowCount();
		data.addAll(features);
		fireTableRowsInserted(idx, idx + features.size());
	}

	public void clear() {
		data.clear();
		fireTableDataChanged();
	}

	public static int[] getSearchableCols() {
		return SEARCHABLE_COLS;
	}
}
