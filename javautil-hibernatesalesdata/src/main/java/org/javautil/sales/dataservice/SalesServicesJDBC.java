package org.javautil.sales.dataservice;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.javautil.jdbc.datasources.SimpleDatasourcesFactory;
import org.javautil.sales.dto.Salesperson;
import org.javautil.text.AsString;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SalesServicesJDBC extends AbstractJdbcSalesDao implements
		SalesServicesInterface {

	private final AsString asString = new AsString();

	private DataSource dataSource;

	private static final String newline = System.getProperty("line.separator");

	private final Logger logger = Logger.getLogger(getClass());

	public void init(final ClassPathXmlApplicationContext context) {
		// todo test and document
		// should really inject a good datasource
		try {
			dataSource = SimpleDatasourcesFactory.getDataSource("sales");

			setConnection(dataSource.getConnection());
		} catch (final SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// todo show how to conditionally update what session awareness means on
	// these things

	// todo add parm to query
	@SuppressWarnings("unchecked")
	public List<Salesperson> getSalesPeople() {
		// this weird format works well with eclipse source formatters
		final String queryText = "" + //
				"select " + //
				"	salesperson_id, display_name, first_name, last_name " + //
				"from salesperson " + //
				"where first_name < :firstName";

		PreparedStatement ps = null;
		ArrayList<Salesperson> salespersons;
		ResultSet rset;
		try {
			ps = getConnection().prepareStatement(queryText);
			rset = ps.executeQuery();

			salespersons = new ArrayList<Salesperson>();
			// todo write a salesperson DAO that gets updated

			while (rset.next()) {
				final Salesperson sp = fetchSalespersonFromResultSet(rset);
				salespersons.add(sp);
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
		// what happened in my session to the salepersons that just went out of
		// scope? should evict
		return salespersons;
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

	// todo populate an ARRAY do a CAST

}
