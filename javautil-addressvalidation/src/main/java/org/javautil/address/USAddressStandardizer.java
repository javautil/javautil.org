package org.javautil.address;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.javautil.address.beans.StandardAddressBean;
import org.javautil.address.exception.AddressStandardizationException;
import org.javautil.address.exception.MultiplePoBoxAddressException;
import org.javautil.address.exception.MultipleRuralRoutesException;
import org.javautil.address.exception.MultipleStreetAddressesException;
import org.javautil.address.exception.MultipleSubunitException;
import org.javautil.address.exception.NoDeliveryLocationFoundException;
import org.javautil.address.exception.RuralRouteAndStreetAddressException;
import org.javautil.address.exception.StandardizedAddressLineWouldBeTooLongException;
import org.javautil.address.formatters.PoBoxFormatter;
import org.javautil.address.formatters.StreetAddressFormatter;
import org.javautil.address.interfaces.Address;
import org.javautil.address.interfaces.AddressStandardizer;
import org.javautil.address.interfaces.StandardizedAddress;
import org.javautil.address.parser.POBoxParser;
import org.javautil.address.parser.RuralRouteParser;
import org.javautil.address.parser.StreetParser;
import org.javautil.address.parser.SubunitParser;
import org.javautil.address.standardname.CityNameStandardizer;
import org.javautil.address.standardname.StateAbbreviations;
import org.javautil.address.standardname.StreetTypeSuffixes;
import org.javautil.address.usps.ZipCodeFormatter;

// TODO BOX should be PO Box if no other address found
// TODO resolve E. Arapahoe Road, Bldg D"
// TODO 99939 East Butthead, Bldg A s/b suite BLDG A
// TODO
/**
 * Converts an address to a standardized format, the format being implementation
 * specific.
 *
 * @author jjs
 * @version $Id: USAddressStandardizer.java,v 1.4 2012/03/04 12:31:14 jjs Exp $
 */
public class USAddressStandardizer implements AddressStandardizer {

	private static final String newline = System.getProperty("line.separator");
	private final CityNameStandardizer cityStandardizer = new CityNameStandardizer();

	/**
	 * 
	 */
	private final boolean streetAddressIsLine2 = AddressResourceBundle
			.getBoolean(AddressResourceName.streetAddressIsLine2);

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private ArrayList<AddressStandardizationException> exceptions = null;

	private StandardizedAddress std;

	private final StreetTypeSuffixes suffixMapper = new StreetTypeSuffixes();
	//
	private final PoBoxFormatter poBoxFormatter = new PoBoxFormatter();

	private final StreetAddressFormatter streetAddressFormatter = new StreetAddressFormatter();

	private Address raw;

	private final int maxStdStreetAddrLength = AddressResourceBundle
			.getInt("USAddressStandardizer.maxStdStreetAddrLength");

	// Address Component Parsers
	private final SubunitParser subunitLine1Parser = new SubunitParser();
	/**
	 * Parser to look for sub units in line two of address.
	 */
	private final SubunitParser subunitLine2Parser = new SubunitParser();
	/**
	 * Parser to look for Post office boxes in line one of address.
	 */
	private final POBoxParser poBoxLine1Parser = new POBoxParser();
	/**
	 * Parser to look for Post office boxes in line two of address.
	 */
	private final POBoxParser poBoxLine2Parser = new POBoxParser();

	/**
	 * Parser to look for Street Addresses in line one of address.
	 */
	private final StreetParser streetLine1Parser = new StreetParser();
	/**
	 * Parser to look for Street Addresses in line two of address.
	 */
	private final StreetParser streetLine2Parser = new StreetParser();

	/**
	 * The parser to look for rural routes in line 1 of address.
	 */
	private final RuralRouteParser routeLine1Parser = new RuralRouteParser();

	/**
	 * The parser to look for rural routes in line 2 of address.
	 */
	private final RuralRouteParser routeLine2Parser = new RuralRouteParser();

	/**
	 * The first line of an address, not the street address.
	 * 
	 * In a standardized address this is more specific than the street address
	 * for example apartment, unit number, basement.
	 */
	private String addr1;

	/**
	 * Street address.
	 */
	private String addr2;

