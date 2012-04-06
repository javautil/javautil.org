package org.javautil.oracle.apex;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class ApexApplicationsForWorkspacesDAO implements ApexApplicationDAO {
	private Collection<String> workspaceNames = null;

	private final Logger logger = Logger.getLogger(this.getClass());

	public ApexApplicationsForWorkspacesDAO(
			final Collection<String> workspaceNames) {
		super();
		if (workspaceNames == null) {
			throw new IllegalArgumentException("workspaceNames is null");
		}
		this.workspaceNames = workspaceNames;
	}

	/*
	 */
	public Collection<ApexApplicationBean> getApplications(final Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		final TreeMap<Integer, ApexApplicationBean> applications = new TreeMap<Integer, ApexApplicationBean>();
		PreparedStatement selectStmt;
		// prepare statement
		final String applicationSelect = //
		"select application_id, application_name, workspace " + //
				"from apex_applications " + //
				"where workspace like upper(:workspace_name) and workspace != 'INTERNAL' ";
		try {
			selectStmt = conn.prepareStatement(applicationSelect);
			for (final String workspaceMask : workspaceNames) {
				selectStmt.setString(1, workspaceMask);
				final ResultSet rset = selectStmt.executeQuery();
				boolean found = false;
				while (rset.next()) {
					found = true;
					final int appId = rset.getInt(1);
					final String appName = rset.getString(2);
					final String workspaceName = rset.getString(3);
					final ApexApplicationBean app = new ApexApplicationBean(
							appId, appName, workspaceName);
					applications.put(appId, app);
				}
				if (!found) {
					logger.warn("no applications found in workspace '"
							+ workspaceMask + "'");
				}
				rset.close();
			}
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		} 
		return applications.values();
	}
}
