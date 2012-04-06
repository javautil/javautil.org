package org.javautil.sales.populate;

import org.apache.log4j.Logger;
import org.javautil.hibernate.persist.TransactionHelper;
import org.javautil.sales.dto.Product;

public class ProductPopulator {
	private final Logger logger = Logger.getLogger(getClass());
	/**
	 * Number of products to generate;
	 */
	private int productCount = 1000;

	private int batchSize = 100;

	public ProductGenerator populateProducts(final TransactionHelper txh,
			final ProductGenerator productGenerator) {
		final long beforeInsert = System.currentTimeMillis();

		for (int i = 0; i < productCount; i++) {
			final Product p = productGenerator.generateProduct();
			if ((i > 0) && (i % batchSize == 0)) {
				logger.debug("product records created " + i);
				txh.flush(); // todo figure out batching
			}
			txh.save(p);
		}

		final long afterInsert = System.currentTimeMillis();
		// todo change back to logger messags
		System.out.println("elapsed Millis " + (afterInsert - beforeInsert));
		txh.flush();
		return productGenerator;

	}

	public void setProductCount(final int _productCount) {
		productCount = _productCount;

	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(final int batchSize) {
		this.batchSize = batchSize;
	}
}
