package org.javautil.sales.populate;

import org.apache.log4j.Logger;
import org.javautil.hibernate.persist.TransactionHelper;
import org.javautil.sales.dto.Customer;

/**
 * Inserts Customer Records.
 * 
 * @author jjs
 * 
 */

public class CustomerPopulator {

	private final Logger logger = Logger.getLogger(getClass());
	private int numberOfCustomersToPopulate = 10000;

	public void populateCustomers(final TransactionHelper txh,
			final CustomerGenerator customerGenerator) {
		if (customerGenerator == null) {
			throw new IllegalArgumentException("customerGenerator is null");
		}
		if (txh == null) {
			throw new IllegalArgumentException("TransactionHelper is null");
		}
		// Session session = persistence.getSession();
		// Transaction transaction = session.beginTransaction();

		final long beforeInsert = System.currentTimeMillis();
		// todo need to add batch processing
		// still don't know,

		for (int i = 0; i < numberOfCustomersToPopulate; i++) {
			final Customer p = customerGenerator.getCustomer();
			if (i % 1000 == 0) {
				txh.flush(); // todo figure out batching
				logger.debug("Customer records created " + i);
			}
			txh.save(p);
		}

		final long afterInsert = System.currentTimeMillis();
		final long elapsedMillis = afterInsert - beforeInsert;
		System.out.println("customers added elapsed Millis " + elapsedMillis);
		logger.info("customers added in " + elapsedMillis);

		txh.flush();
		// transaction.commit();
	}

	public int getNumberOfCustomersToPopulate() {
		return numberOfCustomersToPopulate;
	}

	public void setNumberOfCustomersToPopulate(
			final int numberOfCustomersToPopulate) {
		this.numberOfCustomersToPopulate = numberOfCustomersToPopulate;
	}

}
