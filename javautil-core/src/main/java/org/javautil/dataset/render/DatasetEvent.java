package org.javautil.dataset.render;

import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.Dataset;

public class DatasetEvent<T> {

	private Dataset<T> dataset;

	private ColumnMetadata column;

	private Long columnIndex;

	private Long rowIndex;

	private Object data;

	public DatasetEvent() {
	}

	public DatasetEvent(final Dataset<T> dataset) {
		setDataset(dataset);
	}

	public DatasetEvent(final Dataset<T> dataset, final Long rowIndex) {
		setDataset(dataset);
		setRowIndex(rowIndex);
	}

	public DatasetEvent(final Dataset<T> dataset, final ColumnMetadata column,
			final Long columnIndex) {
		setDataset(dataset);
		setColumn(column);
		setColumnIndex(columnIndex);
	}

	public DatasetEvent(final Dataset<T> dataset, final ColumnMetadata column,
			final Long rowIndex, final Long columnIndex, final Object data) {
		setDataset(dataset);
		setColumn(column);
		setColumnIndex(columnIndex);
		setRowIndex(rowIndex);
		setData(data);
	}

	public Dataset<T> getDataset() {
		return dataset;
	}

	public void setDataset(final Dataset<T> dataset) {
		this.dataset = dataset;
	}

	public ColumnMetadata getColumn() {
		return column;
	}

	public void setColumn(final ColumnMetadata column) {
		this.column = column;
	}

	public Long getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(final Long rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Object getData() {
		return data;
	}

	public void setData(final Object data) {
		this.data = data;
	}

	public Long getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(final Long columnIndex) {
		this.columnIndex = columnIndex;
	}
}
