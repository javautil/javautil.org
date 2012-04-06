package org.javautil.dataset;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

public class OracleSqlDataset extends SQLDataset {

	private static Logger logger = Logger.getLogger(OracleSqlDataset.class);

	/**
	 * Obtains a DataSet using the given procedure call.
	 * 
	 * @return
	 * @throws DataSetException
	 */
	public static MutableDataset getDatasetFromCall(final Connection conn,
			final String query, final Map<String, Object> parms)
			throws DatasetException {
		try {

			try {
				logger.debug("Executing Query:\n" + query);
				final CallableStatement cstat = conn.prepareCall(query);
				try {
					cstat.registerOutParameter(1, OracleTypes.CURSOR);
					cstat.execute();
					final ResultSet rset = (ResultSet) cstat.getObject(1);
					try {
						return DisassociatedResultSetDataset.getDataset(rset);
					} finally {
						rset.close();
					}
				} finally {
					cstat.close();
				}
			} finally {
				conn.close();
			}
		} catch (final Exception ex) {
			throw new DatasetException("while executing '" + query + "'" + ex);
		}
	}
}
