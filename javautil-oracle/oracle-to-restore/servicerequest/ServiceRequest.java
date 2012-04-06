package com.dbexperts.oracle.servicerequest;

import javax.sql.DataSource;

public interface ServiceRequest {
	public void arguments(String arguments);

	public abstract Integer getSerialNumber();

	public abstract java.util.Date getStartTime();

	//public abstract void insertLog(Connection dbc) throws java.sql.SQLException;

	public boolean isArgumentListValid();

	public abstract void setDataSource(DataSource dataSource);

	public abstract void setDataSource(DataSource dataSource, String dataSourceName);

	public void setSerialNumber(Integer serialNumber);

	public abstract void setServiceRequestBean(ServiceRequestBean request);

	public Exception getProcessException();
	//public abstract void writeLog();
}