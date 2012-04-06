package org.javautil.sales.dataservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.javautil.oracle.OracleConnectionUtil;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.Salesperson;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SalesServicesGttJdbc extends AbstractJdbcSalesDao implements
		SalesServicesInterface {

	private final Connection conn;

	public SalesServicesGttJdbc(final Connection conn) {
		if (conn == null) {
			throw new IllegalArgumentException("conn is null");
		}
		this.conn = conn;
	}

	private void cleanNumbers() throws SQLException {
		final String deleteText = "delete from gtt_number";
		final Statement s = conn.createStatement();
		s.execute(deleteText);
	}

	private void populateNumbers(final Collection<Number> numbers)
			throws SQLException {
		cleanNumbers();
		final String insertText = "insert into gtt_number(nbr) values (:v) ";
		final PreparedStatement ps = conn.prepareStatement(insertText);
		for (final Number number : numbers) {
			ps.setObject(1, number);
			ps.addBatch();
		}
		ps.executeBatch();
	}

	public Set<Customer> getCustomers(final Collection<Salesperson> salespeople)
			throws SQLException {
		final Salesperson[] persons = salespeople.toArray(new Salesperson[0]);
		return getCustomers(persons);
	}

	public Set<Customer> getCustomers(final Salesperson... salespersons)
			throws SQLException {
		final LinkedHashSet<Customer> customers = new LinkedHashSet<Customer>();

		final String columns = "CUSTOMER_ID, CUSTOMER_STATUS, NAME, ADDR_1, ADDR_2, CITY, STATE, ZIP_CD, OUTSIDE_SALESPERSON_ID, INSIDE_SALESPERSON_ID ";

		final String sqlText = "select " + columns
				+ //
				"from customer "
				+ //
				"where outside_salesperson_id in (select nbr from gtt_number) "
				+ //
				"      or inside_salesperson_id in (select nbr from gtt_number)  ";

		final Collection<Number> salesids = new ArrayList<Number>();
		for (final Salesperson salesperson : salespersons) {
			salesids.add(salesperson.getSalespersonId());
		}

		populateNumbers(salesids);

		try {

			final Statement stmt = conn.createStatement();
			final ResultSet rset = stmt.executeQuery(sqlText);
			while (rset.next()) {
				final Customer cust = populateCustomer(rset);
				customers.add(cust);
			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return customers;

	}

	public static void main(final String[] args) throws SQLException {
		final SalesPersonDaoJDBC sp = new SalesPersonDaoJDBC();
		final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
				"application-context.xml");
		sp.init(springContext);
		final Connection conn = sp.getConn();
		final SalesServicesGttJdbc gtt = new SalesServicesGttJdbc(conn);
		final OracleConnectionUtil ocu = new OracleConnectionUtil(conn);
		ocu.traceOn(gtt.getClass().getSimpleName());
		// ClassPathXmlApplicationContext springContext = new
		// ClassPathXmlApplicationContext("application-context.xml");
		// sp.init(springContext);
		final List<Salesperson> salespeople = sp.getSalesPeople("C");
		// sp.getCustomers(salespeople);
		// Connection conn = sp.getConn();
		// GetSalespersonsUsingGTT gtt = new GetSalespersonsUsingGTT(conn);
		gtt.getCustomers(salespeople);

	}

	@Override
	public List<Salesperson> getSalesPeople(final String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Salesperson getSalesperson(final Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
