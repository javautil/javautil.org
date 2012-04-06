package org.javautil.address.service.usps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.javautil.address.beans.AuthoritativeAddressBean;
import org.javautil.address.interfaces.AuthoritativeAddress;

/**
 * Processes responses from the USPS online validation service.
 */

//  An example of a response is:
// 
//<?xml version="1.0"?>
//<AddressValidateResponse>
//        <Address>
//                <Address2>1600 PENNSYLVANIA AVE NW</Address2>
//                <City>WASHINGTON</City><State>DC</State><Zip5>20500</Zip5><Zip4>0003</Zip4>
//                <ReturnText>Default address: The address you entered was found but more
//                            information is needed (such as an apartment, suite, or box number)
//                            to match to a specific address.
//                </ReturnText>
//        </Address>
//</AddressValidateResponse>
/**
 * <p>UspsValidationResponse class.</p>
 *
 * @author jjs
 * @version $Id: UspsValidationResponse.java,v 1.1 2012/03/06 12:27:53 jjs Exp $
 */
public class UspsValidationResponse {

	private static final XPath addr1Path = DocumentHelper
			.createXPath("./Address1");
	private static final XPath addr2Path = DocumentHelper
			.createXPath("./Address2");
	/**
	 * 
	 */
	private static final XPath cityPath = DocumentHelper.createXPath("./City");
	private static final XPath statePath = DocumentHelper
			.createXPath("./State");
	private static final XPath zip5Path = DocumentHelper.createXPath("./Zip5");
	private static final XPath zip4Path = DocumentHelper.createXPath("./Zip4");

	private static final XPath descriptionPath = DocumentHelper
			.createXPath("./Error/Description");

	private static Logger logger = Logger
			.getLogger(UspsValidationResponse.class);

	/**
	 * <p>Constructor for UspsValidationResponse.</p>
	 */
	public UspsValidationResponse() {

	}

	/**
	 * Processes responses from the USPS online validation service.
	 *
	 * An example of a response is:
	 *
	 * @return Collection<AuthoritativeAddress> extracted from the response XML.
	 * @param response a {@link java.lang.String} object.
	 */
	@SuppressWarnings("unchecked")
	// hack for pmd error
	public static List<AuthoritativeAddress> parseResponse(final String response) {
		if (response == null) {
			throw new IllegalArgumentException("response is null");
		}
		// The response is parsed into the document.
		Document doc;
		//
		final ArrayList<AuthoritativeAddress> retval = new ArrayList<AuthoritativeAddress>();

		// Parse the response into the document
		try {
			doc = DocumentHelper.parseText(response);
		} catch (final DocumentException e) {
			throw new IllegalStateException("invalid response from usps '"
					+ response);
		}

		final Element root = doc.getRootElement();
		// process each address
		for (final Iterator<Element> i = root.elementIterator("Address"); i
				.hasNext();) {
			final Element addressElement = i.next();
			final AuthoritativeAddress ab = new AuthoritativeAddressBean();
			ab.setAddress1(getText(addressElement, addr1Path));
			ab.setAddress2(getText(addressElement, addr2Path));
			ab.setCity(getText(addressElement, cityPath));
			ab.setState(getText(addressElement, statePath));
			final String zip5 = getText(addressElement, zip5Path);
			final String zip4 = getText(addressElement, zip4Path);
			if (zip4 == null || zip4.length() == 0) {
				ab.setPostalCode(zip5);
			} else {
				ab.setPostalCode(zip5 + "-" + zip4);
			}
			final String errorMessage = getText(addressElement, descriptionPath);
			ab.setAuthoritativeErrorMessage(errorMessage);
			if (logger.isDebugEnabled()) {
				logger.debug(ab.getFormatted());
			}
			retval.add(ab);
		}

		return retval;
	}

	private static String getText(final Element parent, final XPath path) {
		String retval = null;
		final Element child = (Element) path.selectSingleNode(parent);
		if (child != null) {
			retval = child.getText();
		}
		return retval;
	}

}
