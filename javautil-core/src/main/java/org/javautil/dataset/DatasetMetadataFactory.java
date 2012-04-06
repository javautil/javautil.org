package org.javautil.dataset;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class DatasetMetadataFactory {
	// TODO consider is getInstance the appropriate method name ? Overload for
	// getMutableCopy?
	public static MutableDatasetMetadata getInstance(
			final ResultSetMetaData meta) throws SQLException {
		final DatasetMetadataImpl retval = new DatasetMetadataImpl();
		int i = 1;
		ColumnMetadata cmd = null;
		int columnType = -1;
		try {
			final int columnCount = meta.getColumnCount();

			while (i <= columnCount) {
				cmd = new ColumnMetadata();
				cmd.setColumnDisplaySize(meta.getColumnDisplaySize(i));
				cmd.setColumnName(meta.getColumnLabel(i));
				cmd.setColumnType(meta.getColumnType(i));
				cmd.setColumnTypeName(meta.getColumnTypeName(i));
				cmd.setDataType(DataType.getAsDataType(meta.getColumnType(i)));

				/*
				 * Nasty little hack, oracle blows up when asking for precision
				 * on a blob or clob squirrely errror sometimes returns -1 other
				 * times blows up when trying to return 2^32
				 */
				switch (columnType) {
				case Types.VARCHAR:
				case Types.NVARCHAR:
				case Types.CHAR:
				case Types.FLOAT:
				case Types.DECIMAL:
				case Types.NUMERIC:
				case Types.DOUBLE:
				case Types.INTEGER:
				case Types.LONGNVARCHAR:
				case Types.LONGVARCHAR:
					cmd.setPrecision(meta.getPrecision(i));
					break;

				}
				// cmd.setPrecision(meta.getPrecision(i));
				cmd.setScale(meta.getScale(i));
				retval.addColumn(cmd);
				i++;
			}
			return retval;
		} catch (final Exception wtf) {
			final String message = "At column " + i + " type " + columnType
					+ " " + cmd.toString() + " encountered " + wtf.getMessage();
			throw new RuntimeException(message);
		}
	}

	/**
	 * returns a new mutable even if the argument was already mutable
	 * 
	 * @param meta
	 * @return
	 */
	public static MutableDatasetMetadata getMutableCopy(
			final DatasetMetadata meta) {
		final DatasetMetadataImpl retval = new DatasetMetadataImpl();
		for (int i = 0; i < meta.getColumnCount(); i++) {
			final ColumnMetadata cmd = new ColumnMetadata();
			cmd.setColumnDisplaySize(meta.getColumnDisplaySize(i));
			cmd.setColumnName(meta.getColumnName(i));
			cmd.setColumnTypeName(meta.getColumnTypeName(i));
			cmd.setDataType(meta.getColumnType(i));
			cmd.setPrecision(meta.getPrecision(i));
			cmd.setScale(meta.getScale(i));
			retval.addColumn(cmd);
		}
		return retval;
	}

	/**
	 * Returns the argument if it is mutable else returns a mutable copy.
	 * 
	 * 
	 * @param meta
	 * @return
	 */
	public static MutableDatasetMetadata getMutable(final DatasetMetadata meta) {
		if (meta == null) {
			throw new IllegalArgumentException("meta is null");
		}
		MutableDatasetMetadata retval = null;
		if (meta instanceof MutableDatasetMetadata) {
			retval = (MutableDatasetMetadata) meta;
		} else {
			retval = getMutableCopy(meta);
		}
		return retval;
	}
}
