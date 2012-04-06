package org.javautil.address.usps;

/**
 * TODO Placeholder for properties needed to process a service request.
 *
 * @author jjs
 * @version $Id: UspsValidationServicePropertyHelper.java,v 1.1 2012/03/04 12:31:15 jjs Exp $
 */
public class UspsValidationServicePropertyHelper {
	// TODO get from environment or a file outside the source
	// code base.
	private String userId = "610JAVAU3000";
	private String productionUrl = "http://Production.ShippingAPIs.com/ShippingAPI.dll";
	private String testUrl = "http://testing.shippingapis.com/ShippingAPITest.dll";

	/**
	 * <p>Getter for the field <code>userId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * <p>Setter for the field <code>userId</code>.</p>
	 *
	 * @param userId a {@link java.lang.String} object.
	 */
	public void setUserId(final String userId) {
		this.userId = userId;
	}

	/**
	 * <p>Getter for the field <code>productionUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getProductionUrl() {
		return productionUrl;
	}

	/**
	 * <p>Setter for the field <code>productionUrl</code>.</p>
	 *
	 * @param productionUrl a {@link java.lang.String} object.
	 */
	public void setProductionUrl(final String productionUrl) {
		this.productionUrl = productionUrl;
	}

	/**
	 * <p>Getter for the field <code>testUrl</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getTestUrl() {
		return testUrl;
	}

	/**
	 * <p>Setter for the field <code>testUrl</code>.</p>
	 *
	 * @param testUrl a {@link java.lang.String} object.
	 */
	public void setTestUrl(final String testUrl) {
		this.testUrl = testUrl;
	}

}
