package org.javautil.dataset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 *         todo jjs should this be fully disassociated
 * 
 */
public class DisassociatedResultSetDataset {
	private static Logger logger = Logger
			.getLogger(DisassociatedResultSetDataset.class);

	/**
	 * Added to support spring's excellent out of the box JdbcTemplate
	 * 
	 * http://stackoverflow.com/questions/212569/getresultset-from-spring-jdbc
	 * 
	 * @param rowSet
	 * @return
	 * @throws SQLException
	 */
	public static MutableDataset getDataset(
			final ResultSetWrappingSqlRowSet rowSet) throws SQLException {
		return getDataset(rowSet.getResultSet(), null);
	}

	/**
	 * Don't forget to close your ResultSet and statement.
	 * 
	 * @param rset
	 * @return
	 * @throws SQLException
	 */
	public static MutableDataset getDataset(final ResultSet rset)
			throws SQLException {
		return getDataset(rset, null);
	}

	/**
	 * Added to support spring's excellent out of the box JdbcTemplate
	 * 
	 * http://stackoverflow.com/questions/212569/getresultset-from-spring-jdbc
	 * 
	 * @param rowSet
	 * @param rowMax
	 * @return
	 * @throws SQLException
	 */
	public static MutableDataset getDataset(
			final ResultSetWrappingSqlRowSet rowSet, final Integer rowMax)
			throws SQLException {
		return getDataset(rowSet.getResultSet(), rowMax);
	}

	/**
	 * Don't forget to close your ResultSet and statement.
	 * 
	 * @param rset
	 * @param maxRowCount
	 * @return a MutableDataset
	 * @throws SQLException
	 */
	public static MutableDataset getDataset(final ResultSet rset,
			final Integer maxRowCount) throws SQLException {
		if (rset == null) {
			throw new IllegalArgumentException("rset is null");
		}
		final MutableDatasetMetadata meta = DatasetMetadataFactory
				.getInstance(rset.getMetaData());
		final MatrixDataset dataset = new MatrixDataset(meta);
		final int columnCount = rset.getMetaData().getColumnCount();
		int rowCount = 0;

		while (rset.next()) {
			if (maxRowCount != null && rowCount == maxRowCount) {
				throw new IllegalArgumentException(
						"too many rows returned; maximum expected was "
								+ maxRowCount);
			}
			final ArrayList<Object> row = new ArrayList<Object>(columnCount);
			for (int i = 1; i <= columnCount; i++) {
				row.add(rset.getObject(i));
			}
			dataset.addRow(row);
			rowCount++;
		}
		logger.debug("rows returned " + rowCount);
		return dataset;
	}

}
