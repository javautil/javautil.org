package org.javautil.sales.dataservice;

import java.util.List;

import org.javautil.sales.dto.Salesperson;

public interface SalesServicesInterface {
	/**
	 * Get all Salespeople with first_name < name
	 * 
	 * This is admittedly contrived.
	 * 
	 * @param name
	 * @return
	 */
	public List<Salesperson> getSalesPeople(String name);

	public Salesperson getSalesperson(Integer id);

	public int getSalespersonCount();

	public int getCustomerCount();

	public int getSalesCount();

	public int getProductCount();

}
