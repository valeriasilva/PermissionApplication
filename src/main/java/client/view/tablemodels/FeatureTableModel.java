package client.view.tablemodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import common.model.Feature;
import oracle.sql.TIMESTAMP;

public class FeatureTableModel extends AbstractTableModel {

	private static final int NAME = 0;
	private static final int DESCRIPTION = 1;
	private static final int CREATIONDATE = 2;
	private static final int PLUGIN = 3;

	private List<Feature> rows;

	private String[] columns = new String[] { "Nome", "Descrição", "Data de Criação", "Plugin" };

	public FeatureTableModel() {
		rows = new ArrayList<Feature>();
	}

	public FeatureTableModel(final List<Feature> listaDeFeatures) {
		rows = new ArrayList<Feature>(listaDeFeatures);
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
		case PLUGIN:
			return String.class;
		default:
			throw new IndexOutOfBoundsException("Coluna da tabela de Funcionalidades fora do limite de colunas. \n columnIndex out of bounds");
		}
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Feature Feature = rows.get(rowIndex);

		switch (columnIndex) {
		case NAME:
			return Feature.getName();
		case DESCRIPTION:
			return Feature.getDescription();
		case CREATIONDATE:
			return Feature.getCreationDate();
		case PLUGIN:
			if (Feature.getPlugin() != null)
				return Feature.getPlugin().getName();
			else
				return "";
		default:
			throw new IndexOutOfBoundsException("Coluna da tabela de Funcionalidades fora do limite de colunas. \ncolumnIndex out of bounds");
		}
	}

	public Feature getFeature(final int indiceLinha) {
		return rows.get(indiceLinha);
	}

	public void addFeature(final Feature Feature) {
		rows.add(Feature);
		final int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeFeature(final int indiceLinha) {
		rows.remove(indiceLinha);
		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeFeatures(final List<Feature> Features) {

		final int indice = getRowCount();
		rows.addAll(Features);
		fireTableRowsInserted(indice, indice + Features.size());
	}

	public void limpar() {
		rows.clear();
		fireTableDataChanged();
	}

}
