package com.dbexperts.oracle.servicerequest;

import java.sql.CallableStatement;

import oracle.jdbc.OracleConnection;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dbexperts.oracle.RdbmsPipeException;
import com.dbexperts.oracle.WrappedOracleConnection;

class ServiceRequestPipeListener {
	private static final Logger	logger			= Logger.getLogger(ServiceRequestPipeListener.class.getName());
	private static final String	procedure		= " declare  \n" + "     pipe_rc number := 0; \n " + " begin \n "
														+ "       pipe_rc := dbms_pipe.receive_message(?,60); \n"
														+ "		   IF ( pipe_rc = 0 ) THEN \n "
														+ "				dbms_pipe.unpack_message(?) ; \n "
														+ "				dbms_pipe.unpack_message(?) ; \n "
														+ "				dbms_pipe.unpack_message(?) ; \n "
														+ "			END IF ; \n "
														+ "			? := pipe_rc;  \n" + " end; \n " + "";
	private String				requestPipeName	= "PipeRequest";
	private CallableStatement			getStmt			= null;

	/**
	 * @todo propertyName needs to be fixed
	 */
	public ServiceRequestPipeListener(final String pipeName) {
		requestPipeName = pipeName;
		logger.setLevel(Level.DEBUG);

	}

	/**
	 * Listens for requests on pipeName specified in constructor.
	 *
	 * Expects that messages written to the specified pipe are formatted by the javautil provided
	 * Oracle plsql package service_rqst.
	 *
	 * Loops on listening for 60 seconds.  The 60 second timeout is not configurable as its only purpose is to
	 * prevent this from blocking on a ready making the session non killable from Oracle.
	 *
	 * @param connection
	 * @return ServiceRequestPipe
	 * @exception java.sql.SQLException
	 */
	public ServiceRequestBean get(final OracleConnection connection) throws java.sql.SQLException {
		if (connection == null) {
			throw new IllegalArgumentException("connection is null");
		}
		final WrappedOracleConnection wrappedConn = WrappedOracleConnection.getInstance(connection);

		wrappedConn.setModule(this.getClass().getName());
		int callRc;
		String serviceName = null;
		String returnPipe = null;
		String args = null;
		final ServiceRequestBean request = new ServiceRequestBean();
		boolean waiting = true;
		boolean ok = true;
		try {
			if (getStmt == null) {
				getStmt = wrappedConn.prepareCall(procedure);
				getStmt.registerOutParameter(2, java.sql.Types.VARCHAR);
				getStmt.registerOutParameter(3, java.sql.Types.VARCHAR);
				getStmt.registerOutParameter(4, java.sql.Types.VARCHAR);
				getStmt.registerOutParameter(5, java.sql.Types.NUMERIC);
			}
			while (waiting && ok) {
				wrappedConn.setAction("waiting on pipe '" + requestPipeName + "'");
				//logger.debug("ServiceRequestPipeListener waiting on pipe '" + requestPipeName + "'");
				getStmt.setString(1, requestPipeName);
				getStmt.executeUpdate();
				serviceName = getStmt.getString(2);
				request.setServiceName(serviceName);
				returnPipe = getStmt.getString(3);
				request.setReturnPipe(returnPipe);
				args = getStmt.getString(4);
				callRc = getStmt.getInt(5);
				switch (callRc) {
					case 0:
						waiting = false;
						break;
					case 1: // timeOut, no request within 60, seconds
						logger.debug("no request received in 60 seconds on pipe '" + requestPipeName + "'");
						break;
					//throw new com.javautil.oracle.RdbmsPipeReadTimeOutException();
					case 2:
						throw new RdbmsPipeException();
					default:
						throw new IllegalStateException("unknown return value from read pipe " + callRc);
				}
			}
			wrappedConn.setAction("setServiceName" + serviceName);
			logger.debug("request for service: '" + serviceName + "'");
			// System.out.println("ServiceRequestPipeListener serviceName " + serviceName);
			request.setArgs(args);
			//      StringBuffer entry = new StringBuffer(1024);
			//            entry.append(getEntry("serviceName", serviceName));
			//            if (returnPipe != null) {
			//                entry.append(getEntry("returnPipe", returnPipe));
			//            }
			//            entry.append(getEntry("args", args));
			//
			//            logger.info(new String(entry));
			logger.info("received request\n" + args);
			return request;
		} catch (final java.sql.SQLException e) {
			ok = false;
			logger.error(e.getMessage());
			//+ "\n" + LoggerHelper.getStackTrace(e));
			throw (java.sql.SQLException) e.fillInStackTrace();
		}
	}

}
