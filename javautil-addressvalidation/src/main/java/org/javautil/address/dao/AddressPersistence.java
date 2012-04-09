package org.javautil.address.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.javautil.address.UtAddressValidate;
import org.javautil.persistence.PersistenceException;

/**
 * <p>
 * AddressPersistence interface.
 * </p>
 * 
 * @author jjs
 * @version $Id: AddressPersistence.java,v 1.4 2012/04/09 01:29:07 jjs Exp $
 */
public interface AddressPersistence {

	/**
	 * <p>
	 * getForRunNbr.
	 * </p>
	 * 
	 * @param runNbr
	 *            a long.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public abstract void getForRunNbr(final long runNbr)
			throws PersistenceException;

	/**
	 * <p>
	 * getNext.
	 * </p>
	 * 
	 * @param count
	 *            a int.
	 * @return a {@link java.util.ArrayList} object.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public abstract ArrayList<UtAddressValidate> getNext(int count)
			throws PersistenceException;

	/**
	 * Fetches then next record must precede with call to
	 * 
	 * @see {@link #getForRunNbr(int)} Closes the statement if no more input
	 * @return null if no more records
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public abstract UtAddressValidate getNext() throws PersistenceException;

	/**
	 * Insert all tuples into persistent store.
	 * 
	 * @param rows
	 *            a {@link java.lang.Iterable} object.
	 * @throws java.sql.SQLException
	 *             if any.
	 */
	public abstract void insert(final Iterable<UtAddressValidate> rows)
			throws java.sql.SQLException;

	/**
	 * <p>
	 * update.
	 * </p>
	 * 
	 * @param addresses
	 *            a {@link java.util.Collection} object.
	 * @return the records that couldn't be persisted
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public abstract ArrayList<UtAddressValidate> update(
			final Collection<UtAddressValidate> addresses)
			throws PersistenceException;

	/**
	 * <p>
	 * update.
	 * </p>
	 * 
	 * @param a
	 *            a {@link org.javautil.address.UtAddressValidate} object.
	 * @throws org.javautil.persistence.PersistenceException
	 *             if any.
	 */
	public abstract void update(final UtAddressValidate a)
			throws PersistenceException;

	// public abstract void setConnection(final Connection conn);

	/**
	 * <p>
	 * dispose.
	 * </p>
	 */
	public abstract void dispose();

}
