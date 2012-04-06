package org.javautil.dataset;

import java.util.ArrayList;
import java.util.List;

import org.javautil.collections.Tuple;
import org.javautil.text.AsString;

public class GroupedBy {

	private DatasetMetadata groupedColumnsMeta;

	private DatasetMetadata nongroupedColumnsMeta;

	private Tuple<?> groupColumns;

	private List<Tuple<?>> nongroupedColumns = new ArrayList<Tuple<?>>();

	private static final String newline = System.getProperty("line.separator");

	public GroupedBy(final Tuple<?> _groupColumns,
			final DatasetMetadata groupedColumnsMeta,
			final DatasetMetadata nongroupedColumnsMeta) {
		if (_groupColumns == null) {
			throw new IllegalArgumentException("groupColumns is null");
		}
		if (groupedColumnsMeta == null) {
			throw new IllegalArgumentException("groupedColumnsMeta is null");
		}
		if (nongroupedColumns == null) {
			throw new IllegalArgumentException("nongroupedColumns is null");
		}
		this.groupColumns = _groupColumns;
		this.groupedColumnsMeta = groupedColumnsMeta;
		this.nongroupedColumnsMeta = nongroupedColumnsMeta;
	}

	@Override
	public int hashCode() {
		return groupColumns.hashCode();
	}

	@Override
	public boolean equals(final Object other) {
		return groupColumns.equals(other);
	}

	public List<Tuple<?>> getNongroupedColumns() {
		return nongroupedColumns;
	}

	public void setNongroupedColumns(final List<Tuple<?>> nongroupedColumns) {
		this.nongroupedColumns = nongroupedColumns;
	}

	public void addNonGroupedColumns(final Tuple<?> row) {
		this.nongroupedColumns.add(row);
	}

	@Override
	public String toString() {
		final AsString as = new AsString();
		final StringBuilder b = new StringBuilder();
		b.append(newline);
		b.append("groupedColumnsMeta: " + newline);
		b.append(groupedColumnsMeta.toString());
		b.append("groupColumns: " + newline + as.toString(groupColumns)
				+ newline);

		b.append("nongroupedColumns" + newline);

		for (final Tuple t : nongroupedColumns) {
			b.append(as.toString(t));
			b.append(newline);
		}

		return b.toString();
	}

	public String toStringNoMeta() {
		final AsString as = new AsString();
		final StringBuilder b = new StringBuilder();
		b.append(newline);

		b.append("groupColumns: " + newline + as.toString(groupColumns)
				+ newline);
		b.append("non grouped " + newline);
		for (final Tuple t : nongroupedColumns) {
			b.append(as.toString(t));
			b.append(newline);
		}

		return b.toString();
	}

	public DatasetMetadata getGroupedColumnsMeta() {
		return groupedColumnsMeta;
	}

	public void setGroupedColumnsMeta(final DatasetMetadata groupedColumnsMeta) {
		this.groupedColumnsMeta = groupedColumnsMeta;
	}

	public DatasetMetadata getNongroupedColumnsMeta() {
		return nongroupedColumnsMeta;
	}

	public void setNongroupedColumnsMeta(
			final DatasetMetadata nongroupedColumnsMeta) {
		this.nongroupedColumnsMeta = nongroupedColumnsMeta;
	}

	public Tuple<?> getGroupColumns() {
		return groupColumns;
	}

	public void setGroupColumns(final Tuple<?> groupColumns) {
		this.groupColumns = groupColumns;
	}
}
