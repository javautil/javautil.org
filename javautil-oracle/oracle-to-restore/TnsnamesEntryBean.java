package com.dbexperts.oracle;

public class TnsnamesEntryBean {
	private String hostName;

	private Integer port;

	private String serviceName;

	private String entryName;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(final String hostName) {
		this.hostName = hostName;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(final Integer port) {
		this.port = port;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(final String serviceName) {
		this.serviceName = serviceName;
	}

	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(final String entryName) {
		this.entryName = entryName;
	}

	@Override
	public String toString() {
		return "name: '"  + entryName + "' host: '"  + hostName + "' port: " + port + " serviceName: '" + serviceName + "'";
	}
}