/**
 * 
 */
package org.javautil.address.beans;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * <p>
 * SimpleAddressTest class.
 * </p>
 * 
 * @author siyer
 * @version $Id: SimpleAddressTest.java,v 1.4 2012/04/11 18:29:24 jjs Exp $
 * @since 0.11.0
 */
public class SimpleAddressTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * test1.
	 * </p>
	 */
	@Test
	public void test1() {
		final String address1 = "410 S Main";
		final String address2 = "";
		final String city = "Romeo";
		final String st = "MI";
		final String postalCode = "48065";
		final SimpleAddress addr = new SimpleAddress(address1, address2, city,
				st, postalCode);
		final int hash = addr.getAddressHash();
		Assert.assertNotNull(addr);
		Assert.assertNotNull(addr.getAddress1());
		Assert.assertNotNull(addr.getFormatted());
		Assert.assertNotNull(addr.toString());
		Assert.assertTrue(hash != 0);
	}

	/**
	 * <p>
	 * test2.
	 * </p>
	 */
	@Test
	public void test2() {
		final SimpleAddress addr = new SimpleAddress();
		Assert.assertEquals("", addr.getFormatted());
	}

	/**
	 * <p>
	 * getIdTest.
	 * </p>
	 */
	@Test
	public void getIdTest() {
		final Long id = new Long(2038290348);
		final SimpleAddress addr = new SimpleAddress();
		addr.setId(id);
		Assert.assertTrue(addr.getId() != null);
	}

	/**
	 * <p>
	 * setStandardizationFlagsTest.
	 * </p>
	 */
	@Test
	public void setStandardizationFlagsTest() {
		final int flag = 100;
		final SimpleAddress addr = new SimpleAddress();
		addr.setStandardizationFlags(flag);
		Assert.assertEquals(100, addr.getStandardizationFlags());
	}

	/**
	 * <p>
	 * miscTest.
	 * </p>
	 */
	@Test
	public void miscTest() {
		final SimpleAddress addr = new SimpleAddress();
		Assert.assertTrue(SimpleAddress.getADDR_LENGTH() != 0);
		Assert.assertNotNull(SimpleAddress.getNewline());
		Assert.assertNotNull(addr.getLogger());
	}

	/*
	 * Both addresses are null. So Equal.
	 */
	/**
	 * <p>
	 * equalsTest1.
	 * </p>
	 */
	@Test
	public void equalsTest1() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		Assert.assertTrue(addr1.equals(addr2));
	}

	/*
	 * Address1.address1 is set. Not Equal.
	 */
	/**
	 * <p>
	 * equalsTest2.
	 * </p>
	 */
	@Test
	public void equalsTest2() {
		final SimpleAddress addr1 = new SimpleAddress();
		addr1.setAddress1("Test");
		final SimpleAddress addr2 = new SimpleAddress();
		Assert.assertFalse(addr1.equals(addr2));
	}

	/*
	 * Not the same object
	 */
	/**
	 * <p>
	 * equalsTest3.
	 * </p>
	 */
	@Test
	public void equalsTest3() {
		final SimpleAddress addr1 = new SimpleAddress();
		// addr1.setAddress1("Test");
		final AddressBean addr2 = new AddressBean();
		Assert.assertFalse(addr1.equals(addr2));
	}

	/*
	 * Assert.
	 */
	/**
	 * <p>
	 * equalsTest4.
	 * </p>
	 */
	@Test
	public void equalsTest4() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest5.
	 * </p>
	 */
	@Test
	public void equalsTest5() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr2.setAddress1("Test");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest6.
	 * </p>
	 */
	@Test
	public void equalsTest6() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test1");
		addr2.setAddress1("Test2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest7.
	 * </p>
	 */
	@Test
	public void equalsTest7() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr2.setAddress2("Test2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest8.
	 * </p>
	 */
	@Test
	public void equalsTest8() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest9.
	 * </p>
	 */
	@Test
	public void equalsTest9() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2.1");
		addr2.setAddress2("Test2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest10.
	 * </p>
	 */
	@Test
	public void equalsTest10() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr2.setCity("City");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest11.
	 * </p>
	 */
	@Test
	public void equalsTest11() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest12.
	 * </p>
	 */
	@Test
	public void equalsTest12() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City1");
		addr2.setCity("City2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest13.
	 * </p>
	 */
	@Test
	public void equalsTest13() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr2.setState("ST");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest14.
	 * </p>
	 */
	@Test
	public void equalsTest14() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest15.
	 * </p>
	 */
	@Test
	public void equalsTest15() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST1");
		addr2.setState("ST2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest16.
	 * </p>
	 */
	@Test
	public void equalsTest16() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr2.setPostalCode("POSTAL");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest18.
	 * </p>
	 */
	@Test
	public void equalsTest18() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest19.
	 * </p>
	 */
	@Test
	public void equalsTest19() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL1");
		addr2.setPostalCode("POSTAL2");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest20.
	 * </p>
	 */
	@Test
	public void equalsTest20() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		new Long(1093433433);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr2.setId(id1);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest21.
	 * </p>
	 */
	@Test
	public void equalsTest21() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		final Long id2 = new Long(1093433433);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr1.setId(id1);
		addr2.setId(id2);
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest22.
	 * </p>
	 */
	@Test
	public void equalsTest22() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		final Long id2 = new Long(288883347);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr1.setId(id1);
		addr2.setId(id2);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest23.
	 * </p>
	 */
	@Test
	public void equalsTest23() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		final Long id2 = new Long(1093433433);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr1.setId(id1);
		addr2.setId(id2);
		addr2.setStandardizationFlags(1);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest24.
	 * </p>
	 */
	@Test
	public void equalsTest24() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		final Long id2 = new Long(1093433433);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr1.setId(id1);
		addr2.setId(id2);
		addr1.setStandardizationFlags(1);
		addr2.setStandardizationFlags(1);
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalsTest25.
	 * </p>
	 */
	@Test
	public void equalsTest25() {
		final SimpleAddress addr1 = new SimpleAddress();
		final SimpleAddress addr2 = new SimpleAddress();
		final Long id1 = new Long(1093433433);
		final Long id2 = new Long(1093433433);
		addr1.setAddress1("Test");
		addr2.setAddress1("Test");
		addr1.setAddress2("Test2");
		addr2.setAddress2("Test2");
		addr1.setCity("City");
		addr2.setCity("City");
		addr1.setState("ST");
		addr2.setState("ST");
		addr1.setPostalCode("POSTAL");
		addr2.setPostalCode("POSTAL");
		addr1.setId(id1);
		addr2.setId(id2);
		addr1.setStandardizationFlags(2002);
		addr2.setStandardizationFlags(1001);
		Assert.assertFalse(addr1.equals(addr2));

	}

}
