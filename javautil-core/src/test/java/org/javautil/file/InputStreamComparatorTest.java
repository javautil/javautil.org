package org.javautil.file;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;

import org.junit.Test;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

public class InputStreamComparatorTest 
	// TODO wtf  what is this and can it be used?
	// extends AbstractDependencyInjectionSpringContextTests 
{

	/**
	 * Test FileComparator compareInputStream method
	 * 
	 * @throws Exception
	 */

	@Test
	public void testCompareInputStreams() throws Exception {

		final ByteArrayInputStream inputStream1 = new ByteArrayInputStream(
				new byte[] { 0, 1 });
		final ByteArrayInputStream inputStream2 = new ByteArrayInputStream(
				new byte[] { 0, 2 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream1, inputStream2);

		assertEquals(-2, returnValue);

	}

	@Test
	public void testCompareInputStreamsExtraValues() throws Exception {

		final ByteArrayInputStream inputStream3 = new ByteArrayInputStream(
				new byte[] { 0, 2 });
		final ByteArrayInputStream inputStream4 = new ByteArrayInputStream(
				new byte[] { 0, 2, 0 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream3, inputStream4);

		assertEquals(3, returnValue);

	}

	@Test
	public void testCompareInputStreamsDifferentPlacement() throws Exception {

		final ByteArrayInputStream inputStream5 = new ByteArrayInputStream(
				new byte[] { 1, 0 });
		final ByteArrayInputStream inputStream6 = new ByteArrayInputStream(
				new byte[] { 0, 2 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream5, inputStream6);

		assertEquals(1, returnValue);

	}

	@Test
	public void testCompareInputStreamsSamePlacement() throws Exception {

		final ByteArrayInputStream inputStream7 = new ByteArrayInputStream(
				new byte[] { 1, 0 });
		final ByteArrayInputStream inputStream8 = new ByteArrayInputStream(
				new byte[] { 1, 0 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream7, inputStream8);

		assertEquals(0, returnValue);

	}

	@Test
	public void testCompareInputStreamsMaxNum() throws Exception {

		final ByteArrayInputStream inputStream9 = new ByteArrayInputStream(
				new byte[] { 0, 127 });
		final ByteArrayInputStream inputStream10 = new ByteArrayInputStream(
				new byte[] { 0, 1 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream9, inputStream10);

		assertEquals(2, returnValue);

	}

	@Test
	public void testCompareInputStreamsMaxNum2() throws Exception {

		final ByteArrayInputStream inputStream9 = new ByteArrayInputStream(
				new byte[] { 0, 1 });
		final ByteArrayInputStream inputStream10 = new ByteArrayInputStream(
				new byte[] { 0, 127 });

		final int returnValue = new InputStreamComparator().compare(
				inputStream9, inputStream10);

		assertEquals(-2, returnValue);

	}
}
