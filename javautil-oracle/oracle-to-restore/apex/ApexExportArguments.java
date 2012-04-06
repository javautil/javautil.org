package com.dbexperts.oracle.apex;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.javautil.commandline.ParamType;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;

public class ApexExportArguments {
    @Optional
    private boolean verbose = false;

    @org.javautil.commandline.annotations.Optional
    private boolean skipDate = true;

    @Optional
    @MultiValue(type=ParamType.STRING)
    // todo jjs - isn't this obviously multivalued as it is a collection ?
    // todo jjs how do I make this exclusive 
  //  @Exclusive(property= "applicationId");
    // todo how do we show the default setting
    private Collection<String> workspaceName;

    @Optional
    @MultiValue(type=ParamType.INTEGER)
    private Collection<Integer> applicationId;

    @Optional
    private File exportDirectory = new File(".");

    @Required
    private String databaseURL = null;

    @Required
    private String userName = null;

    @Required
    private String password = null;
    
    @Optional
    private boolean showAppInfoOnly;

    public boolean isShowAppInfoOnly() {
        return showAppInfoOnly;
    }

    public void setShowAppInfoOnly(boolean showAppInfoOnly) {
        this.showAppInfoOnly = showAppInfoOnly;
    }

    public boolean isVerbose() {
	return verbose;
    }

    public void setVerbose(boolean verbose) {
	this.verbose = verbose;
    }

    public boolean isSkipDate() {
	return skipDate;
    }

    public void setSkipDate(boolean skipDate) {
	this.skipDate = skipDate;
    }

    public Collection<String> getWorkspaceName() {
	if (workspaceName == null) {
	    ArrayList<String> al = new ArrayList<String>();
	    al.add("%");
	    workspaceName = al;
	}
	return workspaceName;
    }

    public void setWorkspaceName(Collection<String> workspaceName) {
	this.workspaceName = workspaceName;
    }

    public Collection<Integer> getApplicationId() {
//	// todo nasty little hack
//	Collection<String> ids = (Collection<String>) applicationId;
	ArrayList<Integer> rv = null;
//	for (String id : (String) applicationId) {
//	   
//	}
	// todo this is really bad, this returns a collection of String
	if (applicationId != null) {
	    rv = new ArrayList<Integer>();
	for (Object v : applicationId) {
	
	    String p = (String) v;
	    Integer q = Integer.parseInt(p);
	    rv.add(q);
	}
	}
	return rv;
    }

    public void setApplicationId(Collection<Integer> applicationId) {
	this.applicationId = applicationId;
    }

    public File getExportDirectory() {
	return exportDirectory;
    }

    public void setExportDirectory(File exportDirectory) {
	this.exportDirectory = exportDirectory;
    }

    public String getDatabaseURL() {
	return databaseURL;
    }

    public void setDatabaseURL(String databaseURL) {
	this.databaseURL = databaseURL;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }
}
