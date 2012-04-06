package com.dbexperts.oracle.apex;

public class ApexApplicationBean {
    private int id;
    private String applicationName;
    private String workspaceName;
    public ApexApplicationBean(int id, String applicationName,
	    String workspaceName) {
	super();
	this.id = id;
	this.applicationName = applicationName;
	this.workspaceName = workspaceName;
    }
    public String getApplicationName() {
        return applicationName;
    }
//    public void setApplicationName(String applicationName) {
//        this.applicationName = applicationName;
//    }
    public int getId() {
        return id;
    }
//    public void setId(int id) {
//        Id = id;
//    }
    public String getWorkspaceName() {
        return workspaceName;
    }
    // public void setWorkspaceName(String workspaceName) {
    // this.workspaceName = workspaceName;
    //    }
    
    public String toString () {
	String rv = "ApplicationId: " + id + " Name: '" + applicationName + "' workspace: '" + workspaceName + "'";
	return rv;
    }
}
