package org.javautil.sales.dataservice;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.Salesperson;

public abstract class AbstractJdbcSalesDao implements SalesServicesInterface {
	private Connection conn;

	private final Logger logger = Logger.getLogger(getClass());

	public Salesperson fetchSalespersonFromResultSet(final ResultSet rset)
			throws SQLException {
		final Salesperson salesperson = new Salesperson();
		salesperson.setSalespersonId(rset.getInt("salesperson_id"));
		salesperson.setDisplayName(rset.getString("display_name"));
		salesperson.setFirstName(rset.getString("first_name"));
		salesperson.setLastName(rset.getString("last_name"));
		return salesperson;
	}

	public Customer populateCustomer(final ResultSet rset) throws SQLException {
		rset.next();
		final Customer cust = new Customer();
		cust.setCustomerId(rset.getInt(1));
		cust.setCustomerStatus(rset.getString(2));
		cust.setName(rset.getString(3));
		cust.setAddr1(rset.getString(4));
		cust.setAddr2(rset.getString(5));
		cust.setCity(rset.getString(6));
		cust.setState(rset.getString(7));
		cust.setZipCd(rset.getString(8));
		cust.setOutsideSalespersonId(rset.getInt(9));

		final Salesperson sp = new Salesperson();
		sp.setSalespersonId(rset.getInt(10));
		cust.setSalesperson(sp);
		// cust.setInsideSalespersonId(rset.getInt(10));
		return cust;
	}

	// Customer fetchCustomer(ResultSet rset) throws SQLException {
	//
	// Customer cust = new Customer();
	// cust.setCustomerId(rset.getInt(1));
	// cust.setCustomerStatus(rset.getString(2));
	// cust.setName(rset.getString(3));
	// cust.setAddr1(rset.getString(4));
	// cust.setAddr2(rset.getString(5));
	// cust.setCity(rset.getString(6));
	// cust.setState(rset.getString(7));
	// cust.setZipCd(rset.getString(8));
	// cust.setOutsideSalespersonId(rset.getInt(9));
	// // todo this is problematic as we can't use the hibernate reference
	// // to make this work we need to drop the foreign key on the
	// // cust table
	// Salesperson sp = new Salesperson();
	// sp.setSalespersonId(salespersonId)
	// cust.setInsideSalespersonId(rset.getInt(10));
	// return cust;
	// }

	public int getSqlStatementInt(final String text) {
		int retval;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			final ResultSet rset = stmt.executeQuery(text);
			rset.next();
			retval = rset.getInt(1);
		} catch (final SQLException e) {
			String message = "sqlStmt '" + text + "' " + e.getMessage();
			logger.error(message);
			throw new RuntimeException(message, e);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (final SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return retval;
	}

	/**
	 * Access should be restricted todo should check that the name includes
	 * nothing but table characters as a check against injection
	 * 
	 * @param tableName
	 * @return
	 */
	int getTableCount(final String tableName) {
		return getSqlStatementInt("select count(*) from " + tableName);
	}

	@Override
	public int getSalespersonCount() {
		return getTableCount("salesperson");
	}

	@Override
	public int getCustomerCount() {
		return getTableCount("customer");
	}

	@Override
	public int getProductCount() {
		return getTableCount("product");
	}

	@Override
	public int getSalesCount() {
		return getTableCount("sale");
	}

	public int getManufacturerCount() {
		return getTableCount("manufacturer");
	}

	public Connection getConnection() {
		return conn;
	}

	public void setConnection(final Connection conn) {
		this.conn = conn;
	}
}
