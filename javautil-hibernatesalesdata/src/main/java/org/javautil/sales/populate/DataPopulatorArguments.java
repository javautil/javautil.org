package org.javautil.sales.populate;

import org.javautil.commandline.CommandLineHandler;
import org.javautil.commandline.annotations.Optional;

/**
 * Arguments to DataPopulator.
 * 
 * This bean holds the command line values for DataPopulator. see DataPopulator.
 * 
 * @author jjs
 * 
 */
public class DataPopulatorArguments {

	@Optional
	private Integer salespersonsCount = 100;

	@Optional
	private Integer customerCount = 1000;

	@Optional
	private Integer productCount = 1000;

	@Optional
	private Integer manufacturerCount = 701;

	@Optional
	private Integer salesCount = 100000;

	public void processArguments(final String[] args) {
		final CommandLineHandler cmd = new CommandLineHandler(this);
		cmd.evaluateArguments(args);
	}

	public Integer getSalespersonsCount() {
		return salespersonsCount;
	}

	public void setSalespersonsCount(final Integer salespersonsCount) {
		this.salespersonsCount = salespersonsCount;
	}

	public Integer getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(final Integer customerCount) {
		this.customerCount = customerCount;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(final Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getSalesCount() {
		return salesCount;
	}

	public void setSalesCount(final Integer salesCount) {
		this.salesCount = salesCount;
	}

	public Integer getManufacturerCount() {
		return manufacturerCount;
	}

	public void setManufacturerCount(final Integer manufacturerCount) {
		this.manufacturerCount = manufacturerCount;
	}

}
