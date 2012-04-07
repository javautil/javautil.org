package org.javautil.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * MapSet test, also measures time to map.
 * 
 * @author tim@softwareMentor.com
 */
@SuppressWarnings("unchecked")
public class MapSetTest {

	@Test
	public void testToMap() {
		// generate a set
		final int size = 10000;
		final Set set = new HashSet(size);
		for (int i = 0; i < size; i++) {
			set.add(new CollectionMember(i, new Integer(i).toString()));
		}
		// map the set
		final long start = System.nanoTime();
		final Map<Integer, CollectionMember> map = MapSet.map(set, "id");
		final long end = System.nanoTime();
		System.out.println("mapped " + size + " set elements in "
				+ (end - start) / 1000 + "uS");

		// check the map
		assertEquals(size, map.size());
		final CollectionMember zero = map.get(0);
		assertNotNull(zero);
		assertEquals("0", zero.getValue());
	}
}

class CollectionMember {
	private final int id;
	private final String value;

	CollectionMember(final int id, final String value) {
		this.id = id;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

}
