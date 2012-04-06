package org.javautil.address.standardname;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

// todo use this in stree standardization 
/**
 * <p>DirectionMapper class.</p>
 *
 * @author jjs
 * @version $Id: DirectionMapper.java,v 1.3 2012/03/04 12:31:11 jjs Exp $
 */
public class DirectionMapper {
	private static Map<String, String> directionMap = new TreeMap<String, String>();
	private static final String NW = "NW";
	private static final String W = "W";
	private static final String S = "S";
	private static final String N = "N";
	private static final String E = "E";
	private static final String NE = "NE";
	private static final String SW = "SW";
	private static final String SE = "SE";

	private final boolean logMisses = true;
	/**
	 * Number of times the specified string was not found
	 */
	private final HashMap<String, Integer> missCount = new HashMap<String, Integer>();
	static {
		directionMap.put("N", N);
		directionMap.put("S", S);
		directionMap.put("E", E);
		directionMap.put("W", W);
		directionMap.put("NORTH", N);
		directionMap.put("SOUTH", S);
		directionMap.put("EAST", E);
		directionMap.put("WEST", W);
		directionMap.put("NE", NE);
		directionMap.put("NW", NW);
		directionMap.put("SE", SE);
		directionMap.put("SW", SW);

	}

	/**
	 * <p>dumpMissCount.</p>
	 *
	 * @param min a int.
	 */
	public void dumpMissCount(final int min) {
		for (final String s : missCount.keySet()) {

			final Integer c = missCount.get(s);
			if (c >= min) {
				System.out.println(s + " " + c);
			}
		}
	}

	/**
	 * <p>map.</p>
	 *
	 * @param in a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String map(final String in) {
		if (in == null) {
			throw new IllegalArgumentException("in is null");
		}

		String find = in.toUpperCase().trim();
		find = find.replace(".", "");
		// logger.info("looking for '" + find + "'");
		final String found = directionMap.get(find);
		if (logMisses) {
			logMiss(find);
		}
		return found;
	}

	private void logMiss(final String text) {
		final Integer misses = missCount.get(text);
		if (misses == null) {
			missCount.put(text, 1);
		} else {
			missCount.put(text, misses + 1);
		}
	}
}
