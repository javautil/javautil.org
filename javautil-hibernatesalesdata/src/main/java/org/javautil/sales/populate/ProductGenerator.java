package org.javautil.sales.populate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import org.apache.log4j.Logger;
import org.javautil.sales.dto.Product;

public class ProductGenerator implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4359190860347931524L;

	private static final long seed = 4359190860347931524L;
	private final HashSet<Integer> upcCodes = new HashSet<Integer>();

	private final ArrayList<Product> productList = new ArrayList<Product>();

	private static final int E5 = 100000; // as in 1e5 (fortran and
	// successors) 10^5

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * Number of times a product upc generation resulted in a formerly assigned
	 * value.
	 * 
	 */
	private long collisionCount;

	private final int numberOfManufacturers = 701;

	private final Random random;

	// todo delete
	// private RandomUtils randomUtils = new RandomUtils();

	public ProductGenerator() {
		random = new Random();
		random.setSeed(seed);
	}

	public String generateUPC() {

		String retval = null;
		int tryCount = 0;
		while (retval == null) {
			final int mfr = random.nextInt(numberOfManufacturers);
			final int product = random.nextInt(E5);
			final int upc = mfr * E5 + product;
			if (!upcCodes.contains(upc)) {
				upcCodes.add(upc);
				retval = Integer.toString(upc);
			} else {
				collisionCount++;
				if (logger.isInfoEnabled()) {
					final String upcFormatted = mfr + "-" + product;
					logger.info("already have " + upcFormatted + " tryCount "
							+ tryCount + " collisions " + collisionCount + " "
							+ upcCodes.size());
				}
			}
			tryCount++;
		}
		return retval;
	}

	public Product generateProduct() {
		final Product p = new Product();
		p.setUpc10(generateUPC());
		p.setProductStatus("S");
		productList.add(p);
		// logger.debug("created " + p.getUpc10());
		return p;
	}

	public Product getRandomProduct() {
		Product product = null;
		if (productList.size() == 0) {
			throw new IllegalStateException("getProduct has not been called");
		}
		final int index = (int) Math.random() * productList.size();
		product = productList.get(index);
		return product;

	}
}
