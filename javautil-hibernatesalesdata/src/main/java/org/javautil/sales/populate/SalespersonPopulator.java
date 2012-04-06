package org.javautil.sales.populate;

import org.apache.log4j.Logger;
import org.javautil.hibernate.persist.TransactionHelper;
import org.javautil.sales.dataservice.SalesServicesJDBC;
import org.javautil.sales.dto.Salesperson;

/**
 * Populates the sale table.
 * 
 * @author jjs
 * 
 */
public class SalespersonPopulator {
	private final Logger logger = Logger.getLogger(getClass());

	public void populate(final TransactionHelper txh,
			final SalespersonGenerator salespersonGenerator) {
		logger.info("adding salesPeople");
		for (final Salesperson sp : salespersonGenerator.getSalesPeople()) {
			txh.save(sp);
		}

		final String message = "number of Salespeople "
				+ salespersonGenerator.getSalesPeople().size();
		logger.info(message);
		// flushing fills in the Generated Keys -- todo confirm at flush
		// todo write up on Oracle Sequence generator
		txh.flush();
		final int insertCount = salespersonGenerator.getSalesPeople().size();
		final int count = getSalesCount(txh);
		assert (insertCount == count);
	}

	int getSalesCount(final TransactionHelper txh) {
		final SalesServicesJDBC ss = new SalesServicesJDBC();
		// without
		ss.setConnection(txh.getConnection());
		final int retval = ss.getSalespersonCount();
		return retval;
	}
}
