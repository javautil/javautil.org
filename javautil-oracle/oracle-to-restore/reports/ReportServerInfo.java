package com.dbexperts.oracle.reports;

public class ReportServerInfo {


    //todo mab document 
	/**
	 * The host name of the computer that is running the report server.
	 */
    private String reportServerName;
    
    /**
     * The IP port the report server is listening on 
     */
    private int    serverPort;
    
    /**
     * The reportServer configuration entry that maps to the userid/password for 
     * the connecting to create the report.
     */
    private String reportServerConfigurationEntry;
    
    /**
     * The application path, for windows this should be
     * dev60cgi/rwcgi60.exe
     */
    private String applicationPath;



	/**
	 * @return the reportServerName
	 */
	public String getReportServerName() {
		return reportServerName;
	}

	/**
	 * @param reportServerName the reportServerName to set
	 */
	public void setReportServerName(String reportServerName) {
		this.reportServerName = reportServerName;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the reportServerConfigurationEntry
	 */
	public String getReportServerConfigurationEntry() {
		return reportServerConfigurationEntry;
	}

	/**
	 * @param reportServerConfigurationEntry the reportServerConfigurationEntry to set
	 */
	public void setReportServerConfigurationEntry(String reportServerConfigurationEntry) {
		this.reportServerConfigurationEntry = reportServerConfigurationEntry;
	}

	/**
	 * @return the applicationPath
	 */
	public String getApplicationPath() {
		return applicationPath;
	}

	/**
	 * @param applicationPath the applicationPath to set
	 */
	public void setApplicationPath(String applicationPath) {
		this.applicationPath = applicationPath;
	}
    
    
  
}
