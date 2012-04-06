package org.javautil.oracle.apex;

public class ApexApplicationBean {
	private final int id;
	private final String applicationName;
	private final String workspaceName;

	public ApexApplicationBean(final int id, final String applicationName,
			final String workspaceName) {
		super();
		this.id = id;
		this.applicationName = applicationName;
		this.workspaceName = workspaceName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	// public void setApplicationName(String applicationName) {
	// this.applicationName = applicationName;
	// }
	public int getId() {
		return id;
	}

	// public void setId(int id) {
	// Id = id;
	// }
	public String getWorkspaceName() {
		return workspaceName;
	}

	// public void setWorkspaceName(String workspaceName) {
	// this.workspaceName = workspaceName;
	// }

	@Override
	public String toString() {
		final String rv = "ApplicationId: " + id + " Name: '" + applicationName
				+ "' workspace: '" + workspaceName + "'";
		return rv;
	}
}
