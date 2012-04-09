package org.javautil.address.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.address.UtAddressValidate;
import org.javautil.address.service.usps.AbstractAddressValidationService;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.persistence.PersistenceException;

/**
 * <p>
 * AddressValidationJdbc class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressValidationServiceJdbc.java,v 1.1 2012/04/09 01:29:06 jjs Exp $
 */
public class AddressValidationServiceJdbc extends AbstractAddressValidationService {

	/** Constant <code>revision="$Revision: 1.1 $"</code> */
	public static final String revision = "$Revision: 1.1 $";

	static final String GEO_USER_ID = "GEO_USER_ID";

	static final String GEO_PASSWORD = "GEO_PASSWORD";

	private Connection conn;

	private AddressValidationServiceArguments arguments;

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * Constructor for AddressValidationJdbc.
	 * </p>
	 */
	public AddressValidationServiceJdbc() {
		logger.info("instantiated " + revision);
	}

	// TODO find old version and create subclass which implements acknowledge

	/** {@inheritDoc} */
	@Override
	protected void wrapUp() throws PersistenceException {
		logger.info(getValidator().getStatisticsMessage());
		try {
			conn.commit();
		} catch (final SQLException sqe) {
			throw new PersistenceException(sqe);
		}
		// acknowledge();
		try {
			dispose();
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	private void dispose() throws SQLException {
		conn.close();

	}

	/** {@inheritDoc} */
	@Override
	protected void conditionalCommit() throws PersistenceException {
		if (getUncommittedCount() >= getCommitRecordCount()) {
			try {
				conn.commit();
				setUncommittedCount(0);
				if (logger.isDebugEnabled()) {
					logger.debug("committed after " + getProcessedCount()
							+ "records");
				}
			} catch (final SQLException e) {
				throw new PersistenceException(e);
			}
		}
	}

	@Override
	protected void persistChanges() throws PersistenceException {
		final ArrayList<UtAddressValidate> unpersistable = getPersister()
				.update(getAddresses());
		for (final UtAddressValidate addr : unpersistable) {
			// logger.error(addr.toString()
			// + addr.getPersistenceException().getMessage());
		}
		try {
			conn.commit(); // TODO this should be conditional commit
		} catch (final SQLException e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * <p>
	 * Getter for the field <code>arguments</code>.
	 * </p>
	 * 
	 * @return the arguments
	 */
	public AddressValidationServiceArguments getArguments() {
		return arguments;
	}

	/**
	 * <p>
	 * Setter for the field <code>arguments</code>.
	 * </p>
	 * 
	 * @param arguments
	 *            the arguments to set
	 */
	public void setArguments(AddressValidationServiceArguments arguments) {
		this.arguments = arguments;
	}

	// @Override
	// public void arguments(final String arguments) {
	//
	// logger.info("arguments " + arguments);
	//
	// this.arguments = arguments;
	// parser = new RequestArgumentParser(arguments);
	//
	// }

	/**
	 * <p>
	 * setDataSource.
	 * </p>
	 * 
	 * @param dataSource
	 *            a {@link javax.sql.DataSource} object.
	 */
	public void setDataSource(final DataSource dataSource) {

		if (dataSource == null) {
			throw new IllegalArgumentException("dataSource is null");
		}
	}

	/**
	 * <p>
	 * setConnection.
	 * </p>
	 * 
	 * @param conn
	 *            a {@link java.sql.Connection} object.
	 */
	public void setConnection(final Connection conn) {
		logger.info("Setting connection to : " + conn);
		this.conn = conn;
	}

	/**
	 * Only waits 1 second for the listener to take in the response.
	 * 
	 * TODO check what happens with large values
	 * 
	 * @param conn
	 *            connection on which
	 * @param pipeName
	 * @param response
	 * @throws SQLException
	 */
	// protected void respond(final Connection conn, final String pipeName,
	// final String response) throws SQLException {
	// if (pipeName == null) {
	// throw new IllegalArgumentException("response pipe name is null");
	// }
	// if (conn == null) {
	// throw new IllegalArgumentException("conn is null");
	// }
	// if (response == null) {
	// throw new IllegalArgumentException("response is null");
	// }
	// // todo oracle this only works with oracle
	// final String respondText = " declare  \n"
	// + "     pipe_rc number := 0; \n " + " begin \n "
	// + "       dbms_pipe.pack_message(:response);\n"
	// + "       pipe_rc := dbms_pipe.send_message(:pipe_nm,1); \n"
	// + " end; \n ";
	// final CallableStatement respondStatement = conn
	// .prepareCall(respondText);
	// respondStatement.setString(1, response);
	// respondStatement.setString(2, pipeName);
	// respondStatement.execute();
	// respondStatement.close();
	// }

	// @Override
	// public void arguments(String arguments) {
	// // TODO Auto-generated method stub
	//
	// }

	/**
	 * <p>
	 * main.
	 * </p>
	 * 
	 * @param args
	 *            an array of {@link java.lang.String} objects.
	 * @throws java.sql.SQLException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public static void main(final String args[]) throws PersistenceException,
			AddressValidationException, SQLException {

		final AddressValidationServiceJdbc as = new AddressValidationServiceJdbc();
		final AddressValidationServiceArguments arguments = new AddressValidationServiceArguments();
		final CommandLineHandler clh = new CommandLineHandler(arguments);
		clh.evaluateArguments(args);
		as.process(arguments);
	}

}
