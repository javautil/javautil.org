package org.javautil.oracle.apex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeMap;

public class ApexApplicationsForApplicationIdDAO implements ApexApplicationDAO {
	private Collection<Integer> appIds = null;

	public ApexApplicationsForApplicationIdDAO(final Collection<Integer> appIds) {
		super();
		if (appIds == null) {
			throw new IllegalArgumentException("appIds is null");
		}

		this.appIds = appIds;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.apex.ApexApplicationDAO#getApplications(java.sql
	 * .Connection)
	 */
	public Collection<ApexApplicationBean> getApplications(final Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		final TreeMap<Integer, ApexApplicationBean> applications = new TreeMap<Integer, ApexApplicationBean>();
		PreparedStatement selectStmt;
		// prepare statement
		final String applicationSelect = "select application_id, application_name, workspace "
				+ //
				"from apex_applications " + //
				"where application_id =  :appid ";
		try {
			selectStmt = conn.prepareStatement(applicationSelect);
			for (final Integer id : appIds) {
				selectStmt.setInt(1, id);
				final ResultSet rset = selectStmt.executeQuery();
				while (rset.next()) {
					final int appId = rset.getInt(1);
					final String appName = rset.getString(2);
					final String workspaceName = rset.getString(3);
					final ApexApplicationBean app = new ApexApplicationBean(
							appId, appName, workspaceName);
					applications.put(appId, app);
				}
			}
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		} finally {

		}

		return applications.values();

	}
}
