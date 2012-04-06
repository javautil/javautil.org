package org.javautil.sales.populate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javautil.hibernate.persist.Persistence;
import org.javautil.hibernate.persist.TransactionHelper;
import org.springframework.beans.factory.annotation.Autowired;

public class DataPopulator {
	// TODO create interface

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	private TransactionHelper txh;
	// TODO clean uyp

	private final Logger logger = Logger.getLogger(getClass());

	private final ProductGenerator productGenerator = new ProductGenerator();

	private final ProductPopulator productPopulator = new ProductPopulator();

	private CustomerGenerator customerGenerator;

	private final CustomerPopulator customerPopulator = new CustomerPopulator();

	private final SalespersonGenerator salespersonGenerator = new SalespersonGenerator();

	private final SalespersonPopulator salespersonPopulator = new SalespersonPopulator();

	private DataPopulatorArguments arguments = new DataPopulatorArguments();

	public void afterPropertiesSet() {
		if (arguments == null) {
			throw new IllegalStateException("arguments is null");
		}

	}

	public void processArguments(final String[] args) {
		arguments.processArguments(args);
	}

	// TODO delete
	public void init() {
		// TODO TransactionHelper should go away
		txh = new TransactionHelper();
		txh.setSessionFactory(this.getSessionFactory());
		txh.afterPropertiesSet();
	}

	/**
	 * The Hibernate way.
	 * 
	 * HQL "queries" using the Class name , lower case would not have worked;
	 * 
	 * Database independent but generates lots of redo and undo.
	 */
	public void clean() {
		final Session session = txh.getSession();
		txh.startTransaction();
		final int saleDeleteCount = session.createQuery("delete Sale")
				.executeUpdate();
		final int customerDeleteCount = session.createQuery("delete Customer")
				.executeUpdate();
		final int productDeleteCount = session.createQuery("delete Product")
				.executeUpdate();
		final int salespersonDeleteCount = session.createQuery(
				"delete Salesperson").executeUpdate();
		logger.debug("deleted sales " + saleDeleteCount + " customer "
				+ customerDeleteCount + " product " + productDeleteCount
				+ " sales " + salespersonDeleteCount);
	}

	public void populate() {
		init();

		clean();
		salespersonGenerator.setQuantityToGenerate(arguments
				.getSalespersonsCount());
		salespersonPopulator.populate(txh, salespersonGenerator);

		customerGenerator = new CustomerGenerator(salespersonGenerator);
		customerPopulator.setNumberOfCustomersToPopulate(arguments
				.getCustomerCount());
		customerPopulator.populateCustomers(txh, customerGenerator);

		productPopulator.setProductCount(arguments.getProductCount());
		productPopulator.populateProducts(txh, productGenerator);

		final SalesPopulator salesPopulator = new SalesPopulator();
		salesPopulator.setSalesCount(arguments.getSalesCount());
		salesPopulator.populateSales(txh, productGenerator, customerGenerator);

		txh.commit();
	}

	public DataPopulatorArguments getArguments() {
		return arguments;
	}

	public void setArguments(final DataPopulatorArguments arguments) {
		if (arguments == null) {
			throw new IllegalArgumentException("arguments is null");
		}
		this.arguments = arguments;
	}

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// DataPopulator dp = new DataPopulator();
	// BasicConfigurator.configure();
	// // TODO What is this
	// sessionFactory = (SessionFactory)
	// H2InMemorySessionFactory.getInstance(new File(HBM_DIR));
	// dp.processArguments(args);
	// try {
	// dp.init();
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// dp.populate();
	// }

	// // TODO what is this
	// protected Serializable doWorkInCurrentTransaction(Connection conn,
	// String sql) throws SQLException {
	// // TODO Auto-generated method stub
	// return null;
	// }

}
