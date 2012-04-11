/**
 * 
 */
package org.javautil.address.beans;

import java.sql.Timestamp;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * <p>
 * StandardAddressBeanTest class.
 * </p>
 * 
 * @author siyer
 * @version $Id: StandardAddressBeanTest.java,v 1.2 2012/03/04 12:31:18 jjs Exp
 *          $
 * @since 0.11.0
 */
public class StandardAddressBeanTest {

	private final Logger logger = Logger.getLogger(getClass());

	/**
	 * <p>
	 * constructorTest1.
	 * </p>
	 */
	@Test
	public void constructorTest1() {
		final StandardAddressBean bean = new StandardAddressBean();
		Assert.assertNotNull(bean);
		Assert.assertNotNull(bean.toString());
	}

	/**
	 * <p>
	 * constructorTest2.
	 * </p>
	 */
	@Test
	public void constructorTest2() {
		final String address1 = "410 S Main";
		final String address2 = "";
		final String city = "Romeo";
		final String st = "MI";
		final String postalCode = "48065";
		final StandardAddressBean addr = new StandardAddressBean(address1,
				address2, city, st, postalCode);
		final int hash = addr.hashCode();
		Assert.assertNotNull(addr);
		Assert.assertTrue(hash != 0);
		Assert.assertNotNull(addr.toString());
	}

	/**
	 * <p>
	 * constructorTest3.
	 * </p>
	 */
	@Test
	public void constructorTest3() {
		final String address1 = "410 S Main";
		final String address2 = "";
		final String city = "Romeo";
		final String st = "MI";
		final String postalCode = "48065";
		final String streetNbr = "410";
		final String streetName = "S Main";
		final String streetType = "St";
		final String poBox = null;
		final String subunitCode = "110";
		final String subunitType = "Ste";
		final StandardAddressBean addr = new StandardAddressBean(address1,
				address2, city, st, postalCode, streetNbr, streetName,
				streetType, poBox, subunitType, subunitCode);
		final int hash = addr.hashCode();
		Assert.assertNotNull(addr);
		Assert.assertTrue(hash != 0);
		Assert.assertNotNull(addr.toString());
	}

	/**
	 * <p>
	 * constructorTest4.
	 * </p>
	 */
	@Test
	public void constructorTest4() {
		final long currentTs = System.currentTimeMillis();
		final String poBox = "102002";
		final String streetName = "S Main";
		final String streetNumber = "410";
		final String streetType = "St";
		final String subunitCode = "100";
		final String subunitType = "Ste";
		final Timestamp standardizationTime = new Timestamp(currentTs);
		final String standardizationErrorMessage = "OK";
		final Double longitude = 34.22;
		final Double latitude = 43.21;
		final StandardAddressBean bean = new StandardAddressBean();
		bean.setPoBox(poBox);
		bean.setStreetName(streetName);
		bean.setStreetNumber(streetNumber);
		bean.setStreetType(streetType);
		bean.setSubunitCode(subunitCode);
		bean.setSubunitType(subunitType);
		bean.setStandardizationErrorMessage(standardizationErrorMessage);
		bean.setStandardizationTime(standardizationTime);
		bean.setLatitude(latitude);
		bean.setLongitude(longitude);
		final StandardAddressBean bean2 = new StandardAddressBean(bean);
		Assert.assertNotNull(bean);
		Assert.assertNotNull(bean2);
		Assert.assertNotNull(bean.getPoBox());
		Assert.assertNotNull(bean.getStandardizationTime());
		Assert.assertNotNull(bean.getLatitude());
		Assert.assertNotNull(bean.getLongitude());
		Assert.assertTrue(bean.hashCode() != 0);
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
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		Assert.assertTrue(addr1.equals(addr2));
	}

	/*
	 * Both addresses are not same. So Not Equal.
	 */
	/**
	 * <p>
	 * equalsTest2.
	 * </p>
	 */
	@Test
	public void equalsTest2() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr2.setLatitude(12.33);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest3.
	 * </p>
	 */
	@Test
	public void equalTest3() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final AddressBean addr2 = new AddressBean();
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest4.
	 * </p>
	 */
	@Test
	public void equalTest4() {
		// TODO Write test to make sure that we compare a SimpleAddress
	}

