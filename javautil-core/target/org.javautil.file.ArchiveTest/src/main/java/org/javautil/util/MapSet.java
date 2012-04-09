package org.javautil.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.javautil.lang.reflect.IntrospectionHelper;

/**
 * Utility to convert a Collection of java beans to a Map with specified key.
 * Additional methods provide support for sorting and handling of duplicate key
 * values.
 * 
 * NOTE: Generics are not used in the return types of the maps, to allow for
 * casting to the expected types. Generics may not be desirable here, as they
 * would limit the input collections to having beans of only a single type.
 * 
 * @author tim@softwareMentor.com, bcm
 */
@SuppressWarnings("unchecked")
public class MapSet {

	/**
	 * Translates beans into a TreeMap using the key in field keyField. A
	 * natural ordering of keys is expected as TreeMap requires it.
	 * 
	 * @throws IllegalStateException
	 *             when duplicate keyField values are found and
	 *             errorOnDuplicateKey is true
	 */
	public static TreeMap treeMap(final Collection<? extends Object> beans,
			final String keyField, final boolean errorOnDuplicateKey) {
		final TreeMap map = new TreeMap();
		fillMap(beans, keyField, map, errorOnDuplicateKey);
		return map;
	}

	/**
	 * Translates beans into a TreeMap using the key in field keyField.
	 * Duplicate keyField values will fail-fast if errorOnDuplicateKey is true.
	 * The ordering enforced by the comparator will be applied to the map.
	 * 
	 * @throws IllegalStateException
	 *             when duplicate keyField values are found and
	 *             errorOnDuplicateKey is true
	 */
	public static TreeMap treeMap(final Collection<? extends Object> beans,
			final String keyField, final Comparator comparator,
			final boolean errorOnDuplicateKey) {
		final TreeMap map = new TreeMap(comparator);
		fillMap(beans, keyField, map, errorOnDuplicateKey);
		return map;
	}

	/**
	 * Translates beans into a HashMap using the key in field keyField.
	 * Duplicate keyField values will not appear in the resulting HashMap. Order
	 * of keys and values is not guaranteed, as per the HashMap specification.
	 */
	public static HashMap map(final Collection<? extends Object> beans,
			final String keyField) {
		return map(beans, keyField, false);
	}

	/**
	 * Duplicate keyField values will fail-fast if errorOnDuplicateKey is true.
	 * Otherwise this method will act exactly as the two parameter version.
	 * 
	 * @throws IllegalStateException
	 *             when duplicate keyField values are found and
	 *             errorOnDuplicateKey is true
	 */
	public static HashMap map(final Collection<? extends Object> beans,
			final String keyField, final boolean errorOnDuplicateKey) {
		final HashMap map = new HashMap(beans.size());
		fillMap(beans, keyField, map, errorOnDuplicateKey);
		return map;
	}

	/**
	 * Fills targetMap using a value extracted from beans. The value is read
	 * using reflection, by reading a class field having the name of the
	 * keyField parameter. The keyField must exist on every bean contained in
	 * the beans Collection.
	 * 
	 * NOTE: we do not assume that the class has a field named keyField and
	 * access it directly, rather, we invoke the getter method (which is the
	 * preferred way to access fields on cglib proxies).
	 * 
	 * @param beans
	 *            a collection of java beans to be populated into the targetMap
	 * @param keyField
	 *            the class field name to be read
	 * @param targetMap
	 *            the map to be populated with values from the bean collection
	 * @param errorOnDuplicateKey
	 *            true If the map should fail upon encountering duplicate
	 * 
	 * @throws IllegalStateException
	 *             when duplicate keyField values are found and
	 *             errorOnDuplicateKey is true
	 */
	protected static void fillMap(final Collection<? extends Object> beans,
			final String keyField, final Map<Object, Object> targetMap,
			final boolean errorOnDuplicateKey) {
		if (beans.size() > 0) {
			try {
				final IntrospectionHelper reflector = new IntrospectionHelper();
				reflector.setPropertyName(keyField);
				for (final Object bean : beans) {
					if (bean == null) {
						throw new IllegalArgumentException(
								"one or more beans is null");
					}
					reflector.setIntrospectionBean(bean);
					final Object key = reflector.invokeGetter();
					final Object old = targetMap.put(key, bean);
					if (old != null && errorOnDuplicateKey) {
						throw new IllegalArgumentException("key \"" + key
								+ "\" was found as bean property value for "
								+ "field \"" + keyField + "\" on more than "
								+ "one bean");
					}
				}
			} catch (final Exception e) {
				throw new IllegalArgumentException(keyField, e);
			}
		}
	}

}
