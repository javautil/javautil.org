package org.javautil.address.service;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.javautil.oracle.servicerequest.ServiceRequestBean;

//import org.javautil.jdbc.datasources.Datasources;

// todo this should be Abstract Service Request
public abstract class AbstractAddressValidation {
	public String arguments = null;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private final SimpleDateFormat dateParser = new SimpleDateFormat(
			"yyyy/mm/dd");
	private final java.util.Date startTime = new java.util.Date();

	// private Datasources dataSources;
	private ServiceRequestBean request;
	private Exception processException = null;
	
	

	// private Connection conn;

	// public Connection getConnection() throws SQLException {
	// if (conn == null) {
	// conn = dataSource.getConnection();
	// }
	// return conn;
	// }

	// public DataSource getDataSource() {
	// return dataSource;
	// }

	// /**
	// * @return the dataSources
	// */
	// public DataSources getDataSources() {
	// return dataSources;
	// }

	// public String getParameter(final String parm) {
	// String rc = null;
	// try {
	// rc = parser.getParameter(parm);
	// } catch (final java.lang.NullPointerException n) {
	// if (parser == null) {
	// throw new java.lang.NullPointerException("parser is null");
	// }
	// }
	// return rc;
	// }
	//
	// public boolean getParameterBoolean(final String parm, final boolean dflt)
	// {
	// boolean rc = dflt;
	// String parmValue = null;
	// parmValue = getParameter(parm);
	// if (parmValue != null) {
	// while (true) {
	// if (parmValue.equalsIgnoreCase("true")) {
	// rc = true;
	// break;
	// }
	// if (parmValue.equalsIgnoreCase("false")) {
	// rc = false;
	// break;
	// }
	// throw new java.lang.IllegalArgumentException("parameter '"
	// + parm + "' '" + parmValue + "'"
	// + " is not true or false");
	// }
	// }
	// return rc;
	// }
	//
	// public java.sql.Date getParameterDate(final String parm) {
	// java.sql.Date rc = null;
	// String parmValue = null;
	// try {
	// parmValue = getParameter(parm);
	// if (parmValue != null) {
	// final java.util.Date d = dateParser.parse(parmValue);
	// rc = new java.sql.Date(d.getTime());
	// }
	// } catch (final java.lang.Exception n) {
	// throw new java.lang.IllegalArgumentException("Illegal parameter '"
	// + parm + "' '" + parmValue + "'");
	// }
	// return rc;
	// }
	//
	// public Integer getParameterInteger(final String parm) {
	// Integer rc = null;
	// String parmValue = null;
	// try {
	// parmValue = getParameter(parm);
	// rc = new Integer(parmValue);
	// } catch (final java.lang.NumberFormatException n) {
	// throw new java.lang.IllegalArgumentException("Illegal parameter '"
	// + parm + "' '" + parmValue + "'");
	// }
	// return rc;
	// }

	// public String getParameterMandatoryString(final String parm) {
	// String rc = null;
	// try {
	// rc = getParameter(parm);
	// if (rc == null) {
	// throw new java.lang.IllegalArgumentException("Parameter '"
	// + parm + " is mandatory and not provided");
	// }
	// } catch (final java.lang.NullPointerException n) {
	// if (parser == null) {
	// throw new java.lang.NullPointerException("parser is null");
	// }
	// }
	// return rc;
	// }
	//
	// public RequestArgumentParser getParser() {
	// return parser;
	// }

	public String getResponsePipeName() {
		if (request == null) {
			throw new IllegalStateException("request is null");
		}
		final String pipeName = request.getReturnPipe();
		return pipeName;
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see com.javautil.oracle.serviceRequest.PipeRequest#getSerialNumber()
	// */
	// @Override
	// public Integer getSerialNumber() {
	// return serialNumber;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.javautil.oracle.serviceRequest.PipeRequest#getStartTime()
	 */
	public java.util.Date getStartTime() {
		return startTime;
	}

	// @Override
	// public void setDataSource(final DataSource dataSource,
	// final String dataSourceName) {
	// setDataSource(dataSource);
	// }

	// /**
	// * @param dataSources
	// * the dataSources to set
	// */
	// public void setDataSources(final DataSources dataSources) {
	// this.dataSources = dataSources;
	// }

	// @Override
	// public final void setSerialNumber(final Integer serialNumber) {
	// if (serialNumber == null) {
	// throw new IllegalArgumentException(
	// "may not set serial number to null, if you don't care, don't call this");
	// }
	// if (this.serialNumber != null) {
	// throw new java.lang.IllegalStateException(
	// "attempt to set serial number, but it has already been set");
	// }
	// this.serialNumber = serialNumber;
	// }

	// @Override
	// public void setServiceRequestBean(final ServiceRequestBean request) {
	// this.request = request;
	// }

	// // todo format
	// protected void logException(final Exception e) {
	// final StringBuffer buff = new StringBuffer();
	// String message = null;
	// final Logger logger = Logger.getLogger(this.getClass().getName());
	// buff.append("============================");
	// buff.append("RequestThread severe error '");
	// buff.append(e.getMessage());
	// buff.append("'\n");
	// buff.append("====  arguments  ====\n");
	// buff.append(parser.toString());
	// buff.append("==== stack trace ====\n");
	// message = new String(buff);
	// logger.error("RequestThread.logException ++++ " + message);
	// }

	public final Exception getProcessException() {
		return processException;
	}

	public final void setProcessException(final Exception processException) {
		this.processException = processException;
	}
}