	/**
	 * <p>
	 * equalTest5.
	 * </p>
	 */
	@Test
	public void equalTest5() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr2.setLatitude(23.34);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest6.
	 * </p>
	 */
	@Test
	public void equalTest6() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest7.
	 * </p>
	 */
	@Test
	public void equalTest7() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(43.55);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest8.
	 * </p>
	 */
	@Test
	public void equalTest8() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr2.setLongitude(65.33);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest9.
	 * </p>
	 */
	@Test
	public void equalTest9() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest10.
	 * </p>
	 */
	@Test
	public void equalTest10() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(23.89);
		addr2.setLongitude(65.33);
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest11.
	 * </p>
	 */
	@Test
	public void equalTest11() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr2.setPoBox("102");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest12.
	 * </p>
	 */
	@Test
	public void equalTest12() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest13.
	 * </p>
	 */
	@Test
	public void equalTest13() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("409943");
		addr2.setPoBox("102");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest14.
	 * </p>
	 */
	@Test
	public void equalTest14() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr2.setStandardizationErrorMessage("Error");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest15.
	 * </p>
	 */
	@Test
	public void equalTest15() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest16.
	 * </p>
	 */
	@Test
	public void equalTest16() {
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("No Error");
		addr2.setStandardizationErrorMessage("Error");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest17.
	 * </p>
	 */
	@Test
	public void equalTest17() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr2.setStandardizationTime(new Timestamp(ms));
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest18.
	 * </p>
	 */
	@Test
	public void equalTest18() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest19.
	 * </p>
	 * 
	 * @throws java.lang.InterruptedException
	 *             if any.
	 */
	@Test
	public void equalTest19() throws InterruptedException {
		final long ms = System.currentTimeMillis();
		Thread.sleep(2500);
		final long ms2 = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms2));
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest20.
	 * </p>
	 */
	@Test
	public void equalTest20() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest21.
	 * </p>
	 */
	@Test
	public void equalTest21() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr2.setStreetName("Street");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest22.
	 * </p>
	 */
	@Test
	public void equalTest22() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest23.
	 * </p>
	 */
	@Test
	public void equalTest23() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street NOT SAME");
		addr2.setStreetName("Street");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest24.
	 * </p>
	 */
	@Test
	public void equalTest24() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr2.setStreetNumber("11232");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest25.
	 * </p>
	 */
	@Test
	public void equalTest25() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest26.
	 * </p>
	 */
	@Test
	public void equalTest26() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("89023");
		addr2.setStreetNumber("11232");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest27.
	 * </p>
	 */
	@Test
	public void equalTest27() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr2.setStreetType("AVE");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest29.
	 * </p>
	 */
	@Test
	public void equalTest29() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest30.
	 * </p>
	 */
	@Test
	public void equalTest30() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("BLVD");
		addr2.setStreetType("AVE");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest31.
	 * </p>
	 */
	@Test
	public void equalTest31() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr2.setSubunitCode("1023");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest32.
	 * </p>
	 */
	@Test
	public void equalTest32() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr1.setSubunitCode("1023");
		addr2.setSubunitCode("1023");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest33.
	 * </p>
	 */
	@Test
	public void equalTest33() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr1.setSubunitCode("90232");
		addr2.setSubunitCode("1023");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest34.
	 * </p>
	 */
	@Test
	public void equalTest34() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr1.setSubunitCode("1023");
		addr2.setSubunitCode("1023");
		addr2.setSubunitType("SUITE");
		Assert.assertFalse(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest35.
	 * </p>
	 */
	@Test
	public void equalTest35() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr1.setSubunitCode("1023");
		addr2.setSubunitCode("1023");
		addr1.setSubunitType("SUITE");
		addr2.setSubunitType("SUITE");
		Assert.assertTrue(addr1.equals(addr2));
	}

	/**
	 * <p>
	 * equalTest36.
	 * </p>
	 */
	@Test
	public void equalTest36() {
		final long ms = System.currentTimeMillis();
		final StandardAddressBean addr1 = new StandardAddressBean();
		final StandardAddressBean addr2 = new StandardAddressBean();
		addr1.setLatitude(23.34);
		addr2.setLatitude(23.34);
		addr1.setLongitude(65.33);
		addr2.setLongitude(65.33);
		addr1.setPoBox("102");
		addr2.setPoBox("102");
		addr1.setStandardizationErrorMessage("Error");
		addr2.setStandardizationErrorMessage("Error");
		addr1.setStandardizationTime(new Timestamp(ms));
		addr2.setStandardizationTime(new Timestamp(ms));
		addr1.setStreetName("Street");
		addr2.setStreetName("Street");
		addr1.setStreetNumber("11232");
		addr2.setStreetNumber("11232");
		addr1.setStreetType("AVE");
		addr2.setStreetType("AVE");
		addr1.setSubunitCode("1023");
		addr2.setSubunitCode("1023");
		addr1.setSubunitType("APT");
		addr2.setSubunitType("SUITE");
		Assert.assertFalse(addr1.equals(addr2));
	}

}
