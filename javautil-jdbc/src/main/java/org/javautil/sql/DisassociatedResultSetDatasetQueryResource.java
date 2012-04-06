package org.javautil.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.javautil.dataset.DisassociatedResultSetDataset;
import org.javautil.dataset.MutableDataset;

/**
 * 
 * @author jjs
 */
public class DisassociatedResultSetDatasetQueryResource extends
		BaseQueryResourceImpl {

	@SuppressWarnings("unchecked")
	public MutableDataset getDataset() {

		ResultSet rset = null;
		try {
			if (getQueryHelper() != null) {
				getQueryHelper().destroy();
			}
			QueryHelper qh = new QueryHelper(getConnection(), getQueryText());
			setQueryHelper(qh);
			rset = getQueryHelper().executeQuery(getParameters(), false);
			return DisassociatedResultSetDataset.getDataset(rset);
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (rset != null) {
					rset.close();
				}
				super.destroy();
			} catch (final SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
