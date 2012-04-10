package org.javautil.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class ListHelper {
	public static List<Object> toList(final Object... o) {
		final ArrayList<Object> al = new ArrayList<Object>(o.length);
		for (final Object element : o) {
			al.add(element);
		}
		return al;
	}

	public static ArrayList<String> toStringList(final String... o) {
		final ArrayList<String> al = new ArrayList<String>(o.length);
		for (final String element : o) {
			al.add(element);
		}
		return al;
	}
}
