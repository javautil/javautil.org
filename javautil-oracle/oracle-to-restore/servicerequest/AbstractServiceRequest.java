package com.dbexperts.oracle.servicerequest;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.dbexperts.jdbc.DataSources;

// todo this should be Abstract Service Request
public abstract class AbstractServiceRequest extends Thread implements ServiceRequest {
	public String arguments = null;
	private final Logger requestLogger = Logger.getLogger(this.getClass().getName());
	private Integer serialNumber = null;

	private RequestArgumentParser parser = null;
	private final SimpleDateFormat dateParser = new SimpleDateFormat("yyyy/mm/dd");
	private final java.util.Date startTime = new java.util.Date();
	private DataSource dataSource;
	private DataSources dataSources;
	private ServiceRequestBean request;
	private Exception processException = null;
	public void arguments(final String arguments) {

		requestLogger.info("arguments " + arguments);

		this.arguments = arguments;
		parser = new RequestArgumentParser(arguments);

	}

	public String getArguments() {
		return arguments;
	}

	public Connection getConnection() throws SQLException {
		final Connection conn = dataSource.getConnection();
		return conn;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @return the dataSources
	 */
	public DataSources getDataSources() {
		return dataSources;
	}

	public String getParameter(final String parm) {
		String rc = null;
		try {
			rc = parser.getParameter(parm);
		} catch (final java.lang.NullPointerException n) {
			if (parser == null) {
				throw new java.lang.NullPointerException("parser is null");
			}
		}
		return rc;
	}

	public boolean getParameterBoolean(final String parm, final boolean dflt) {
		boolean rc = dflt;
		String parmValue = null;
		parmValue = getParameter(parm);
		if (parmValue != null) {
			while (true) {
				if (parmValue.equalsIgnoreCase("true")) {
					rc = true;
					break;
				}
				if (parmValue.equalsIgnoreCase("false")) {
					rc = false;
					break;
				}
				throw new java.lang.IllegalArgumentException("parameter '" + parm + "' '" + parmValue + "'"
						+ " is not true or false");
			}
		}
		return rc;
	}

	public java.sql.Date getParameterDate(final String parm) {
		java.sql.Date rc = null;
		String parmValue = null;
		try {
			parmValue = getParameter(parm);
			if (parmValue != null) {
				final java.util.Date d = dateParser.parse(parmValue);
				rc = new java.sql.Date(d.getTime());
			}
		} catch (final java.lang.Exception n) {
			throw new java.lang.IllegalArgumentException("Illegal parameter '" + parm + "' '" + parmValue + "'");
		}
		return rc;
	}

	public Integer getParameterInteger(final String parm) {
		Integer rc = null;
		String parmValue = null;
		try {
			parmValue = getParameter(parm);
			rc = new Integer(parmValue);
		} catch (final java.lang.NumberFormatException n) {
			throw new java.lang.IllegalArgumentException("Illegal parameter '" + parm + "' '" + parmValue + "'");
		}
		return rc;
	}

	public String getParameterMandatoryString(final String parm) {
		String rc = null;
		try {
			rc = getParameter(parm);
			if (rc == null) {
				throw new java.lang.IllegalArgumentException("Parameter '" + parm + " is mandatory and not provided");
			}
		} catch (final java.lang.NullPointerException n) {
			if (parser == null) {
				throw new java.lang.NullPointerException("parser is null");
			}
		}
		return rc;
	}

	public RequestArgumentParser getParser() {
		return parser;
	}

	public String getResponsePipeName() {
		if (request == null) {
			throw new IllegalStateException("request is null");
		}
		final String pipeName = request.getReturnPipe();
		return pipeName;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.javautil.oracle.serviceRequest.PipeRequest#getSerialNumber()
	 */
	public Integer getSerialNumber() {
		return serialNumber;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.javautil.oracle.serviceRequest.PipeRequest#getStartTime()
	 */
	public java.util.Date getStartTime() {
		return startTime;
	}

	public void setDataSource(final DataSource dataSource, final String dataSourceName) {
		setDataSource(dataSource);
	}

	public void setDataSource(final DataSource dataSource) {

		if (dataSource == null) {
			throw new IllegalArgumentException("dataSource is null");
		}
		this.dataSource = dataSource;
	}
	/**
	 * @param dataSources
	 *            the dataSources to set
	 */
	public void setDataSources(final DataSources dataSources) {
		this.dataSources = dataSources;
	}


	public final void setSerialNumber(final Integer serialNumber) {
		if (serialNumber == null) {
			throw new IllegalArgumentException("may not set serial number to null, if you don't care, don't call this");
		}
		if (this.serialNumber != null) {
			throw new java.lang.IllegalStateException("attempt to set serial number, but it has already been set");
		}
		this.serialNumber = serialNumber;
	}

	public void setServiceRequestBean(final ServiceRequestBean request) {
		this.request = request;
	}

	//todo format
	protected void logException(final Exception e) {
		final StringBuffer buff = new StringBuffer();
		String message = null;
		final Logger logger = Logger.getLogger(this.getClass().getName());
		buff.append("============================");
		buff.append("RequestThread severe error '");
		buff.append(e.getMessage());
		buff.append("'\n");
		buff.append("====  arguments  ====\n");
		buff.append(parser.toString());
		buff.append("==== stack trace ====\n");
		message = new String(buff);
		logger.error("RequestThread.logException ++++ " + message);
	}

	/**
	 * Only waits 1 second for the listener to take in the response.
	 *
	 * @todo check what happens with large values
	 * @param conn
	 * @param pipeName
	 * @param response
	 * @throws SQLException
	 */
	protected void respond(final Connection conn, final String pipeName, final String response) throws SQLException {
		if (pipeName == null) {
			throw new IllegalArgumentException("response pipe name is null");
		}
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		if (response == null) {
			throw new IllegalArgumentException("response is null");
		}
		// todo oracle this only works with oracle
		final String respondText = " declare  \n" + "     pipe_rc number := 0; \n " + " begin \n "
				+ "       dbms_pipe.pack_message(:response);\n" + "       pipe_rc := dbms_pipe.send_message(:pipe_nm,1); \n"
				+ " end; \n ";
		final CallableStatement respondStatement = conn.prepareCall(respondText);
		respondStatement.setString(1, response);
		respondStatement.setString(2, pipeName);
		respondStatement.execute();
		respondStatement.close();
	}



	public final Exception getProcessException() {
		return processException;
	}

	public final void setProcessException(final Exception processException) {
		this.processException = processException;
	}
}
