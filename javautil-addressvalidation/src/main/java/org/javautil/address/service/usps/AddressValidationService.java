package org.javautil.address.service.usps;

import org.javautil.address.dao.AddressPersistence;
import org.javautil.address.service.AddressValidationServiceArguments;
import org.javautil.address.usps.AddressValidationException;
import org.javautil.persistence.PersistenceException;

public interface AddressValidationService {

	/**
	 * <p>
	 * process.
	 * </p>
	 * 
	 * @param arguments
	 *            a
	 *            {@link org.javautil.address.service.AddressValidationServiceArguments}
	 *            object.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 * @throws org.javautil.address.usps.AddressValidationException
	 *             if any.
	 * @throws java$sql$SQLException
	 *             if any.
	 */
	public abstract void process(
			final AddressValidationServiceArguments arguments)
			throws PersistenceException, AddressValidationException;

	public abstract void setPersister(AddressPersistence persister);

}