/**
 * 
 */
package org.javautil.address.service;

import java.sql.SQLException;

import org.javautil.address.dao.AddressPersistence;
import org.javautil.address.service.usps.AddressValidationService;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.address.usps.UspsValidationServicePropertyHelper;
import org.javautil.commandline.CommandLineHandler;
import org.javautil.jdbc.datasources.DataSourceInstantiationException;
import org.javautil.persistence.PersistenceException;
import org.javautil.util.InvalidEnvironmentException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>
 * AddressValidationServiceIT class.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressValidationServiceIT.java,v 1.3 2012/03/04 12:31:16 jjs
 *          Exp $
 * @since 0.11.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:AddressValidationServiceCsvIt-context.xml" })
public class AddressValidationServiceCsvIT {
	@Autowired
	private AddressValidationService validationService = null;
	@Autowired
	private AddressPersistence persister;

	@Test
	public void notATest() {

	}

	/**
	 * <p>
	 * addressValidationServiceArgumentsTest.
	 * </p>
	 * 
	 * @throws java.sql.SQLException
	 *             if any.
	 * @throws org.javautil.jdbc.datasources.DataSourceInstantiationException
	 *             if any.
	 * @throws org.javautil.util.InvalidEnvironmentException
	 *             if any.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 */
	@Ignore
	@Test
	public void addressValidationServiceArgumentsTest() throws SQLException,
			DataSourceInstantiationException, InvalidEnvironmentException,
			PersistenceException, AddressValidationException {
		final String uspsAcct = new UspsValidationServicePropertyHelper()
				.getUserId();
		final String[] parms = { "-runNbr", "1", "-uspsAcct", uspsAcct,
				"-noDatasource" };

		final AddressValidationServiceArguments args = new AddressValidationServiceArguments();

		final CommandLineHandler clh = new CommandLineHandler(args);
		// TODO these should be injected
		validationService.setPersister(persister);
		clh.evaluateArguments(parms);
		validationService.process(args);
	}

	public AddressValidationService getAvs() {
		return validationService;
	}

	public void setAvs(AddressValidationService avs) {
		this.validationService = avs;
	}

	public AddressPersistence getPersister() {
		return persister;
	}

	public void setPersister(AddressPersistence persister) {
		this.persister = persister;
	}

}
