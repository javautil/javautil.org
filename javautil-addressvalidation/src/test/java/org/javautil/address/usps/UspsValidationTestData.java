package org.javautil.address.usps;

import org.javautil.address.beans.AddressBean;

/**
 * Performs validation per
 * https://www.usps.com/webtools/htm/Address-Information-v3-1a.htm Section 2.2.
 *
 * For testing purposes, the only value in the test code in this section that
 * you should change is the user ID. Enter the user ID you received in the
 * registration e-mail for testing. All remaining code in the test scripts
 * provided below must remain unchanged.
 *
 * Testing URL
 *
 * To make test calls to the Address Standardization server, access is required
 * to a server. Use the Testing URL provided in the registration e-mail.
 *
 * Scripted Test Requests
 *
 * There are two test requests included in this procedure. All of the test
 * script code contained in this document can be cut and pasted for your use in
 * testing the software. Be sure to note the request numbers so you can match up
 * the responses you will receive as provided in the Successful Test Responses
 * section.
 *
 * Test Request #1
 *
 * http://SERVERNAME/ShippingAPITest.dll?API=Verify&XML=<AddressValidateRequest%
 * 20USERID="xxxxxxx"><Address ID="0"><Address1></Address1> <Address2>6406 Ivy
 * Lane</Address2><City>Greenbelt</City><State>MD</State>
 * <Zip5></Zip5><Zip4></Zip4></Address></AddressValidateRequest>
 *
 * Test Request #2
 *
 * http://SERVERNAME/ShippingAPITest.dll?API=Verify&XML=<AddressValidateRequest%
 * 20USERID="xxxxxxx"><Address ID="1"><Address1></Address1> <Address2>8 Wildwood
 * Drive</Address2><City>Old Lyme</City><State>
 * CT</State><Zip5>06371</Zip5><Zip4></Zip4></Address></AddressValidateRequest>
 *
 * @author jjs
 * @version $Id: UspsValidationTestData.java,v 1.2 2012/03/04 12:31:19 jjs Exp $
 * @since 0.11.0
 */
public class UspsValidationTestData {

	private static final AddressBean address1 = new AddressBean(null,
			"6406 Ivy Lane", "Greenbelt", "MD", null);
	private static final AddressBean address2 = new AddressBean(null,
			"8 Wildwood Drive", "Old Lyme", "CT", "06371");
	/**
	 * Not exactly what the USPS asks for but as we are using a Document we will
	 * get what is supported. Actually wants private static final String
	 * address1Xml =
	 * "http://SERVERNAME/ShippingAPITest.dll?API=Verify&XML=<AddressValidateRequest%"
	 * +
	 * "20USERID=\"xxxxxxx\"><Address ID=\"0\"><Address1></Address1> <Address2>6406 Ivy"
	 * + "Lane</Address2><City>Greenbelt</City><State>MD</State>" +
	 * "<Zip5></Zip5><Zip4></Zip4></Address></AddressValidateRequest>";
	 * 
	 * And as this is a single request, we will skip the Address enclosing
	 * element
	 * 
	 * TODO restore ID attribute even for single requests.
	 */
	private static final String address1Xml = "http://SERVERNAME/ShippingAPITest.dll?API=Verify&XML=<AddressValidateRequest "
			+ "USERID=\"xxxxxxx\"><Address><Address1/><Address2>6406 Ivy "
			+ "Lane</Address2><City>Greenbelt</City><State>MD</State>"
			+ "<Zip5/><Zip4/></Address></AddressValidateRequest>";

	private static final String address2Xml = "http://SERVERNAME/ShippingAPITest.dll?API=Verify&XML=<AddressValidateRequest "
			+ "USERID=\"xxxxxxx\"><Address><Address1/><Address2>8 Wildwood"
			+ "Drive</Address2><City>Old Lyme</City><State>"
			+ "CT</State><Zip5>06371</Zip5><Zip4/></Address></AddressValidateRequest>";

	/**
	 * <p>Getter for the field <code>address1</code>.</p>
	 *
	 * @return a {@link org.javautil.address.beans.AddressBean} object.
	 */
	public static AddressBean getAddress1() {
		return address1;
	}

	/**
	 * <p>Getter for the field <code>address2</code>.</p>
	 *
	 * @return a {@link org.javautil.address.beans.AddressBean} object.
	 */
	public static AddressBean getAddress2() {
		return address2;
	}

	/**
	 * <p>getAddress1xml.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getAddress1xml() {
		return address1Xml;
	}

	/**
	 * <p>getAddress2xml.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getAddress2xml() {
		return address2Xml;
	}

}
