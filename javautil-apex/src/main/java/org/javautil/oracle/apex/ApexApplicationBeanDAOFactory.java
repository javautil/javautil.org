package org.javautil.oracle.apex;

import java.util.Collection;

public class ApexApplicationBeanDAOFactory {
	// TODO what is this?
	public static ApexApplicationDAO forIds(final Collection<Integer> appIds) {
		final ApexApplicationDAO rv = new ApexApplicationsForApplicationIdDAO(
				appIds);
		return rv;
	}

	public static ApexApplicationDAO forWorkspaceNameMask(
			final Collection<String> workspaceNameMask) {
		final ApexApplicationDAO rv = new ApexApplicationsForWorkspacesDAO(
				workspaceNameMask);
		return rv;
	}
}
