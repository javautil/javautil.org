package org.javautil.address.standardname;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/**
 * <p>UspsUnitAbbreviations class.</p>
 *
 * @see http://www.usps.com/ncsc/lookups/usps_abbreviations.html
 * @author jjs
 * @version $Id: UspsUnitAbbreviations.java,v 1.3 2012/03/04 12:31:11 jjs Exp $
 */
public class UspsUnitAbbreviations implements QualifiedStandardNames {
	private static final TreeMap<String, UspsUnitAbbreviation> byCode = new TreeMap<String, UspsUnitAbbreviation>();

	static {
		populateStandards();
		// populateCommonAbbreviations();
	}

	/**
	 * <p>getCodes.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCodes() {
		final StringBuilder b = new StringBuilder();
		for (final String s : byCode.keySet()) {
			b.append("'");
			b.append(s);
			b.append("' ");
		}
		return b.toString();
	}

	/**
	 * <p>getStandard.</p>
	 *
	 * @param uspsCode a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String getStandard(final String uspsCode) {
		final UspsUnitAbbreviation uua = byCode.get(uspsCode);
		return uua == null ? null : uua.getStandardName();
	}

	private static void populateStandards() {
		final ArrayList<UspsUnitAbbreviation> list = new ArrayList<UspsUnitAbbreviation>();

		list.add(new UspsUnitAbbreviation("Apartment", "APT", true,
				new String[] { "APART" }));
		list.add(new UspsUnitAbbreviation("Basement", "BSMT", false, null));
		list.add(new UspsUnitAbbreviation("Building", "BLDG", true, null));
		list.add(new UspsUnitAbbreviation("Department", "DEPT", true, null));
		list.add(new UspsUnitAbbreviation("Floor", "FL", true, null));
		list.add(new UspsUnitAbbreviation("Front", "FRNT", false, null));
		list.add(new UspsUnitAbbreviation("Hangar", "HNGR", true, null));
		list.add(new UspsUnitAbbreviation("Lobby", "LBBY", false, null));
		list.add(new UspsUnitAbbreviation("Lot", "LOT ", true, null));
		list.add(new UspsUnitAbbreviation("Lower", "LOWR", false, null));
		list.add(new UspsUnitAbbreviation("Office", "OFC", false, null));
		list.add(new UspsUnitAbbreviation("Penthouse", "PH", false, null));
		list.add(new UspsUnitAbbreviation("Pier", "PIER", true, null));
		list.add(new UspsUnitAbbreviation("Rear", "REAR", false, null));
		list.add(new UspsUnitAbbreviation("Room", "RM", true, null));
		list.add(new UspsUnitAbbreviation("Side", "SIDE", false, null));
		list.add(new UspsUnitAbbreviation("Slip", "SLIP", true, null));
		list.add(new UspsUnitAbbreviation("Space", "SPC", true, null));
		list.add(new UspsUnitAbbreviation("Stop", "STOP", true, null));
		list.add(new UspsUnitAbbreviation("Suite", "STE", true, null));
		list.add(new UspsUnitAbbreviation("Trailer", "TRLR", true, null));
		list.add(new UspsUnitAbbreviation("Unit", "UNIT", true, null));
		list.add(new UspsUnitAbbreviation("Upper", "UPPR", false, null));

		for (int i = 0; i < list.size(); i++) {
			{
				final UspsUnitAbbreviation uua = list.get(i);
				byCode.put(uua.getStandardName().toUpperCase(), uua);
				byCode.put(uua.getStandardAbbreviation().toUpperCase(), uua);
				for (final String abbrev : uua.getVariants()) {
					byCode.put(abbrev.toUpperCase(), uua);
				}
			}
		}
	}

	/**
	 * <p>getUspsUnitAbbreviation.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.javautil.address.standardname.UspsUnitAbbreviation} object.
	 */
	public UspsUnitAbbreviation getUspsUnitAbbreviation(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}
		final UspsUnitAbbreviation retval = byCode.get(name.toUpperCase());
		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public boolean requiresQualifier(final String name) {
		if (name == null) {
			throw new IllegalArgumentException("name is null");
		}
		final UspsUnitAbbreviation ua = byCode.get(name.toUpperCase());
		if (ua == null) {
			throw new IllegalArgumentException("unknown code: '" + name + "'");
		}
		return ua.isQualifierRequired();

	}

	/** {@inheritDoc} */
	@Override
	public String getStandardName(final String variant) {
		final UspsUnitAbbreviation ua = getUspsUnitAbbreviation(variant);
		return ua.getStandardName();
	}

	/** {@inheritDoc} */
	@Override
	public Collection<String> getVariants(final String name) {
		final UspsUnitAbbreviation ua = getUspsUnitAbbreviation(name);
		Collection<String> retval = null;
		if (ua != null) {
			retval = ua.getVariants();
		}
		// else {
		// retval = empty;
		// }
		return retval;
	}

	/** {@inheritDoc} */
	@Override
	public String getStandardAbbreviation(final String name) {
		final UspsUnitAbbreviation ua = getUspsUnitAbbreviation(name);
		return ua.getStandardAbbreviation();
	}

}
