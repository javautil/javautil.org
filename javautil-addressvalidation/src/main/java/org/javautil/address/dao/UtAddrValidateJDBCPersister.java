package org.javautil.address.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.javautil.address.UtAddressValidate;
import org.javautil.persistence.PersistenceException;

/**
 * <p>
 * UtAddrValidateJDBCPersister class.
 * </p>
 * 
 * @author jjs
 * @version $Id: UtAddrValidateJDBCPersister.java,v 1.4 2012/03/04 12:31:14 jjs
 *          Exp $
 */
public class UtAddrValidateJDBCPersister implements AddressPersistence {
	int insertBatchSize = 100;
	private Connection conn;

	static final String selectText = "SELECT ut_addr_validate_nbr,  "
			+ "run_nbr,  "
			+ "data_src_nbr,\n"
			+ "    data_src_pk,           name,\n"
			+ "     raw_addr_1,           raw_addr_2,    raw_addr_3,    raw_city,  raw_state_cd,  raw_cntry_cd,  raw_postal_cd,\n"
			+ "    std_po_box,            std_street_name,       std_street_nbr,    std_street_type,  std_subunit_cd,  std_subunit_type,\n"
			+ "    std_addr_1,\n"
			+ "    std_addr_2,\n"
			+ "    std_city,\n"
			+ "    std_state_cd,    std_postal_cd, \n"
			+ "    auth_addr_1,\n"
			+ "    auth_addr_2,\n"
			+ "    auth_city,\n"
			+ "    auth_state_cd,  auth_postal_cd,\n"
			+ "    auth_rqst_cd,         auth_latitude, auth_longitude, "
			+ " std_auth_addr_1,  std_auth_addr_2, std_auth_city,  std_auth_state_cd,   std_auth_postal_cd, \n"
			+ "    std_ts,  std_err_msg,    auth_err_msg,  std_auth_err_msg \n"
			+ "FROM UT_ADDR_VALIDATE\n";

	private PreparedStatement insertStmt = null;
	/**
	 * sql text for inserting all rows into the table
	 */
	static String insertText = "insert into UT_ADDR_VALIDATE "
			+ "(\n"
			+ "ut_addr_validate_nbr,\n"
			+ "run_nbr,\n"
			+ "data_src_nbr,\n"
			+ "data_src_pk,\n"
			+ "name,\n"
			+ "raw_addr_1,\n"
			+ "raw_addr_2,\n"
			+ "raw_addr_3,\n"
			+ "raw_city,\n"
			+ "raw_state_cd,\n"
			+ "raw_cntry_cd,\n"
			+ "raw_postal_cd,\n"
			+ "std_po_box,\n"
			+ "std_street_name,\n"
			+ "std_street_nbr,\n"
			+ "std_street_type,\n"
			+ "std_subunit_cd,\n"
			+ "std_subunit_type,\n"
			+ "std_addr_1,\n"
			+ "std_addr_2,\n"
			+ "std_city,\n"
			+ "std_state_cd,\n"
			+ "std_postal_cd,\n"
			+ "auth_addr_1,\n"
			+ "auth_addr_2,\n"
			+ "auth_city,\n"
			+ "auth_state_cd,\n"
			+ "auth_postal_cd,\n"
			+ "auth_rqst_cd, auth_latitude, auth_longitude, \n"
			+ "std_auth_addr_1,\n"
			+ "std_auth_addr_2,\n"
			+ "std_auth_city,\n"
			+ "std_auth_state_cd,\n"
			+ "std_auth_postal_cd,\n"
			+ "std_ts,\n"
			+ "std_err_msg,\n"
			+ "auth_err_msg,\n"
			+ "std_auth_err_msg\n"
			+ ")\n"
			+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//
	//
	//
	private PreparedStatement selectStmt = null;
	private String selectSQL;
	private ResultSet rset;

	private int fetchedCount;
	private boolean moreRecords = true;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.address.dao.AddressPersistence#getForRunNbr(long)
	 */
	/** {@inheritDoc} */
	@Override
	public void getForRunNbr(final long runNbr) throws PersistenceException {
		if (conn == null) {
			throw new IllegalStateException("conn is null");
		}

		if (selectStmt == null) {
			selectSQL = selectText + " where run_nbr = ? "

			+ " order by ut_addr_validate_nbr";
			try {
				selectStmt = conn.prepareStatement(selectSQL);
				selectStmt.setLong(1, runNbr);
				rset = selectStmt.executeQuery();
			} catch (final SQLException e) {
				throw new PersistenceException(selectSQL, e);
			}
		}
	}

