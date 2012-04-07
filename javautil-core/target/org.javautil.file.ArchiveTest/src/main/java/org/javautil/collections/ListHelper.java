package org.javautil.collections;

import java.util.ArrayList;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class ListHelper {
	@SuppressWarnings("unchecked")
	public static ArrayList<Object> toList(final Object... o) {
		final ArrayList<Object> al = new ArrayList(o.length);
		for (final Object element : o) {
			al.add(element);
		}
		return al;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> toStringList(final String... o) {
		final ArrayList al = new ArrayList(o.length);
		for (final String element : o) {
			al.add(element);
		}
		return al;
	}
}
