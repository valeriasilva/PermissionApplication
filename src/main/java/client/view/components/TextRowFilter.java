package client.view.components;

import java.util.Arrays;

import javax.swing.RowFilter;

public final class TextRowFilter extends RowFilter<Object, Object> {

	private final transient String searchText;
	private final transient int[] indexes;

	public TextRowFilter(final String searchText, int... indexes) {
		this.searchText = searchText;
		this.indexes = Arrays.copyOf(indexes, indexes.length);
	}

	public boolean include(Entry<? extends Object, ? extends Object> entry) {

		if (searchText == null || searchText.isEmpty()) {
			return true;
		}

		for (int col : indexes) {
			if (entry.getStringValue(col).toUpperCase().contains(searchText.toUpperCase())) {
				return true;
			}
		}

		return false;
	}

}