	/**
	 * <p>Constructor for USAddressStandardizer.</p>
	 */
	public USAddressStandardizer() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.AddressStandardizer#standardize(org.javautil.address
	 * .interfaces.Address)
	 */
	/**
	 * <p>standardize.</p>
	 *
	 * @param raw a {@link org.javautil.address.interfaces.Address} object.
	 * @return a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	public synchronized StandardizedAddress standardize(final Address raw)
			throws AddressStandardizationException {
		this.raw = raw;
		final StandardizedAddress std = new StandardAddressBean();
		standardize(raw, std);
		return std;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javautil.address.AddressStandardizer#standardize(org.javautil.address
	 * .interfaces.Address, org.javautil.address.interfaces.StandardizedAddress)
	 */
	/** {@inheritDoc} */
	@Override
	public synchronized void standardize(final Address raw,
			final StandardizedAddress std)
			throws AddressStandardizationException {
		if (raw == null) {
			throw new IllegalArgumentException("raw is null");
		}

		this.raw = raw;
		this.std = std;
		exceptions = new ArrayList<AddressStandardizationException>();
		final ZipCodeFormatter zipFormatter = new ZipCodeFormatter();
		addr1 = raw.getAddress1();
		addr2 = raw.getAddress2();
		try {
			// these must occur before process address lines so subunits and po
			// boxes can be stripped
			processPoBox();
			processSubunit();
			//
			processAddressLines();
			processRuralRoute();
			std.setCity(cityStandardizer.getStandardCityName(raw.getCity(),
					std.getState(), null)); // raw.getRawCountryCode()));
			std.setState(StateAbbreviations.getStandardAbbreviation(raw
					.getState()));
			std.setCountryCode(raw.getCountryCode() == null ? null : raw
					.getCountryCode().trim().toUpperCase());
			checkResults();
			std.setPostalCode(zipFormatter.formatUSPostalCode(raw
					.getPostalCode()));
		} catch (final AddressStandardizationException e) {
			exceptions.add(e);
			throw e;
		}

	}

	/**
	 * <p>processAddressLines.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	protected void processAddressLines() throws AddressStandardizationException {

		final boolean addr1HasStreet = streetLine1Parser.parse(addr1);
		final boolean addr2HasStreet = streetLine2Parser.parse(addr2);

		if (addr1HasStreet && addr2HasStreet) {
			throw new MultipleStreetAddressesException(raw);
		}

		String streetAddress = null;
		if (addr1HasStreet || addr2HasStreet) {
			final StreetParser sp = addr1HasStreet ? streetLine1Parser
					: streetLine2Parser;
			std.setStreetNumber(sp.getStreetNumber());
			std.setStreetName(sp.getStreetName());
			std.setStreetType(sp.getStreetType());
			streetAddress = streetAddressFormatter.format(sp);
			if (streetAddress.length() > maxStdStreetAddrLength) {
				final String message = "street address length for '"
						+ streetAddress + "' length " + streetAddress.length()
						+ " exceeds limit of " + maxStdStreetAddrLength;
				throw new StandardizedAddressLineWouldBeTooLongException(raw,
						message);
			}
			logger.debug("streetAddress: '" + streetAddress + "'");
			setStreetAddressLine(streetAddress);
		}
		if (logger.isDebugEnabled()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("addr1: ").append(addr1).append(newline);
			sb.append("addr2: ").append(addr2).append(newline);
			sb.append("streetAddress: '").append(streetAddress).append("'");
			logger.debug(sb.toString());
		}
	}

	/**
	 * <p>processPoBox.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	protected void processPoBox() throws AddressStandardizationException {

		final boolean addr1HasPoBox = poBoxLine1Parser.parse(addr1);
		final boolean addr2HasPoBox = poBoxLine2Parser.parse(addr2);
		// todo should throw an exception if it has a PO Box and a street
		// address
		if (addr1HasPoBox && addr2HasPoBox) {
			throw new MultiplePoBoxAddressException(raw);
		}

		if (addr1HasPoBox || addr2HasPoBox) {
			final POBoxParser sp = addr1HasPoBox ? poBoxLine1Parser
					: poBoxLine2Parser;
			std.setPoBox(sp.getPoBox());
			setStreetAddressLine(poBoxFormatter.format(sp));
		}
	}

	/**
	 * <p>processRuralRoute.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	protected void processRuralRoute() throws AddressStandardizationException {
		final boolean addr1Route = routeLine1Parser.parse(addr1);
		final boolean addr2Route = routeLine2Parser.parse(addr2);
		if (addr1Route && addr2Route) {
			throw new MultipleRuralRoutesException(raw);
		}

		if (addr1Route || addr2Route) {
			if (std.getStreetNumber() != null) {
				throw new RuralRouteAndStreetAddressException(raw);

			}

			final RuralRouteParser routeParser = addr1Route ? routeLine1Parser
					: routeLine2Parser;
			final String addrLine = "RR " + routeParser.getRuralRoute()
					+ " Box " + routeParser.getBox();
			setStreetAddressLine(addrLine);
		}
	}

	/**
	 * <p>processSubunit.</p>
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	protected void processSubunit() throws AddressStandardizationException {

		final boolean addr1HasSubunit = subunitLine1Parser.parse(addr1);
		final boolean addr2HasSubunit = subunitLine2Parser.parse(addr2);

		if (addr1HasSubunit && addr2HasSubunit) {
			throw new MultipleSubunitException(raw);
		}

		if (addr1HasSubunit) {
			addr1 = subunitLine1Parser.getNonUnit();
		}
		if (addr2HasSubunit) {
			addr2 = subunitLine2Parser.getNonUnit();
		}

		if (addr1HasSubunit || addr2HasSubunit) {
			final SubunitParser sp = addr1HasSubunit ? subunitLine1Parser
					: subunitLine2Parser;
			std.setSubunitType(sp.getSubunitType());
			std.setSubunitCode(sp.getSubunitCode());
			final String subunit = sp.getSubunitTypeStandardName() + " "
					+ sp.getSubunitCode();
			std.setAddress1(subunit);
		}
	}

	/**
	 * Checks to see if a street address
	 *
	 * @throws org.javautil.address.exception.AddressStandardizationException if any.
	 */
	protected void checkResults() throws AddressStandardizationException {
		if (getStreetAddressLine() == null) {
			throw new NoDeliveryLocationFoundException(raw);

		}
	}

	// todo should only be done if country is US or it really looks like a zip
	// code

	/** {@inheritDoc} */
	@Override
	public ArrayList<AddressStandardizationException> getExceptions() {
		return exceptions;
	}

	/**
	 * <p>getRawAddress.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.Address} object.
	 */
	public Address getRawAddress() {
		return raw;
	}

	/**
	 * <p>getStandardAddress.</p>
	 *
	 * @return a {@link org.javautil.address.interfaces.StandardizedAddress} object.
	 */
	public StandardizedAddress getStandardAddress() {
		return std;
	}

	/**
	 * <p>getStreetAddressLine.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStreetAddressLine() {
		final String retval = streetAddressIsLine2 ? std.getAddress2() : std
				.getAddress1();
		return retval;
	}

	private void setStreetAddressLine(final String text) {
		if (streetAddressIsLine2) {
			std.setAddress2(text);
		} else {
			std.setAddress1(text);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 */
	@Override
	public String toString() {
		final String TAB = System.getProperty("line.separator");

		final String retValue = "USAddressStandardizer ( " + super.toString()
				+ TAB + "addr1 = '" + this.addr1 + "'" + TAB + "addr2 = '"
				+ this.addr2 + "'" + TAB + "cityStandardizer = '"
				+ this.cityStandardizer + "'" + TAB + "exceptions = '"
				+ this.exceptions + "'" + TAB + "logger = '" + this.logger
				+ "'" + TAB + "maxStdStreetAddrLength = '"
				+ this.maxStdStreetAddrLength + "'" + TAB
				+ "poBoxFormatter = '" + this.poBoxFormatter + "'" + TAB
				+ "poBoxLine1Parser = '" + this.poBoxLine1Parser + "'" + TAB
				+ "poBoxLine2Parser = '" + this.poBoxLine2Parser + "'" + TAB
				+ "raw = '" + this.raw + "'" + TAB + "std = '" + this.std + "'"
				+ TAB + "streetAddressFormatter = '"
				+ this.streetAddressFormatter + "'" + TAB
				+ "streetAddressIsLine2 = '" + this.streetAddressIsLine2 + "'"
				+ TAB + "streetLine1Parser = '" + this.streetLine1Parser + "'"
				+ TAB + "streetLine2Parser = '" + this.streetLine2Parser + "'"
				+ TAB + "subunitLine1Parser = '" + this.subunitLine1Parser
				+ "'" + TAB + "subunitLine2Parser = '"
				+ this.subunitLine2Parser + "'" + TAB + "suffixMapper = '"
				+ this.suffixMapper + "'" + TAB + " )";

		return retValue;
	}

}
