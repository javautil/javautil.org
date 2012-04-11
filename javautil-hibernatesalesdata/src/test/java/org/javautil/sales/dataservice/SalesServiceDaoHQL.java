package org.javautil.sales.dataservice;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.javautil.hibernate.persist.Dao;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.sales.BeanTags;
import org.javautil.sales.dto.Customer;
import org.javautil.sales.dto.GttNumber;
import org.javautil.sales.dto.Salesperson;
import org.javautil.text.AsString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//

public class SalesServiceDaoHQL implements SalesServicesInterface {

	private final AsString asString = new AsString();

	// todo this seems very contrived
	@Autowired
	private Persistence daoFactory;

	private static final String newline = System.getProperty("line.separator");

	private final Logger logger = Logger.getLogger(getClass());

	private List<Salesperson> salesPeople;

	/**
	 * A an example HQL (HibernateQueryLanguage) query.
	 * 
	 * Note that the table names are specified by the mapped Class name, hence
	 * Customer must be in upper case. Further the table column names are
	 * specified as in SQL.
	 * 
	 * The columns to be selected are implied so the select... is not necessary.
	 */
	private static final String hqlText = "from Customer " + //
			"where outside_salesperson_id in (select nbr from GttNumber) " + //
			"      or inside_salesperson_id in (select nbr from GttNumber)  ";

	public static void main(final String[] args) {
		final SalesServiceDaoHQL qe = new SalesServiceDaoHQL();
		final ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(
				"application-context.xml");

		qe.init(springContext);
		final List<Salesperson> salespeople = qe.getSalesPeople("C");

		qe.getCustomers(salespeople);

	}

	// todo jjs more contrive
	public void init(final ClassPathXmlApplicationContext context) {
		daoFactory = (Persistence) context.getBean(BeanTags.DAO_FACTORY);
	}

	/**
	 * Note: Salesperson is the java object and first_name is the database
	 * column name that is todo ... in the object the syntax is a little bizzare
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Salesperson> getSalesPeople(final String firstName) {
		daoFactory.beginTransaction(); // todo think about transaction
										// management

		final Query q = daoFactory.getSession().createQuery(
				" from Salesperson" + //
						" where first_name < :firstName ");
		q.setString("firstName", firstName);
		// is this now a proxied class? No, provide
		salesPeople = q.list();

		final String message = asString.toString(salesPeople);
		// todo work on better presentation
		System.out.println(newline + message);
		// what happened in my session to the salepersons that just went out of
		// scope? should evict
		return salesPeople;
	}

	public List<Salesperson> getSalesPeople(final String lowerLastName,
			final String upperLastName) {
		daoFactory.beginTransaction(); // todo think about transaction
										// management

		final Query q = daoFactory.getSession().createQuery(
				" from Salesperson"
						+ //
						" where upper(last_name) >= :lowerLastName "
						+ " and upper(last_name) <=  :upperLastName) ");

		q.setString("lowerLastName", lowerLastName);
		q.setString("upperLastName", upperLastName);
		// is this now a proxied class? No, provide
		salesPeople = q.list();

		final String message = asString.toString(salesPeople);
		// todo work on better presentation
		System.out.println(newline + message);
		// what happened in my session to the salepersons that just went out of
		// scope? should evict
		return salesPeople;
	}

	@Override
	public Salesperson getSalesperson(final Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Customer> getCustomers(final Collection<Salesperson> salespeople) {
		final Salesperson[] persons = salespeople.toArray(new Salesperson[0]);
		return getCustomers(persons);
	}

	/**
	 * Delete everything in the gtt_number table.
	 * 
	 * An easy, but expensive way to do a delete.
	 * 
	 * Todo check to see what kind of sql this generates.
	 */
	void emptyGttNumber() {
		final String hqlText = "from GttNumber";
		final Query q = daoFactory.getSession().createQuery(hqlText);
		q.list().clear();
	}

	public Set<Customer> getCustomers(final Salesperson... salespersons) {
		final LinkedHashSet<Customer> customers = new LinkedHashSet<Customer>();

		emptyGttNumber();

		final Dao<GttNumber, ?> dao = daoFactory.get(GttNumber.class);
		for (final Salesperson salesperson : salespersons) {

			final GttNumber gn = new GttNumber();
			gn.setNbr(salesperson.getSalespersonId());

			dao.save(gn);

		}
		daoFactory.flush(); // push the level 1 cache to the database

		final Query q = daoFactory.getSession().createQuery(hqlText);
		customers.addAll(q.list());

		return customers;

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

	@Override
	public int getSalespersonCount() {
		return getTableCount("salesperson");
	}

	public int getTableCount(final String tableName) {
		daoFactory.beginTransaction();
		// todo this is not associated with any persistence, why do I need a
		// transaction?
		final SQLQuery q = daoFactory.getSession()
				.createSQLQuery("select count(*) from " + tableName)
				.addScalar("COUNT(*)", Hibernate.INTEGER);
		final Integer c = (Integer) q.list().get(0);
		return c.intValue();
	}

	public Persistence getDaoFactory() {
		return daoFactory;
	}

	public void setDaoFactory(final Persistence daoFactory) {
		this.daoFactory = daoFactory;
	}

}
