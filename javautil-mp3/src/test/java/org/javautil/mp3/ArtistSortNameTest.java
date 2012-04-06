package org.javautil.mp3;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArtistSortNameTest extends ArtistSortName {
	@Test
	public void getSortNameTest() {
		assertEquals("BEATLES", getSortName("Beatles, The"));
		assertEquals("BEATLES", getSortName("BEATLES,  THE"));
		assertEquals("BEATLES", getSortName("The Beatles"));
		assertEquals("BEATLES", getSortName(" The Beatles "));

	}

	@Test
	public void processTheTest() {
		assertEquals("BEATLES", processThe("BEATLES, THE"));
		assertEquals("BEATLES", processThe("BEATLES,  THE"));
	}
}
