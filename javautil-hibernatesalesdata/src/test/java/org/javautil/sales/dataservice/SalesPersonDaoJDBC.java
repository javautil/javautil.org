package org.javautil.sales.dataservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;
import org.javautil.sales.BeanTags;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.Salesperson;
import org.javautil.text.AsString;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// TODO must be able to get an OracleConnection

public class SalesPersonDaoJDBC extends AbstractJdbcSalesDao implements
		SalesServicesInterface {

	private final AsString asString = new AsString();

	private static final String selectColumns = "	salesperson_id, display_name, first_name, last_name ";

	static final String queryText = "" + //
			"select " + //
			selectColumns + //
			"from salesperson ";

	private DataSource dataSource;

	private Connection conn;

	private static final String newline = System.getProperty("line.separator");

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * 
	 * todo Have to be able to set the Oracle Connection properties at the
	 * Datasource Level
	 * 
	 * @param context
	 */
	public void init(final ClassPathXmlApplicationContext context) {
		try {
			dataSource = (DataSource) context.getBean(BeanTags.DATA_SOURCE);
			conn = dataSource.getConnection();
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Salesperson getSalesperson(final ResultSet rset)
			throws SQLException {

		final Salesperson salesperson = new Salesperson();
		salesperson.setSalespersonId(rset.getInt("salesperson_id"));
		salesperson.setDisplayName(rset.getString("display_name"));
		salesperson.setFirstName(rset.getString("first_name"));
		salesperson.setLastName(rset.getString("last_name"));
		return salesperson;
	}

	// todo show how to conditionally update what session awareness means on
	// these things

	// todo add parm to query
	// todo make sure that we turn on statement caching in the connection
	@Override
	@SuppressWarnings("unchecked")
	public List<Salesperson> getSalesPeople(final String firstName) {
		// this wierd format works well with eclipse source formatters
		final String queryText = "" + //
				"select " + //
				"	salesperson_id, display_name, first_name, last_name " + //
				"from salesperson " + //
				"where first_name < :firstName";

		PreparedStatement ps = null;
		ArrayList<Salesperson> salespersons;
		ResultSet rset;
		try {
			ps = conn.prepareStatement(queryText);
			ps.setString(1, firstName);
			rset = ps.executeQuery();

			salespersons = new ArrayList<Salesperson>();
			// todo write a salesperson DAO that gets updated
			while (rset.next()) {
				salespersons.add(getSalesperson(rset));
			}
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				ps.close();
			} catch (final SQLException e) {
				throw new RuntimeException(e);
			}
		}

		asString.toString(salespersons);
		// todo work on better presentation
		System.out.println(newline + salespersons);
		return salespersons;
	}

	public Set<Customer> getCustomers(final Collection<Salesperson> salespeople) {
		final Salesperson[] persons = salespeople.toArray(new Salesperson[0]);
		return getCustomers(persons);
	}

	public Set<Customer> getCustomers(final Salesperson... salespersons) {
		final LinkedHashSet<Customer> customers = new LinkedHashSet<Customer>();

		final String columns = "CUSTOMER_ID, CUSTOMER_STATUS, NAME, ADDR_1, ADDR_2, CITY, STATE, ZIP_CD, OUTSIDE_SALESPERSON_ID, INSIDE_SALESPERSON_ID ";

		final String sqlText = "select " + columns
				+ //
				"from customer "
				+ //
				"where outside_salesperson_id in table(cast :salesperson as number_array) "
				+ //
				"      or inside_salesperson_id in table(cast :salesperson  as number_array) ";

		final int[] salesids = new int[salespersons.length];
		for (int i = 0; i < salespersons.length; i++) {
			salesids[i] = salespersons[i].getSalespersonId();
		}

		ARRAY salesidArray;

		try {

			final ArrayDescriptor NUMBER_ARRAYDescriptor = ArrayDescriptor
					.createDescriptor("NUMBER_ARRAY", conn);
			salesidArray = new ARRAY(NUMBER_ARRAYDescriptor, conn, salesids);

			final PreparedStatement stmt = conn.prepareStatement(sqlText);
			stmt.setObject(1, salesidArray);
			stmt.setObject(2, salesidArray);
			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				final Customer cust = populateCustomer(rset);
				customers.add(cust);
			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return customers;

	}

	// private Customer populateCustomer(ResultSet rset) throws SQLException {
	// rset.next();
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
	//
	// Salesperson sp = new Salesperson();
	// sp.setSalespersonId(rset.getInt(10));
	// cust.setSalesperson(sp);
	// //cust.setInsideSalespersonId(rset.getInt(10));
	// return cust;
	// }

	public Set<Customer> getCustomers(final Salesperson salesperson) {
		final LinkedHashSet<Customer> customers = new LinkedHashSet<Customer>();
		final String sqlText = "select CUSTOMER_ID, CUSTOMER_STATUS, NAME, ADDR_1, ADDR_2, CITY, STATE, ZIP_CD, OUTSIDE_SALESPERSON_ID, INSIDE_SALESPERSON_ID "
				+ "from customer where outside_salesperson_id = :salesperson or inside_salesperson_id = :salesperson ";

		try {
			final PreparedStatement stmt = conn.prepareStatement(sqlText);
			stmt.setInt(1, salesperson.getSalespersonId());
			stmt.setInt(2, salesperson.getSalespersonId());
			final ResultSet rset = stmt.executeQuery();
			while (rset.next()) {
				final Customer cust = populateCustomer(rset);
				customers.add(cust);
			}

		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}

		return customers;

	}

	// todo write a table cast

	// todo populate gtt and try different levels of sampling
	@Override
	public Salesperson getSalesperson(final Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	// todo populate an ARRAY do a CAST

	public static void main(final String[] args) {
		final SalesPersonDaoJDBC sp = new SalesPersonDaoJDBC();
		final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		sp.init(springContext);
		final List<Salesperson> salespeople = sp.getSalesPeople("C");
		sp.getCustomers(salespeople);
	}

	public Connection getConn() {
		return conn;
	}

	@Override
	public int getCustomerCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProductCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSalesCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSalespersonCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