	public void getAll() {
		if (conn == null) {
			throw new IllegalStateException("conn is null");
		}

		if (selectStmt == null) {
			selectSQL = selectText + " order by ut_addr_validate_nbr";
			try {
				selectStmt = conn.prepareStatement(selectSQL);
				rset = selectStmt.executeQuery();
			} catch (final SQLException e) {
				throw new PersistenceException(selectSQL, e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.address.dao.AddressPersistence#getNext(int)
	 */
	/** {@inheritDoc} */
	@Override
	public ArrayList<UtAddressValidate> getNext(final int count)
			throws PersistenceException {
		final ArrayList<UtAddressValidate> retval = new ArrayList<UtAddressValidate>();
		for (int i = 0; i < count; i++) {
			final UtAddressValidate addr = getNext();
			if (addr != null) {
				retval.add(addr);
			} else {
				break;
			}
		}
		return retval;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.address.dao.AddressPersistence#getNext()
	 */
	/** {@inheritDoc} */
	@Override
	public UtAddressValidate getNext() throws PersistenceException {
		if (rset == null) {
			getAll();
		}
		UtAddressValidate retval = null;
		try {
			if (rset.next()) {
				fetchedCount++;
				retval = new UtAddressValidate();
				getRow(rset, retval);
			} else {
				logger.debug("no more records for run after fetching "
						+ fetchedCount);
				moreRecords = false;
				selectStmt.close();
			}
		} catch (final SQLException sqe) {
			throw new PersistenceException(selectSQL, sqe);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(retval);
		}
		return retval;
	}

	/**
	 * <p>
	 * getRow.
	 * </p>
	 * 
	 * @param rset
	 *            a {@link java.sql.ResultSet} object.
	 * @param row
	 *            a {@link org.javautil.address.UtAddressValidate} object.
	 * @throws java$sql$SQLException
	 *             if any.
	 */
	public static void getRow(final ResultSet rset, final UtAddressValidate row)
			throws java.sql.SQLException {
		String columnName = null;

		try {
			final long UtAddrValidateNbr = rset
					.getLong(columnName = "UT_ADDR_VALIDATE_NBR");
			row.setUtAddrValidateNbr(rset.wasNull() ? null : new Long(
					UtAddrValidateNbr));
			final int RunNbr = rset.getInt(columnName = "RUN_NBR");
			row.setRunNbr(rset.wasNull() ? null : new Integer(RunNbr));
			final int DataSrcNbr = rset.getInt(columnName = "DATA_SRC_NBR");
			row.setDataSrcNbr(rset.wasNull() ? null : new Integer(DataSrcNbr));
			final long DataSrcPk = rset.getLong(columnName = "DATA_SRC_PK");
			row.setDataSrcPk(rset.wasNull() ? null : new Long(DataSrcPk));
			row.setName(rset.getString(columnName = "NAME"));
			// Raw Address
			row.getRawAddress().setAddress1(
					rset.getString(columnName = "RAW_ADDR_1"));
			row.getRawAddress().setAddress2(
					rset.getString(columnName = "RAW_ADDR_2"));
			// row.getRawAddress().setAddress3(rset.getString(columnName =
			// "RAW_ADDR_3"));
			row.getRawAddress()
					.setCity(rset.getString(columnName = "RAW_CITY"));
			row.getRawAddress().setState(
					rset.getString(columnName = "RAW_STATE_CD"));
			row.getRawAddress().setCountryCode(
					rset.getString(columnName = "RAW_CNTRY_CD"));
			row.getRawAddress().setPostalCode(
					rset.getString(columnName = "RAW_POSTAL_CD"));
			// Standard Address
			row.getStdAddress().setPoBox(
					rset.getString(columnName = "STD_PO_BOX"));
			row.getStdAddress().setStreetName(
					rset.getString(columnName = "STD_STREET_NAME"));
			row.getStdAddress().setStreetNumber(
					rset.getString(columnName = "STD_STREET_NBR"));
			row.getStdAddress().setStreetType(
					rset.getString(columnName = "STD_STREET_TYPE"));
			row.getStdAddress().setSubunitCode(
					rset.getString(columnName = "STD_SUBUNIT_CD"));
			row.getStdAddress().setSubunitType(
					rset.getString(columnName = "STD_SUBUNIT_TYPE"));
			row.getStdAddress().setAddress1(
					rset.getString(columnName = "STD_ADDR_1"));
			row.getStdAddress().setAddress2(
					rset.getString(columnName = "STD_ADDR_2"));
			row.getStdAddress()
					.setCity(rset.getString(columnName = "STD_CITY"));
			row.getStdAddress().setState(
					rset.getString(columnName = "STD_STATE_CD"));
			row.getStdAddress().setPostalCode(
					rset.getString(columnName = "STD_POSTAL_CD"));
			row.getStdAddress().setStandardizationErrorMessage(
					rset.getString(columnName = "STD_ERR_MSG"));
			// Authoritative Address
			row.getAuthAddress().setAddress1(
					rset.getString(columnName = "AUTH_ADDR_1"));
			row.getAuthAddress().setAddress2(
					rset.getString(columnName = "AUTH_ADDR_2"));
			row.getAuthAddress().setCity(
					rset.getString(columnName = "AUTH_CITY"));
			row.getAuthAddress().setState(
					rset.getString(columnName = "AUTH_STATE_CD"));
			row.getAuthAddress().setPostalCode(
					rset.getString(columnName = "AUTH_POSTAL_CD"));
			final double authLatitude = rset
					.getDouble(columnName = "AUTH_LATITUDE");
			row.getAuthAddress().setLatitude(
					rset.wasNull() ? null : authLatitude);
			final double authLongitude = rset
					.getDouble(columnName = "AUTH_LONGITUDE");
			row.getAuthAddress().setLongitude(
					rset.wasNull() ? null : authLongitude);
			row.setAuthRqstCd(rset.getString(columnName = "AUTH_RQST_CD"));
			row.getAuthAddress().setAuthoritativeErrorMessage(
					rset.getString(columnName = "AUTH_ERR_MSG"));
			// Standardized Authoritative Address
			row.getStdAuthAddress().setAddress1(
					rset.getString(columnName = "STD_AUTH_ADDR_1"));
			row.getStdAuthAddress().setAddress2(
					rset.getString(columnName = "STD_AUTH_ADDR_2"));
			row.getStdAuthAddress().setCity(
					rset.getString(columnName = "STD_AUTH_CITY"));
			row.getStdAuthAddress().setState(
					rset.getString(columnName = "STD_AUTH_STATE_CD"));
			row.getStdAuthAddress().setPostalCode(
					rset.getString(columnName = "STD_AUTH_POSTAL_CD"));
			row.setStdTs(rset.getTimestamp(columnName = "STD_TS"));

			row.getStdAuthAddress().setStandardizationErrorMessage(
					rset.getString(columnName = "STD_AUTH_ERR_MSG"));
		} catch (final java.sql.SQLException s) {
			throw new java.sql.SQLException("error processing column"
					+ columnName + "\n" + s.getMessage());
		}
	} // end of getRow

	String getSelectText() {
		return selectText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.dao.AddressPersistence#insert(java.lang.Iterable)
	 */
	/** {@inheritDoc} */
	@Override
	public void insert(final Iterable<UtAddressValidate> rows)
			throws java.sql.SQLException {
		if (insertStmt == null && conn == null) {
			throw new IllegalStateException("conn is null");
		}
		if (insertStmt == null) {
			insertStmt = conn.prepareStatement(insertText);
		}
		int batchCount = 0;
		for (final UtAddressValidate row : rows) {
			int bindNbr = 1;
			insertStmt.setObject(bindNbr++, row.getUtAddrValidateNbr());
			insertStmt.setObject(bindNbr++, row.getRunNbr());
			insertStmt.setObject(bindNbr++, row.getDataSrcNbr());
			insertStmt.setObject(bindNbr++, row.getDataSrcPk());
			insertStmt.setObject(bindNbr++, row.getName());
			insertStmt.setObject(bindNbr++, row.getRawAddress().getAddress1());
			insertStmt.setObject(bindNbr++, row.getRawAddress().getAddress2());
			insertStmt.setObject(bindNbr++, row.getRawAddress().getCity());
			insertStmt.setObject(bindNbr++, row.getRawAddress().getState());
			insertStmt.setObject(bindNbr++, row.getRawAddress()
					.getCountryCode());
			insertStmt
					.setObject(bindNbr++, row.getRawAddress().getPostalCode());
			insertStmt.setObject(bindNbr++, row.getStdAddress().getPoBox());
			insertStmt
					.setObject(bindNbr++, row.getStdAddress().getStreetName());
			insertStmt.setObject(bindNbr++, row.getStdAddress()
					.getStreetNumber());
			insertStmt
					.setObject(bindNbr++, row.getStdAddress().getStreetType());
			insertStmt.setObject(bindNbr++, row.getStdAddress()
					.getSubunitCode());
			insertStmt.setObject(bindNbr++, row.getStdAddress()
					.getSubunitType());
			insertStmt.setObject(bindNbr++, row.getStdAddress().getAddress1());
			insertStmt.setObject(bindNbr++, row.getStdAddress().getAddress2());
			insertStmt.setObject(bindNbr++, row.getStdAddress().getCity());
			insertStmt.setObject(bindNbr++, row.getStdAddress().getState());
			insertStmt
					.setObject(bindNbr++, row.getStdAddress().getPostalCode());
			insertStmt.setObject(bindNbr++, row.getAuthAddress().getAddress1());
			insertStmt.setObject(bindNbr++, row.getAuthAddress().getAddress2());
			insertStmt.setObject(bindNbr++, row.getAuthAddress().getCity());
			insertStmt.setObject(bindNbr++, row.getAuthAddress().getState());
			insertStmt.setObject(bindNbr++, row.getAuthAddress()
					.getPostalCode());
			insertStmt.setObject(bindNbr++, row.getAuthRqstCd());
			insertStmt.setObject(bindNbr++, row.getAuthAddress().getLatitude());
			insertStmt
					.setObject(bindNbr++, row.getAuthAddress().getLongitude());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress()
					.getAddress1());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress()
					.getAddress2());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress().getCity());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress().getState());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress()
					.getPostalCode());
			insertStmt.setObject(bindNbr++, row.getStdTs());
			insertStmt.setObject(bindNbr++, row.getStdAddress()
					.getStandardizationErrorMessage());
			insertStmt.setObject(bindNbr++, row.getAuthAddress()
					.getAuthoritativeErrorMessage());
			insertStmt.setObject(bindNbr++, row.getStdAuthAddress()
					.getStandardizationErrorMessage());
			insertStmt.addBatch();
			batchCount++;
			if (batchCount > insertBatchSize) {
				insertStmt.executeBatch();
				batchCount = 0;
			}
		}
		if (batchCount > 0) {
			insertStmt.executeBatch();
		}
	}

	private final String updateText = "update UT_ADDR_VALIDATE \n"
			+ "set  \n"
			+ "    std_po_box = ?, \n"
			+ "    std_street_name = ?, \n"
			+ "    std_street_nbr = ?, \n"
			+ "    std_street_type = ?, \n"
			+ "    std_subunit_cd = ?, \n"
			+ "    std_subunit_type = ?, \n"
			+ "    std_addr_1 = ?, \n"
			+ "    std_addr_2 = ?, \n"
			+ "    std_city = ?, \n"
			+ "    std_state_cd = ?, \n"
			+ "    std_postal_cd = ?, \n"
			+ "    auth_addr_1 = ?, \n"
			+ "    auth_addr_2 = ?, \n"
			+ "    auth_city = ?, \n"
			+ "    auth_state_cd = ?, \n"
			+ "    auth_postal_cd = ?, auth_latitude = ?, auth_longitude = ?, \n"
			+ "    std_auth_addr_1 = ?, \n" + "    std_auth_addr_2 = ?, \n"
			+ "    std_auth_city = ?, \n" + "    std_auth_state_cd = ?, \n"
			+ "    std_auth_postal_cd = ?, \n" + "    std_ts = sysdate, \n"
			+ "    std_err_msg = ?, \n" + "    auth_err_msg = ? \n"
			+ " where ut_addr_validate_nbr = ?  \n";

	private PreparedStatement updateStdAddrStmt;
	private final Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.dao.AddressPersistence#update(java.util.Collection)
	 */
	/** {@inheritDoc} */
	@Override
	public ArrayList<UtAddressValidate> update(
			final Collection<UtAddressValidate> addresses)
			throws PersistenceException {
		final ArrayList<UtAddressValidate> unpersistable = new ArrayList<UtAddressValidate>();
		if (logger.isDebugEnabled()) {
			logger.debug("About to update " + addresses.size() + " records.");
		}
		if (addresses.size() > 0) {
			for (final UtAddressValidate addr : addresses) {
				update(addr);
			}
			try {
				updateStdAddrStmt.executeBatch();
			} catch (final SQLException e) {
				for (final UtAddressValidate addr : addresses) {
					update(addr);
					try {
						updateStdAddrStmt.executeBatch();
					} catch (final SQLException e1) {
						unpersistable.add(addr);
						// addr.setPersistenceException(e1);
					}
				}
			}
		}
		return unpersistable;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.dao.AddressPersistence#update(org.javautil.address
	 * .UtAddressValidate)
	 */
	/** {@inheritDoc} */
	@Override
	public void update(final UtAddressValidate a) throws PersistenceException {
		try {
			if (updateStdAddrStmt == null) {
				if (conn == null) {
					throw new IllegalStateException("conn has not been  set");
				}
				updateStdAddrStmt = conn.prepareStatement(updateText);
			}
			if (logger.isDebugEnabled()) {
				logger.debug(a.toString());
			}
			final PreparedStatement ps = updateStdAddrStmt;
			ps.setString(1, a.getStdAddress().getPoBox());
			ps.setString(2, a.getStdAddress().getStreetName());
			ps.setString(3, a.getStdAddress().getStreetNumber());
			ps.setString(4, a.getStdAddress().getStreetType());
			ps.setString(5, a.getStdAddress().getSubunitCode());
			ps.setString(6, a.getStdAddress().getSubunitType());
			ps.setString(7, a.getStdAddress().getAddress1());
			ps.setString(8, a.getStdAddress().getAddress2());
			ps.setString(9, a.getStdAddress().getCity());
			ps.setString(10, a.getStdAddress().getState());
			ps.setString(11, a.getStdAddress().getPostalCode());
			ps.setString(12, a.getAuthAddress().getAddress1());
			ps.setString(13, a.getAuthAddress().getAddress2());
			ps.setString(14, a.getAuthAddress().getCity());
			ps.setString(15, a.getAuthAddress().getState());
			ps.setString(16, a.getAuthAddress().getPostalCode());
			ps.setObject(17, a.getAuthAddress().getLatitude());
			ps.setObject(18, a.getAuthAddress().getLongitude());
			ps.setString(19, a.getStdAuthAddress().getAddress1());
			ps.setString(20, a.getStdAuthAddress().getAddress2());
			ps.setString(21, a.getStdAuthAddress().getCity());
			ps.setString(22, a.getStdAuthAddress().getState());
			ps.setString(23, a.getStdAuthAddress().getPostalCode());
			ps.setString(24, a.getStdAddress().getStandardizationErrorMessage());
			ps.setString(25, a.getAuthAddress().getAuthoritativeErrorMessage());
			ps.setLong(26, a.getUtAddrValidateNbr().longValue());
			ps.addBatch();
		} catch (final SQLException sqe) {
			throw new PersistenceException(updateText, sqe);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.dao.AddressPersistence#setConnection(java.sql.Connection
	 * )
	 */

	/**
	 * <p>
	 * setConnection.
	 * </p>
	 * 
	 * @param conn
	 *            a {@link java.sql.Connection} object.
	 */
	public void setConnection(final Connection conn) {
		this.conn = conn;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.address.dao.AddressPersistence#dispose()
	 */
	/** {@inheritDoc} */
	@Override
	public void dispose() {
		close(insertStmt, "insertStmt");
		close(updateStdAddrStmt, "updateStdAddrStmt");
		close(selectStmt, "selectStmt");

	}

	private void close(final PreparedStatement stmt, final String stmtName) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (final SQLException e) {
				logger.error("unable to close statement " + stmtName);
			}
		}

	}
} // end of class
